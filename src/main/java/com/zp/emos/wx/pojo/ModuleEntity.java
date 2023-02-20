package com.zp.emos.wx.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_module
 * @author 
 */
@Data
public class ModuleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 模块编号
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;


}