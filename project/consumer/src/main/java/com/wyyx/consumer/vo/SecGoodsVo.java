package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-12 13:18
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "秒杀商品vo")
public class SecGoodsVo implements Serializable {
    private static final long serialVersionUID = 397543711087142855L;


    @ApiModelProperty(value = "总页数")
    private Long totalSize;

    @ApiModelProperty(value = "起始页")
    private int startPage;

    @ApiModelProperty(value = "每页条数")
    private int pageSize;

    @ApiModelProperty(value = "秒杀商品信息")
    private List<GoodsVo> list;

}
