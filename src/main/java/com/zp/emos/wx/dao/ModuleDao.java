package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.ModuleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModuleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ModuleEntity record);

    int insertSelective(ModuleEntity record);

    ModuleEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModuleEntity record);

    int updateByPrimaryKey(ModuleEntity record);
}