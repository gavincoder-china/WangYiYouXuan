//package com.wyyx.provider.service.impl;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.wyyx.provider.dto.ComOauthUser;
//import com.wyyx.provider.mapper.ComOauthUserMapper;
//import com.wyyx.provider.service.ComOauthService;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author ltl
// * @date 2019/10/18 11:44
// */
//@Service
//public class ComOauthServiceImpl implements ComOauthService {
//    @Autowired
//    ComOauthUserMapper comOauthUserMapper;
//
//    @Override
//    public String selectPhone(Long oauthUserId) {
//        return comOauthUserMapper.selectPhone(oauthUserId);
//    }
//
//    @Override
//    public int insertId(ComOauthUser comOauthUser) {
//        return comOauthUserMapper.insertSelective(comOauthUser);
//    }
//
//    @Override
//    public Long selectId() {
//        return comOauthUserMapper.selectId();
//    }
//
//    @Override
//    public int insertPhone(ComOauthUser comOauthUser) {
//        return comOauthUserMapper.insertSelective(comOauthUser);
//    }
//
//
//}
