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

    //dkl:查询用户购物车所有商品
    List<ProductCart> queryAllByUserID(Long userId);

    //dkl:通过productId和userId查询用户购物车中的商品
    ProductCart selectByPidAndUserId(Long productId, Long userId);

    //dkl:修改购物车中商品数量
    int updateProductCount(Long productId, Long userId, Long productCount,BigDecimal totalPrice);

    //dkl:删除商品
    int deleteProdectById(Long productId, Long userId);
}