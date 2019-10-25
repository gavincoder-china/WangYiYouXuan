package com.wyyx.consumer.util;

import org.springframework.stereotype.Component;

/**
 * @author ltl
 * @date
 **/
@Component
public class PerInfoUtilC {

    //根据经验值返回对应的等级
    public int getLevel(int expValue) {/*判断等级*/
        int level = 0;
        if (0 <= expValue && expValue < 30) {
            level = 0;
            return level;
        } else if (30 <= expValue && expValue < 90) {
            level = 1;
            return level;
        } else if (90 <= expValue && expValue < 180) {
            level = 2;
            return level;
        } else if (180 <= expValue && expValue < 300) {
            level = 3;
            return level;
        } else if (300 <= expValue) {
            level = 4;
            return level;
        }
        return level;
    }

    //根据等级返回下一级的值
    public int getNextExpValue(int level) {
        int nextExpValue = 0;
        if (0 == level) {
            nextExpValue = 30;
            return nextExpValue;
        } else if (1 == level) {
            nextExpValue = 90;
            return nextExpValue;
        } else if (2 == level) {
            nextExpValue = 180;
            return nextExpValue;
        } else if (3 == level) {
            nextExpValue = 300;
            return nextExpValue;
        } else if(4 == level){
            nextExpValue = 9999; //超过三级后经验值不再增加
            return nextExpValue;
        }
        return nextExpValue;
    }
}
