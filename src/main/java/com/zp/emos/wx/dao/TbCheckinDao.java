package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.TbCheckin;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.Map;

@Mapper
public interface TbCheckinDao {
    Integer haveCheckin(Map<String, Object> data);

    void insert(TbCheckin checkin);


}