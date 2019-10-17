package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.SecProduct;

public interface SecProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecProduct record);

    int insertSelective(SecProduct record);

    SecProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecProduct record);

    int updateByPrimaryKey(SecProduct record);
}