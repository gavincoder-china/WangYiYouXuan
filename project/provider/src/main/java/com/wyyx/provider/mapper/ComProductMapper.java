package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ComProduct;

public interface ComProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComProduct record);

    int insertSelective(ComProduct record);

    ComProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComProduct record);

    int updateByPrimaryKey(ComProduct record);
}