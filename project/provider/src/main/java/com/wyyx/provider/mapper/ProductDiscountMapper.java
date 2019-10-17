package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ProductDiscount;

public interface ProductDiscountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductDiscount record);

    int insertSelective(ProductDiscount record);

    ProductDiscount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductDiscount record);

    int updateByPrimaryKey(ProductDiscount record);
}