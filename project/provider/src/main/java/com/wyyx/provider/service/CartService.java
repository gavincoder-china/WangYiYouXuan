package com.wyyx.provider.service;

import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductCart;

public interface CartService {
    //点击加入购物车后，从商品表中获取商品的信息
    ComProduct selectByPrimaryKey(Long id);

    //获取商品信息后，将商品信息插入购物车表中
    int insert(ProductCart record);
}