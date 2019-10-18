package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ProductCart;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductCart record);

    int insertSelective(ProductCart record);

    ProductCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCart record);

    int updateByPrimaryKey(ProductCart record);

    //查询用户购物车所有商品
    List<ProductCart> queryAllByUserID(Long userId);

    //通过商品id查询用户购物车中的商品
    ProductCart selectByPid(Long pid);

    //修改购物车中商品数量
    int updateProductCount(Long productId, Long userId, Long productCount,BigDecimal totalPrice);

    //购物车中商品数量+1
    int addProductCount(Long pid);

    //删除商品
    int deleteProdectById(Long pid);
}