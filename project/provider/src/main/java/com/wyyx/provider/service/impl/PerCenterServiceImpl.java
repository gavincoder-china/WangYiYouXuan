package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.UserExperience;
import com.wyyx.provider.dto.UserPoint;
import com.wyyx.provider.mapper.UserExperienceMapper;
import com.wyyx.provider.mapper.UserPointMapper;
import com.wyyx.provider.service.PerCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ltl
 * @date 2019/10/19 13:36
 */
@Service
@Transactional
public class PerCenterServiceImpl implements PerCenterService {
    @Autowired
    UserPointMapper userPointMapper;
    @Autowired
    UserExperienceMapper userExperienceMapper;

    @Override
    public UserPoint getUserPoint(Long userId) {
        return userPointMapper.selectByUserId(userId);
    }

    @Override
    public UserExperience getUserExperience(Long userId) {
        return userExperienceMapper.selectByUserId(userId);
    }
}
