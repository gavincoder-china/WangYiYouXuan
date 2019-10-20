package com.wyyx.provider.service;

import com.wyyx.provider.dto.UserExperience;
import com.wyyx.provider.dto.UserPoint;

/**
 * @author ltl
 * @date 2019/10/19 13:35
 */
public interface PerCenterService {

    //根据用户id从积分表中获取积分
    UserPoint getUserPoint(Long userId);
    //根据用户id从经验表中获取经验值
    UserExperience getUserExperience(Long userId);


}
