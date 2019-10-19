package com.wyyx.provider.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class ProductOrder implements Serializable {
    /**
     * 商品订单id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long productId;

    private String name;

    /**
     * 收货人手机号
     */
    private String receiverPhone;

    /**
     * 收获人地址
     */
    private String receiverAddress;

    /**
     * 订单状态,0取消,1待支付,2支付成功,3支付失败,4已收货,5退货中,6已退货
     */
    private Byte state;

    private Boolean isDelete;

    /**
     * 该条记录的创建时间
     */
    private Date createTime;

    /**
     * 支付方式(如微信支付)
     */
    private String payType;

    /**
     * 收货人姓名
     */
    private String receiverName;

    private BigDecimal finalPrice;

    private static final long serialVersionUID = 1L;
}