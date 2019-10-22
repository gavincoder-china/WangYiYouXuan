package com.wyyx.provider.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kitty_zhu
 * @date 2019-10-22 16:54
 */
@Data
public class OrderGoodsInfo implements Serializable {
    private static final long serialVersionUID = 5468079540455347917L;

    private long id;

    private String name;

    private String imgUrl;

    private long count;

    private BigDecimal price;

    private BigDecimal totalPrice;
}
