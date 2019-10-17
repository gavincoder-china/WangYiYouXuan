package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author chddald
 * @Date 2019/10/17
 */
@Data
public class NoLoginProductCart implements Serializable{
    private static final long serialVersionUID = -3883521461490012923L;

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
}