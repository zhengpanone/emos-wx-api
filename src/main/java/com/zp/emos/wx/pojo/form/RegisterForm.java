package com.zp.emos.wx.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("用户注册表单类")
public class RegisterForm {
    @NotBlank(message = "注册码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "注册码必须是6位数字")
    @ApiModelProperty("注册码")
    private String registerCode;

    /**
     * 微信临时授权码
     */
    @NotBlank(message = "临时授权凭证不能为空")
    @ApiModelProperty("临时授权凭证")
    private String code;


    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @ApiModelProperty("用户昵称")
    private String nickname;


    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    @URL(message = "用户头像必须是图片URL地址")
    @ApiModelProperty("用户头像")
    private String photo;
}
