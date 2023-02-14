package com.zp.emos.wx.config.filter;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zp.emos.wx.config.shiro.JwtUtil;
import com.zp.emos.wx.config.shiro.OAuth2Token;
import com.zp.emos.wx.config.shiro.ThreadLocalToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype") // 多列对象
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {
    @Autowired
    private ThreadLocalToken threadLocalToken;
    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 拦截请求之后,用于把令牌字符串封装成令牌对象
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StrUtil.isBlank(token)) {
            return null;
        }
        return new OAuth2Token(token);
    }

    /**
     * 拦截请求,判断请求是否需要被shiro处理
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        // ajax提交application/json数据的时候,会先发出option请求
        // 这里要放行options请求,不需要Shiro处理
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        // 除了options请求之外,所有请求都要被shiro处理
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        // 允许跨域请求
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));

        threadLocalToken.clear();
        String token = getRequestToken(req);
        if(StrUtil.isBlank(token)){
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效令牌");
            return false;
        }
        try {
            jwtUtil.verifyToken(token);
        }catch (TokenExpiredException e){
            if(redisTemplate.hasKey(token)){
                redisTemplate.delete(token);
                int userId = jwtUtil.getUserId(token);
                token = jwtUtil.createToken(userId);
                redisTemplate.opsForValue().set(token,userId+"",cacheExpire, TimeUnit.DAYS);
                threadLocalToken.setToken(token);

            }else {
                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
                resp.getWriter().print("token 令牌已过期");
                return false;
            }
        }catch (JWTDecodeException e){
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("token 令牌无效");
            return false;
        }

        boolean bool =executeLogin(servletRequest,servletResponse);
        return  bool;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        // 允许跨域请求
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
        resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
        try {
            resp.getWriter().print(e.getMessage());
        } catch (IOException ex) {
            log.error("认证失败");
        }
        return false;
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 该方法用于处理所有应该被shiro处理的请求
     * @param request
     * @return
     */

    private String getRequestToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            token =request.getParameter("token");
        }
        return token;
    }
}
