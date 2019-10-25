package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
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
 * @Date : 2019-10-21 11:22
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "订单信息")
public class ReturnOrderVo implements Serializable {
    private static final long serialVersionUID = 966883636330222424L;
    @ApiModelProperty(value = "总条数")
    private Long totalSize;
    @ApiModelProperty(value = "起始页")
    private int startPage;
    @ApiModelProperty(value = "每页条数")
    private int pageSize;
    @ApiModelProperty(value = "订单内商品信息")
    private ArrayList list;

}
