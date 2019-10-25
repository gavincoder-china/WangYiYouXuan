package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-21 19:28
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "商品信息")
public class IndexGoodsVo implements Serializable {
    private static final long serialVersionUID = -6665209936433259845L;

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

}
