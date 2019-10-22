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




    @ApiModelProperty(value = "用户ip")
    private String ip;

    @ApiModelProperty(value = "分类数据")
    private HashMap category;

    @ApiModelProperty(value = "搜索框默认数据")
    private String defaultProduct;




}
