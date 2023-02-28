package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.TbWorkday;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbWorkdayDao {
    Integer selectTodayIsWorkday();


}