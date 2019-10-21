package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.UserExperience;
import com.wyyx.provider.dto.UserPoint;
import com.wyyx.provider.mapper.ComUserMapper;
import com.wyyx.provider.mapper.UserExperienceMapper;
import com.wyyx.provider.mapper.UserPointMapper;
import com.wyyx.provider.service.PerCenterService;
import com.wyyx.provider.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ltl
 * @date 2019/10/19 13:36
 */
@Service
@Transactional
public class PerCenterServiceImpl implements PerCenterService {
    @Autowired
    private UserPointMapper userPointMapper;
    @Autowired
    private UserExperienceMapper userExperienceMapper;

    @Autowired
    private ComUserMapper comUserMapper;

    @Autowired
    private IdWorker idWorker;


    @Override
    public int getUserPoint(Long userId) {
        return comUserMapper.selectPointsById(userId);
    }


    @Override
    public int getUserExperience(Long userId) {

        return comUserMapper.selectExperiencebyid(userId);
    }

    @Override
    public int updatePointsAndExperiencebyid(Integer updatedPoints, Integer updatedExperience, Long id) {
        //更新积分表与经验表

        UserPoint userPoint = new UserPoint();
        userPoint.setId(idWorker.nextId());
        userPoint.setCreateTime(new Date());
        userPoint.setType((byte) 1);
        userPoint.setPoint(updatedPoints);
        userPoint.setReason(CommonContants.BUY_GOODS_EXP);
        userPointMapper.insertSelective(userPoint);


        UserExperience userExperience = new UserExperience();
        userExperience.setId(idWorker.nextId());
        userExperience.setType((byte) 1);
        userExperience.setCreateTime(new Date());
        userExperience.setExpValue(updatedExperience);
        userExperience.setReason(CommonContants.BUY_GOODS_EXP);
        userExperienceMapper.insertSelective(userExperience);

        return comUserMapper.updatePointsAndExperiencebyid(updatedPoints, updatedExperience, id);
    }

    @Override
    public int updateExp(Integer updatedExperience, Long id) {

        //经验表中加数据
        UserExperience userExperience = new UserExperience();
        userExperience.setId(idWorker.nextId());
        userExperience.setType((byte) 1);
        userExperience.setCreateTime(new Date());
        userExperience.setExpValue(updatedExperience);
        userExperience.setReason(CommonContants.LOGIN_EXP);
        userExperienceMapper.insertSelective(userExperience);

        //用户经验表中加数据

        comUserMapper.updateExperienceByid(updatedExperience, id);
        return 0;
    }
}
