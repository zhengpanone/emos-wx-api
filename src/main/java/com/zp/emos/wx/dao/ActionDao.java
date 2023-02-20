package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.ActionEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ActionEntity record);

    int insertSelective(ActionEntity record);

    ActionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActionEntity record);

    int updateByPrimaryKey(ActionEntity record);
}