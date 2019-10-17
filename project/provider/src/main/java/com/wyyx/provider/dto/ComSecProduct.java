package com.wyyx.provider.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class ComSecProduct implements Serializable {
    /**
    * 普通-秒杀商品中间表
    */
    private Long id;

    /**
    * 普通商品id
    */
    private Long comProductId;

    /**
    * 秒杀商品id
    */
    private Long secProductId;

    /**
    * 该条记录的创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}