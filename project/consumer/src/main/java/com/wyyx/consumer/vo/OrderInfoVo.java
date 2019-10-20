package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-20 16:00
 * @description:
 ************************************************************/
@Data
public class OrderInfoVo implements Serializable {
    private static final long serialVersionUID = 607372233423911046L;

    /**
     * 普通商品名
     */
    private String name;

    /**
     * 普通商品的描述信息
     */
    private String description;

    /**
     * 普通商品的图片地址
     */
    private String imgurl;

    /**
     * 普通商品的售价
     */
    private BigDecimal sellPrice;

    /**
     * 购买数量
     */
    private Long count;


}
