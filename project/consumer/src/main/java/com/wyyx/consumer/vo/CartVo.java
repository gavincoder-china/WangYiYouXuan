package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author chddald
 * @Date 2019/10/18
 */
@Data
@ApiModel(value = "购物车")
public class CartVo implements Serializable {
    private static final long serialVersionUID = 7237401817297422546L;

    @ApiModelProperty(value = "购物车id")
    private long id;

    @ApiModelProperty(value = "商品id")
    private long productId;

    @ApiModelProperty(value = "商品名")
    private String name;

    @ApiModelProperty(value = "商品数量")
    private  long count;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "图片地址")
    private String imgurl;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

}