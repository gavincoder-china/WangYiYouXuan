package com.wyyx.provider.service;

import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.dto.OauthUser;

/**
 * @author ltl
 * @date 2019/10/17 10:24
 */
public interface ComUserService {

    //注册普通用户信息
    int register(ComUser comUser);
    // 普通用户登录
    ComUser login(String phone ,String password);

    ComUser selectByPhone(String phone);

}

