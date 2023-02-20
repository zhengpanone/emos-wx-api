package com.zp.emos.wx.service;

import com.zp.emos.wx.controller.form.RegisterForm;

import java.util.Set;

public interface UserService {
    /**
     * 用户注册
     * @param registerForm 注册表单
     * @return 用户ID
     */
    int registerUser(RegisterForm registerForm);
    /**
     * 查询用户权限
     * @param userId 用户ID
     * @return
     */
    Set<String> searchUserPermissions(int userId);
}
