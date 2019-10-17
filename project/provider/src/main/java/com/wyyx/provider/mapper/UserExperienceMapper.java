package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.UserExperience;

public interface UserExperienceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserExperience record);

    int insertSelective(UserExperience record);

    UserExperience selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserExperience record);

    int updateByPrimaryKey(UserExperience record);
}