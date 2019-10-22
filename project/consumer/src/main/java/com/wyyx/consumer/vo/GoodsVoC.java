package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-21 19:51
 * @description:
 ************************************************************/
@Data
public class GoodsVoC implements Serializable {
    private static final long serialVersionUID = 1629558055041437593L;

    @ApiModelProperty(value = "默认的数据")
    private String defaultProduct;

    @ApiModelProperty(value = "起始页")
    private int startPage;

    @ApiModelProperty(value = "每页条数")
    private int pageSize;
    @ApiModelProperty(value = "总页数")
    private Long totalSize;
    @ApiModelProperty(value = "商品列表")
    private ArrayList goodsList;


}
