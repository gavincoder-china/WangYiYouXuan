package com.wyyx.provider.service;

import com.wyyx.provider.dto.OauthUser;

/**
 * @author ltl
 * @date 2019/10/18 11:28
 */
public interface OauthUserService {
    //授权用户注册
    int register(OauthUser oauthUser);
    //用openid查出授权用户
    OauthUser selectByOpenId(String openid);

    //生成获取code(授权码)的url
    String getCodeUrl();
    //生成获取access_token、openid等属性的url
    String getAccessTokenUrl(String code);
    //用access_token和openid生成获取授权用户信息的url
    String getUserInfoUrl(String access_token,String openid);
}
