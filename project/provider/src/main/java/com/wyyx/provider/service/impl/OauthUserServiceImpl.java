package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.OauthUser;
import com.wyyx.provider.mapper.OauthUserMapper;
import com.wyyx.provider.service.OauthUserService;
import com.wyyx.provider.util.IdWorker;
import com.wyyx.provider.util.wx.WxLoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ltl
 * @date 2019/10/18 11:29
 */
@Service
@Transactional
public class OauthUserServiceImpl implements OauthUserService {
    @Autowired
    OauthUserMapper oauthUserMapper;
    @Autowired
    WxLoginModel wxLoginModel;
    @Autowired
    private IdWorker idWorker;

    @Override
    public int register(OauthUser oauthUser) {
        oauthUser.setId(idWorker.nextId());
        return oauthUserMapper.insertSelective(oauthUser);
    }

    @Override
    public OauthUser selectByOpenId(String openid) {

        return oauthUserMapper.selectAllByOpenid(openid);
    }

    @Override
    public String getCodeUrl() {
        return wxLoginModel.getRealUrl();
    }

    @Override
    public String getAccessTokenUrl(String code) {
        return wxLoginModel.getAccessTokenUrl(code);
    }

    @Override
    public String getUserInfoUrl(String access_token, String openid) {
        return wxLoginModel.getUserInfoUrl(access_token, openid);
    }
}
