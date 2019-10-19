package com.wyyx.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComOauthUser;

/**
 * @author ltl
 * @date 2019/10/18 11:44
 */
@Service
public interface ComOauthService {
    //根据授权用户的oauthUserId从中间表中获取手机号
    String selectPhone(Long oauthUserId);
    //注册授权用户后向中间表中插入授权用户的id
    public int insertId(ComOauthUser comOauthUser);
    //查询userId
    Long selectId();
    //绑定手机号
    int insertPhone(ComOauthUser comOauthUser);
}
