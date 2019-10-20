package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kitty_zhu
 * @date 2019-10-18 16:20
 */
@Data
public class OrderVo implements Serializable {
    /**
     * 商品订单id
     */
    private Long id;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人手机号
     */
    private String receiverPhone;

    /**
     * 收获人地址
     */
    private String receiverAddress;




    private static final long serialVersionUID = 1L;
}
