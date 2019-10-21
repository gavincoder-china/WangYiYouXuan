package com.wyyx.consumer.vo;

import com.wyyx.provider.dto.OrderInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project: 订单详情描述
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-20 15:55
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "订单详情vo")
public class OrderDescVo implements Serializable {
    private static final long serialVersionUID = 7810870199434388986L;

    @ApiModelProperty(value = "订单描述")
    private String name;

    @ApiModelProperty(value = "订单号")
    private long id;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal finalPrice;

    @ApiModelProperty(value = "未优惠价格")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "商品列表详情")
    List<OrderInfoVo> list;

}
