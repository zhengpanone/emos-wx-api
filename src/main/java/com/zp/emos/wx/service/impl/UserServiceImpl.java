package com.zp.emos.wx.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zp.emos.wx.constants.EmosConstants.Root;
import com.zp.emos.wx.dao.UserDao;
import com.zp.emos.wx.exception.EmosException;
import com.zp.emos.wx.pojo.UserEntity;
import com.zp.emos.wx.controller.form.RegisterForm;
import com.zp.emos.wx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Value("${wx.app-id}")
    private String appId;
    @Value("${wx.app-secret}")
    private String appSecret;
    @Autowired
    private UserDao userDao;

    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.get(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openid = json.getStr("openid");
        if (StrUtil.isBlank(openid)) {
            throw new RuntimeException("临时登录凭证错误");
        }
        return openid;
    }


    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = userDao.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public int registerUser(RegisterForm registerForm) {
        Integer userId = null;
        String registerCode = registerForm.getRegisterCode();
        UserEntity user = new UserEntity();
        String openId = getOpenId(registerForm.getCode());
        // 如果邀请码是00000,代表是超级管理员
        if (Root.ROOT_REGISTER_CODE.equals(registerCode)) {
            // 查询超级管理员账户是否已经绑定
            boolean bool = userDao.haveRootUser();
            if (!bool) {
                // 把当前用户绑定到root账户
                user.setNickname(registerForm.getNickname());
                user.setOpenId(openId);
                user.setPhoto(registerForm.getPhoto());
                user.setRole(Root.ROLE);
                user.setName(Root.NAME);
                user.setRoot(true);
                user.setStatus(1);
                user.setCreateTime(new Date());
                userDao.insert(user);
                userId = userDao.searchIdByOpenId(openId);
            } else {
                // 如果root已经绑定了，抛出异常
                throw new EmosException("无法绑定超级管理员账户");
            }

        } else {
            // TODO
            return 0;
        }
        return userId;
    }


}
