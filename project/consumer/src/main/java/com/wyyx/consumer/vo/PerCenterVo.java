package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ltl
 * @date 2019/10/19 14:06
 */
@Data
public class PerCenterVo implements Serializable {
    //用户id
    private Long userId;
    //用户的积分
    private int userPoint;
    //用户的经验值
    private int userExperience;
    //用户的角色(0代表普通用户，1的代表会员,2代表超级会员)
    private int userRole;
}
