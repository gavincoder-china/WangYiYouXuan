package com.wyyx.provider.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class OauthUser implements Serializable {
    /**
    * 授权用户id
    */
    private Long id;

    /**
    * 用户的唯一id
    */
    private String openid;

    /**
    * 授权用户昵称
    */
    private String nickname;

    /**
    * 授权用户性别,1男,2女,0未知
    */
    private Byte sex;

    /**
    * 授权用户所在城市
    */
    private String city;

    /**
    * 授权用户所在省会
    */
    private String province;

    /**
    * 授权用户所在国家
    */
    private String country;

    /**
    * 授权用户头像图片地址
    */
    private String headimgurl;

    /**
    * 该条记录创建时间
    */
    private Date createTime;

    /**
    * 授权平台
    */
    private String platform;

    private static final long serialVersionUID = 1L;
}