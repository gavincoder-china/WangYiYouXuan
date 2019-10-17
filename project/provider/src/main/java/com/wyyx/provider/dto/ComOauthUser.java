package com.wyyx.provider.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class ComOauthUser implements Serializable {
    /**
    * 普通-授权用户中间表
    */
    private Long id;

    /**
    * 授权用户id
    */
    private Long oauthUserId;

    /**
    * 该条记录的创建时间
    */
    private Date createTime;

    /**
    * 手机号码
    */
    private String phone;

    private static final long serialVersionUID = 1L;
}