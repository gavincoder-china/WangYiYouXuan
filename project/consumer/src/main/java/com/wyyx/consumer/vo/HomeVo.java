package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kitty_zhu
 * @date 2019-10-17 12:11
 * 网页首页的分组商品信息
 */
@Data
@ApiModel(value = "网页首页的分组商品信息")
public class HomeVo  implements Serializable {
    private static final long serialVersionUID = 8190753692898517393L;


    @ApiModelProperty(value = "总页数")
    private Long totalSize;

    @ApiModelProperty(value = "起始页")
    private int startPage;

    @ApiModelProperty(value = "每页条数")
    private int pageSize;

    //分类
    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "商品列表")
    private ArrayList<GoodsVo> list;

}
