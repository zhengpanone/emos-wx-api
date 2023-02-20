package com.zp.emos.wx.dao;


import com.zp.emos.wx.pojo.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface UserDao {

    boolean haveRootUser();

    int insert(@Param("user") UserEntity user);
    int searchIdByOpenId(String openId);

    /**
     * 查询用户权限
     * @param userId 用户ID
     * @return
     */
    Set<String> searchUserPermissions(@Param("userId") int userId);
}