package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.entity.TbMeeting;

public interface TbMeetingDao {
    int deleteByPrimaryKey(Long id);

    int insert(TbMeeting record);

    int insertSelective(TbMeeting record);

    TbMeeting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbMeeting record);

    int updateByPrimaryKey(TbMeeting record);
}