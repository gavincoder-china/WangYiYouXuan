package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ltl
 * @date 2019/10/17 11:22
 */
@Data
public class UserRegisterVo implements Serializable {
    
    private static final long serialVersionUID = -8425746972335815411L;
    //手机号
    private String phone;
    //密码
    private String password;
}
