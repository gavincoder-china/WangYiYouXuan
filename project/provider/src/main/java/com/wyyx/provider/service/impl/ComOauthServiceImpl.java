package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComOauthUser;
import com.wyyx.provider.mapper.ComOauthUserMapper;
import com.wyyx.provider.service.ComOauthService;
import com.wyyx.provider.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author ltl
 * @date 2019/10/18 11:44
 */
@Service
public class ComOauthServiceImpl implements ComOauthService {
    @Autowired
    ComOauthUserMapper comOauthUserMapper;

    @Autowired
    private IdWorker idWorker;


    @Override
    public ComOauthUser selectMiddleInfo(Long oauthUserId) {
        return comOauthUserMapper.selectAllByOauthUserId(oauthUserId);
    }

    @Override
    public int insertId(ComOauthUser comOauthUser) {
        comOauthUser.setId(idWorker.nextId());
        return comOauthUserMapper.insertSelective(comOauthUser);
    }



    @Override
    public int insertPhone(ComOauthUser comOauthUser) {



        return comOauthUserMapper.updatephoneAndCreateTimeByOauthUserId(comOauthUser.getPhone(), new Date(), comOauthUser.getOauthUserId());
    }


}
