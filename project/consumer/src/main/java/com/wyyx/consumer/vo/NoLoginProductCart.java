package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author chddald
 * @Date 2019/10/17
 */
@Data
@ApiModel(value = "未登录购物车")
public class NoLoginProductCart implements Serializable{
    private static final long serialVersionUID = -3883521461490012923L;

    /**
     * 商品购物车id
     */
    @ApiModelProperty(value = "商品购物车id")
    private Long id;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long productId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Long productCount;

    /**
     * 商品总价
     */
    @ApiModelProperty(value = "商品总价")
    private BigDecimal totalPrice;

    /**
     * 该条记录的创建时间
     */
    @ApiModelProperty(value = "该条记录的创建时间")
    private Date createTime;
}