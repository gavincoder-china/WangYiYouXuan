package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.UserExperience;

public interface UserExperienceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserExperience record);

    int insertSelective(UserExperience record);

    UserExperience selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserExperience record);

    int updateByPrimaryKey(UserExperience record);

    //根据经验值获取用户经验值等信息
    UserExperience selectByUserId(Long userId);
}