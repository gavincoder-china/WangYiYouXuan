package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ComUser;

public interface ComUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUser record);

    int insertSelective(ComUser record);

    ComUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComUser record);

    int updateByPrimaryKey(ComUser record);
}