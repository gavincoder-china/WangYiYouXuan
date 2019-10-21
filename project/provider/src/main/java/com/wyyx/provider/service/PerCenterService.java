package com.wyyx.provider.service;

import com.wyyx.provider.dto.UserExperience;
import com.wyyx.provider.dto.UserPoint;

/**
 * @author ltl
 * @date 2019/10/19 13:35
 */
public interface PerCenterService {

    //根据用户id从积分表中获取积分
    int getUserPoint(Long userId);
    //根据用户id从经验表中获取经验值
    int getUserExperience(Long userId);

    //更新积分,经验
    int updatePointsAndExperiencebyid(Integer updatedPoints,Integer updatedExperience,Long id);

    //每日登陆加经验
   int updateExp(Integer updatedExperience,Long id);

}
