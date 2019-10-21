package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ltl
 * @date 2019/10/17 11:22
 */
@Data
@ApiModel(value = "用户注册")
public class UserRegisterVo implements Serializable {
    
    private static final long serialVersionUID = -8425746972335815411L;

    @ApiModelProperty(value = "手机号",example = "13260909029")
    private String phone;

    @ApiModelProperty(value = "密码",example = "123")
    private String password;

}
