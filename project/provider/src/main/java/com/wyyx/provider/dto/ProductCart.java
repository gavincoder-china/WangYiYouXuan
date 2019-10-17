package com.wyyx.provider.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class ProductCart implements Serializable {
    /**
    * 商品购物车id
    */
    private Long id;

    /**
    * 商品id
    */
    private Long productId;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 商品数量
    */
    private Long productCount;

    /**
    * 商品总价
    */
    private BigDecimal totalPrice;

    /**
    * 该条记录的创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}