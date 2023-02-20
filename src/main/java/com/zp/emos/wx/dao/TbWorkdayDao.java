package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.TbWorkday;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbWorkdayDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbWorkday record);

    int insertSelective(TbWorkday record);

    TbWorkday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbWorkday record);

    int updateByPrimaryKey(TbWorkday record);
}