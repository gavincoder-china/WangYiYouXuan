package com.wyyx.provider.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class UserExperience implements Serializable {
    /**
    * 经验值id
    */
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 经验值
    */
    private Integer expValue;

    /**
    * 增加/减少原因
    */
    private String reason;

    /**
    * 1增加，2减少
    */
    private Byte type;

    /**
    * 该条记录创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}