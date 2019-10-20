package com.wyyx.consumer.vo;

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
public class HomeVo  implements Serializable {
    private static final long serialVersionUID = 8190753692898517393L;

    private Long totalSize;

    private int startPage;

    private int pageSize;

    //分类
    private String category;

    private ArrayList<GoodsVo> list;

}
