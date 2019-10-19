package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.ComOauthUser;

public interface ComOauthUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComOauthUser record);

    int insertSelective(ComOauthUser record);

    ComOauthUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComOauthUser record);

    int updateByPrimaryKey(ComOauthUser record);

    String selectPhone(long oauthUserId);

    Long selectId();

}