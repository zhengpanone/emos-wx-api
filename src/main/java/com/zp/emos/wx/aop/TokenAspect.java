package com.zp.emos.wx.aop;

import com.zp.emos.wx.common.util.R;
import com.zp.emos.wx.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 该切面类拦截所有的Web方法返回值，判断是否刷新生成新令牌
 * 切入流程：
 * 1、创建组件类并标记注解@Aspect
 * 2、定义切入点标记注解@Pointcut
 * 3、使用切入点切入标记注解@Before、@After、@Around等
 */
@Aspect
@Component
public class TokenAspect {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    // 切点
    @Pointcut("execution(public * com.zp.emos.wx.controller.*.*(..))")
    public void aspect() {

    }

    // 环绕事件
    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        R r = (R) point.proceed(); // 方法执行结果
        String token = threadLocalToken.getToken(); // 检查ThreadLocal中是否保存令牌
        // 如果threadLocal中存在Token,说明是更新的token
        if (token != null) {
            r.put("token", token);// 往响应中放置Token
            threadLocalToken.clear();
        }
        return r;
    }
}
