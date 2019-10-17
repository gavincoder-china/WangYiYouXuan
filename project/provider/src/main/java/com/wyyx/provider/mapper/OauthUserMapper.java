package com.wyyx.provider.mapper;

import com.wyyx.provider.dto.OauthUser;

public interface OauthUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OauthUser record);

    int insertSelective(OauthUser record);

    OauthUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OauthUser record);

    int updateByPrimaryKey(OauthUser record);
}