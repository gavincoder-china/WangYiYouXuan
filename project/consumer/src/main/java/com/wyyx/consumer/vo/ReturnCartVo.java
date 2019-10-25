package com.wyyx.consumer.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import	java.io.Serializable;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-23 09:19
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "购物车信息")
public class ReturnCartVo implements  Serializable {
    private static final long serialVersionUID = -778679250919180569L;
    @ApiModelProperty(value = "购物车数量")
    private int cartNum=0;
    @ApiModelProperty(value = "购物车商品信息")
    private List<CartVo> CartGoods;
}
