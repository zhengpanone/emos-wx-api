package com.zp.emos.wx.config.shiro;

import com.zp.emos.wx.pojo.UserEntity;
import com.zp.emos.wx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权（验证权限时调用）
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 查询用户的权限列表
        UserEntity user = (UserEntity)principalCollection.getPrimaryPrincipal();
        Integer userId = user.getId();
        Set<String> permsSet = userService.searchUserPermissions(userId);
        // 把权限添加用户信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证（验证登录时调用）
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //  从令牌中获取userId,然后检测该账号是否被冻结
        String accessToken = (String) authenticationToken.getPrincipal();
        int userId = jwtUtil.getUserId(accessToken);
        UserEntity user = userService.searchById(userId);
        if (user == null) {
            throw new LockedAccountException("账户已被锁定，请联系管理员");
        }
        //   往info对象中添加用户信息、token字符串
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
