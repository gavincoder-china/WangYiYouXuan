package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.mapper.ComUserMapper;
import com.wyyx.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ltl
 * @date 2019/10/17 12:13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ComUserMapper comUserMapper;    /*装配普通用户的mapper接口*/

    @Override
    public int insertUser(ComUser comUser) {
        return comUserMapper.insertSelective(comUser);
    }

}
