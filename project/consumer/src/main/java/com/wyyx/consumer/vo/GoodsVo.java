package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @Date : 2019-10-20 17:18
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "商品详情vo")
public class GoodsVo implements Serializable {
    private static final long serialVersionUID = -8507429991795677793L;
    /**
     * 普通商品id
     */
    @ApiModelProperty(value = "普通商品id")
    private Long id;

    /**
     * 普通商品名
     */
    @ApiModelProperty(value = "普通商品名")
    private String name;

    /**
     * 普通商品的描述信息
     */
    @ApiModelProperty(value = "普通商品的描述信息")
    private String description;

    /**
     * 普通商品的图片地址
     */
    @ApiModelProperty(value = "普通商品的图片地址")
    private String imgurl;

    /**
     * 普通商品的好评率
     */
    @ApiModelProperty(value = "普通商品的好评率")
    private Integer goodratio;

    /**
     * 好评率颜色分类
     */
    @ApiModelProperty(value = "好评率颜色分类")
    private String color;

    /**
     * 普通商品的售价
     */
    @ApiModelProperty(value = "普通商品的售价")
    private BigDecimal sellPrice;

    /**
     * 普通商品的库存
     */
    @ApiModelProperty(value = "普通商品的库存")
    private Long inventory;

    /**
     * 库存文字说明
     */
    @ApiModelProperty(value = "库存情况说明")
    private String inventoryDesc;

    /**
     * 普通商品的销量
     */
    @ApiModelProperty(value = "普通商品的销量")
    private Long sales;







}
