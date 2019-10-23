package com.wyyx.consumer.util;


import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ProductCart;
import com.wyyx.provider.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author chddald
 * @Date 2019/10/23
 */
@Component
public class CartCountsIndexUtil {

    @Autowired
    private RedisUtil redisUtil;

    @Reference
    private CartService cartService;

    public int cartCounts(UserVo userVo) {

        if (!ObjectUtils.isEmpty(userVo.getUserID())) {
            //查该用户的购物车
            List<ProductCart> productCarts = cartService.queryAllByUserID(userVo.getUserID());

            return productCarts.size();
        } else {
            //查看临时购物车
            HashMap<Object, Object> cartTemp = (HashMap<Object, Object>) redisUtil.hmget(CommonContants.TEMP_CART + userVo.getTemp());

            return cartTemp.size();
        }
    }
}