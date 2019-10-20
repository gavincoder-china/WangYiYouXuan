package com.wyyx.provider.service;

import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductCart;

import java.util.List;

public interface CartService {
    //点击加入购物车后，从商品表中获取商品的信息
    ComProduct selectByPrimaryKey(Long id);

    //获取商品信息后，将商品信息插入购物车表中
    int insert(Long pID,Long userId,Long pCount);

    //查询用户购物车中的所有商品
    List<ProductCart> queryAllByUserID(Long userId);

    //通过商品id和用户id查询用户购物车中的商品
    ProductCart selectByPidAndUserId(Long pID,Long userId);

    //修改购物车中商品的数量
    int updateProductCount(Long pID,Long userId,Long pCount );

    //删除购物车商品
    int deleteProdectById(Long pid,Long userId);


}