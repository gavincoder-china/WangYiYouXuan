package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ProductComment;

public interface ProductCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductComment record);

    int insertSelective(ProductComment record);

    ProductComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductComment record);

    int updateByPrimaryKey(ProductComment record);
}