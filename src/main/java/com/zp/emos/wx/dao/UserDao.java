package com.zp.emos.wx.dao;


import com.zp.emos.wx.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserDao {

    boolean haveRootUser();

    int insert(@Param("user") UserEntity user);
    int searchIdByOpenId(String openId);
}