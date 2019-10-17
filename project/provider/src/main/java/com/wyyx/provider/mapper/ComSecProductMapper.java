package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ComSecProduct;

public interface ComSecProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComSecProduct record);

    int insertSelective(ComSecProduct record);

    ComSecProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComSecProduct record);

    int updateByPrimaryKey(ComSecProduct record);
}