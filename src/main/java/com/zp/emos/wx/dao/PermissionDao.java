package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PermissionEntity record);

    int insertSelective(PermissionEntity record);

    PermissionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PermissionEntity record);

    int updateByPrimaryKey(PermissionEntity record);
}