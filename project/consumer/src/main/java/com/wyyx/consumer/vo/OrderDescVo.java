package com.wyyx.consumer.vo;

import com.wyyx.provider.dto.OrderInfo;
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
public class OrderDescVo implements Serializable {
    private static final long serialVersionUID = 7810870199434388986L;
    private String name;
    private long id;
    /**
     * 实付金额
     */
    private BigDecimal finalPrice;

    /**
     * 未优惠价格
     */
    private BigDecimal totalPrice;


    /**
     * 商品列表详情
     */
    List<OrderInfoVo> list;

}
