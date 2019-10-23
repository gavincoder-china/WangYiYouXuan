package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-22 13:46
 */

@Data
@ApiModel(value = "订单信息")
public class OrderVoC implements Serializable {

    /**
     * 商品订单id
     */
    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "订单状态")
    private byte state;

    @ApiModelProperty(value = "订单价格")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "订单包含的商品信息列表")
    private List goods;

    private static final long serialVersionUID = 1L;
}


