package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-21 07:49
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "商品信息")
public class IndexVo implements Serializable {
    private static final long serialVersionUID = 7825908223560929834L;
    @ApiModelProperty(value = "临时token")
    private String tempToken;
    @ApiModelProperty(value = "分类信息")
    private HashMap map;
    @ApiModelProperty(value = "商品列表")
    private List list;

}
