package com.zp.emos.wx.dao;

import com.zp.emos.wx.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbFaceModelDao {
    String searchFaceModel(int userId);
    void insert(TbFaceModel tbFaceModel);

    int deleteFaceModel(int userId);

}