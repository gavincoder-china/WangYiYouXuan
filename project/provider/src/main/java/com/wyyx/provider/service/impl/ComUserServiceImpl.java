package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.mapper.ComUserMapper;
import com.wyyx.provider.service.ComUserService;
import com.wyyx.provider.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ltl
 * @date 2019/10/17 12:13
 */
@Service
@Transactional
public class ComUserServiceImpl implements ComUserService {
    @Autowired
    ComUserMapper comUserMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private IdWorker idWorker;

    @Override
    public int register(ComUser comUser) {

        //设置加密
        comUser.setPassword(encoder.encode(comUser.getPassword()));
        //设置分布式id
        comUser.setId(idWorker.nextId());

        return comUserMapper.insertSelective(comUser);
    }

    @Override
    public ComUser login(String phone, String password) {

        ComUser comUser = comUserMapper.selectByPhone(phone);

        //密码匹配
        if (null != comUser && encoder.matches(password, comUser.getPassword())) {
            return comUser;
        }
        return null;
    }


}
