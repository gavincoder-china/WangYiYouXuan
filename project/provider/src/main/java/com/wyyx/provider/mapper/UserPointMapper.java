package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.UserPoint;

public interface UserPointMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPoint record);

    int insertSelective(UserPoint record);

    UserPoint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPoint record);

    int updateByPrimaryKey(UserPoint record);


}