package com.wyyx.provider.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class ProductDiscount implements Serializable {
    /**
    * 优惠表id
    */
    private Long id;

    /**
    * 商品id
    */
    private Long productId;

    /**
    * 折扣(打几折)
    */
    private Integer discount;

    /**
    * 优惠原因
    */
    private String discountReason;

    /**
    * 优惠状态(0不优惠;1优惠;2即将有优惠)
    */
    private Byte state;

    /**
    * 优惠开始时间
    */
    private Date beginTime;

    /**
    * 优惠结束时间
    */
    private Date endTime;

    /**
    * 该条记录创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}