package com.zp.emos.wx.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_workday
 * @author 
 */
@Data
public class TbWorkday implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 日期
     */
    private Date date;

    private static final long serialVersionUID = 1L;
}