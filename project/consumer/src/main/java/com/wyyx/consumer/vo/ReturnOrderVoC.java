package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-22 16:12
 */
@Data
@ApiModel(value = "我的订单")
public class ReturnOrderVoC implements Serializable {
    private static final long serialVersionUID = -7470588029213636334L;

    @ApiModelProperty(value = "总页数")
    private Long totalSize;

    @ApiModelProperty(value = "起始页")
    private int startPage;

    @ApiModelProperty(value = "每页条数")
    private int pageSize;

    @ApiModelProperty(value = "订单详情列表")
    private List<OrderVoC> orderList;

}
