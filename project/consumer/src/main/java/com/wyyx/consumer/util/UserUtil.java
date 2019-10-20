package com.wyyx.consumer.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author ltl
 * @date 2019/10/20 20:26
 */
@Component
public class UserUtil {

    //计算付款后积分的方法
    public int getPayPoint(BigDecimal price) {
        BigDecimal prePoint = price.multiply(new BigDecimal("0.1"));    //积分为价格的10%
        BigDecimal finalPoint = prePoint.setScale(0, BigDecimal.ROUND_HALF_UP);  //小数点后一位值四舍五入
        return finalPoint.intValue();   //inValue()用于将BigDecimal转为int
    }

    //计算付款后经验值的方法
    public int getPayExpValue(int count, BigDecimal price) {
        BigDecimal preExpValue = price.multiply(new BigDecimal("0.5"));
        int payExpValue = count * 100 + preExpValue.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        return payExpValue;
    }

}
