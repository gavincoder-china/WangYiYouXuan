package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ProductOrder;

public interface ProductOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductOrder record);

    int insertSelective(ProductOrder record);

    ProductOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductOrder record);

    int updateByPrimaryKey(ProductOrder record);
}