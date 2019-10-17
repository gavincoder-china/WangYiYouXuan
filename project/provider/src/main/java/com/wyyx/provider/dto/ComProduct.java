package com.wyyx.provider.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class ComProduct implements Serializable {
    /**
    * 普通商品id
    */
    private Long id;

    /**
    * 普通商品名
    */
    private String name;

    /**
    * 普通商品的描述信息
    */
    private String description;

    /**
    * 普通商品的图片地址
    */
    private String imgurl;

    /**
    * 普通商品的好评率
    */
    private Integer goodratio;

    /**
    * 普通商品的进价
    */
    private BigDecimal buyPrice;

    /**
    * 普通商品的售价
    */
    private BigDecimal sellPrice;

    /**
    * 普通商品的库存
    */
    private Long inventory;

    /**
    * 普通商品的销量
    */
    private Long sales;

    /**
    * 普通商品的状态(-1代表已删除;0代表已下架;1代表已上架)
    */
    private Boolean status;

    /**
    * 该条记录创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}