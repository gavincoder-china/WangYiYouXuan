package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author chddald
 * @Date 2019/10/18
 */
@Data
public class CartVo implements Serializable {
    private static final long serialVersionUID = 7237401817297422546L;

    private long id;
    private long productId;
    private String name;
    private  long count;
    private String description;
    private String imgurl;
    private BigDecimal totalPrice;

}