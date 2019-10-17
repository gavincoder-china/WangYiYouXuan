package com.wyyx.provider.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class UserPoint implements Serializable {
    /**
    * 用户积分表id
    */
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 用户积分
    */
    private Integer point;

    /**
    * 增加/减少原因
    */
    private String reason;

    /**
    * 1增加，2减少
    */
    private Byte type;

    /**
    * 记录生成时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}