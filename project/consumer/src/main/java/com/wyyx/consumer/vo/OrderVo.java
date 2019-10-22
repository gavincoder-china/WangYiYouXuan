package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kitty_zhu
 * @date 2019-10-18 16:20
 */
@Data
@ApiModel(value = "订单信息")
public class OrderVo implements Serializable {
    /**
     * 商品订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long id;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    /**
     * 收货人手机号
     */
    @ApiModelProperty(value = "收货人手机号")
    private String receiverPhone;

    /**
     * 收获人地址
     */
    @ApiModelProperty(value = "收获人地址")
    private String receiverAddress;

    private static final long serialVersionUID = 1L;
}
