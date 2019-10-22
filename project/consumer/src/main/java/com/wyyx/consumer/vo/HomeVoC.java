package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-21 19:20
 * @description:
 ************************************************************/
@Data
public class HomeVoC implements Serializable {
    private static final long serialVersionUID = -3003715721059946225L;

    @ApiModelProperty(value = "总页数")
    private Long totalSize;

    @ApiModelProperty(value = "起始页")
    private int startPage;

    @ApiModelProperty(value = "每页条数")
    private int pageSize;


    @ApiModelProperty(value = "商品列表")
    private ArrayList list;

    @ApiModelProperty(value = "用户ip")
    private String ip;

    @ApiModelProperty(value = "分类数据")
    private HashMap map;

    @ApiModelProperty(value = "分类数据")
    private String defaultProduct;




}
