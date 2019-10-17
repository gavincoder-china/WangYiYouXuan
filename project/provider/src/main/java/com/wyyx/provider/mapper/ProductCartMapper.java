package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ProductCart;

public interface ProductCartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductCart record);

    int insertSelective(ProductCart record);

    ProductCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCart record);

    int updateByPrimaryKey(ProductCart record);
}