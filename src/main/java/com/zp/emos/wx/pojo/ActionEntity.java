package com.zp.emos.wx.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_action
 * @author 
 */
@Data
public class ActionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 行为编号
     */
    private String actionCode;

    /**
     * 行为名称
     */
    private String actionName;


}