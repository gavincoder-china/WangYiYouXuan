package com.wyyx.provider.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class SecProduct implements Serializable {
    /**
    * 秒杀商品表
    */
    private Long id;

    /**
    * 秒杀商品名
    */
    private String name;

    /**
    * 秒杀商品图片地址
    */
    private String imgurl;

    /**
    * 秒杀商品描述信息
    */
    private String description;

    /**
    * 秒杀商品进价
    */
    private BigDecimal buyPrice;

    /**
    * 秒杀商品售价
    */
    private BigDecimal sellPrice;

    /**
    * 秒杀商品折扣(就是打几折)
    */
    private Integer discount;

    /**
    * 秒杀商品库存
    */
    private Long inventory;

    /**
    * 秒杀商品销量
    */
    private Long sales;

    /**
    * 秒杀开始时间
    */
    private Date startTime;

    /**
    * 秒杀结时间
    */
    private Date endTime;

    /**
    * 该条记录创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}