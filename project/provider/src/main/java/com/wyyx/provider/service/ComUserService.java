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


    //根据手机号查询用户对象
    ComUser selectByPhone(String phone);
    //更新用户为会员
    int updateRole(ComUser comUser);

    //根据用户id获取用户对象
    ComUser selectByUserId(Long userId);

    //修改用户信息
    int updateUserInfo(ComUser comUser);
}

