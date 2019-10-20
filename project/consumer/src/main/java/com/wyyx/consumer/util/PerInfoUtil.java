package com.wyyx.consumer.util;

import org.springframework.stereotype.Component;

/**
 * @author ltl
 * @date 2019/10/20 20:12
 */
@Component
public class PerInfoUtil {

    //根据经验值返回对应的等级
    public int getLevel(int expValue) {
        /*判断等级*/
        int level = 0;
        if (0 <= expValue && expValue < 30) {
            level = 0;
            return level;
        } else if (30 <= expValue && expValue < 60) {
            level = 1;
            return level;
        } else if (60 <= expValue && expValue < 90) {
            level = 2;
            return level;
        } else {
            level = 3;
            return level;
        }
    }


}
