//package com.wyyx.provider.service.impl;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.wyyx.provider.dto.OauthUser;
//import com.wyyx.provider.mapper.OauthUserMapper;
//import com.wyyx.provider.service.OauthUserService;
//import com.wyyx.provider.util.wx.WxLoginModel;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author ltl
// * @date 2019/10/18 11:29
// */
//@Service
//public class OauthUserServiceImpl implements OauthUserService {
//    @Autowired
//    OauthUserMapper oauthUserMapper;
//    @Autowired
//    WxLoginModel wxLoginModel;
//
//    @Override
//    public int register(OauthUser oauthUser) {
//        return oauthUserMapper.insertSelective(oauthUser);
//    }
//
//    @Override
//    public OauthUser selectByOpenId(String openid) {
//        return oauthUserMapper.selectByOpenid(openid);
//    }
//
//    @Override
//    public String getCodeUrl() {
//        return wxLoginModel.getRealUrl();
//    }
//
//    @Override
//    public String getAccessTokenUrl(String code) {
//        return wxLoginModel.getAccessTokenUrl(code);
//    }
//
//    @Override
//    public String getUserInfoUrl(String access_token, String openid) {
//        return wxLoginModel.getUserInfoUrl(access_token, openid);
//    }
//}
