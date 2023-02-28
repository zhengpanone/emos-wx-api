package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.TbHolidays;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbHolidaysDao {
    Integer selectTodayIsHolidays();

}