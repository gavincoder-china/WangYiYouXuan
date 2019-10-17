package com.wyyx.provider.service;

import com.wyyx.provider.dto.ComUser;

/**
 * @author ltl
 * @date 2019/10/17 10:24
 */
public interface UserService {

    //插入用户信息
    int register(ComUser comUser);

    // 用户登录
    ComUser login(String phone ,String password);



}

