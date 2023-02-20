package com.zp.emos.wx.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_user
 * @author 
 */
@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 长期授权字符串
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像网址
     */
    private String photo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 入职日期
     */
    private Date hireDate;

    /**
     * 角色
     */
    private String role;

    /**
     * 是否是超级管理员
     */
    private Boolean root;

    /**
     * 部门编号
     */
    private Integer deptId;

    /**
     * 状态 1：在职，0：离职
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 部门名称
     */
    private String deptName;
}