package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.UserPoint;

public interface UserPointMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPoint record);

    int insertSelective(UserPoint record);

    UserPoint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPoint record);

    int updateByPrimaryKey(UserPoint record);
    //通过用户id获取积分
    UserPoint selectByUserId(Long userId);
}