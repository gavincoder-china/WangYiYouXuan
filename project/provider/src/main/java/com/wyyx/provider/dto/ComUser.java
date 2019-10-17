package com.wyyx.provider.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class ComUser implements Serializable {
    /**
    * 普通用户id
    */
    private Long id;

    /**
    * 普通用户手机号
    */
    private String phone;

    /**
    * 普通用户密码
    */
    private String password;

    /**
    * 普通用户昵称
    */
    private String nickname;

    /**
    * 普通用户姓名
    */
    private String name;

    /**
    * 普通用户性别(1代表男;2代表女,0是未确定)
    */
    private Boolean sex;

    /**
    * 普通用户地址
    */
    private String address;

    /**
    * 普通用户生日
    */
    private Date birthday;

    /**
    * 普通用户积分
    */
    private String points;

    /**
    * 普通用户角色(0代表非会员;1代表会员;2代表超级会员)
    */
    private Boolean role;

    /**
    * 普通用户创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}