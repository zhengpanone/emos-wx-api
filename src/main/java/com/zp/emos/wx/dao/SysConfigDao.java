package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysConfigDao {
     List<SysConfig> selectAllParam();

}