package com.wyyx.provider.util;

import com.wyyx.provider.mapper.ComUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author chddald
 * @Date 2019/10/20
 * 计算不包含运费的价格
 */
@Component
public class TotalPriceUtil {
    @Autowired
    private ComUserMapper comUserMapper;
    //没扣除运费的价格
    public BigDecimal finalPrice(Long userId, BigDecimal totalPrice) {
        //获得用户等级，判断用户是否为超级会员
        BigDecimal userRole = new BigDecimal(comUserMapper.selectRoleByUserId(userId));
        //获得用户积分
        BigDecimal userPoint = new BigDecimal(comUserMapper.selectPointsById(userId));
        //计算抵扣50%所需要的积分
        BigDecimal needPoint = totalPrice.setScale(0, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(50));
        //计算商品最终价格
        if (userRole.intValue() != 2) {
            if (userPoint.intValue() == 0) {
                BigDecimal finalTotalPrice = totalPrice;
                return finalTotalPrice;
            } else if (userPoint.intValue() > 0 || userPoint.intValue() < needPoint.intValue()) {
                BigDecimal finalTotalPrice = new BigDecimal(totalPrice.intValue() - (userPoint.intValue() / 100));
                return finalTotalPrice;
            } else if (userPoint.intValue() > needPoint.intValue()) {
                BigDecimal finalTotalPrice = new BigDecimal(totalPrice.intValue() - (needPoint.intValue() / 100));
                return finalTotalPrice;
            }
        } else {
            if (userPoint.intValue() == 0) {
                BigDecimal finalTotalPrice = totalPrice.multiply(new BigDecimal(0.98));
                return finalTotalPrice;
            } else if (userPoint.intValue() > 0 || userPoint.intValue() < needPoint.intValue()) {
                BigDecimal finalTotalPrice = new BigDecimal(totalPrice.multiply(new BigDecimal(0.98)).intValue() - (userPoint.intValue() / 100));
                return finalTotalPrice;
            } else if (userPoint.intValue() > needPoint.intValue()) {
                BigDecimal finalTotalPrice = new BigDecimal(totalPrice.multiply(new BigDecimal(0.98)).intValue() - (needPoint.intValue() / 100));
                return finalTotalPrice;
            }
        }
        return null;
    }

    //扣除运费的最终价格
    public BigDecimal goodsFinalPrice(Long userId,BigDecimal price){

        //获得用户等级
        BigDecimal userRole = new BigDecimal(comUserMapper.selectRoleByUserId(userId));
        if (userRole.intValue() == 0){
            BigDecimal finalPrice = new BigDecimal(price.intValue()+10);
            return finalPrice;
        }else if (userRole.intValue() == 1){
            BigDecimal finalPrice = new BigDecimal(price.intValue()+5);
            return finalPrice;
        }else if (userRole.intValue() == 2){
            BigDecimal finalPrice = price;
            return finalPrice;
        }
        return null;
    }


}