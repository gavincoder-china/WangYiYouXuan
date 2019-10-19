package com.wyyx.consumer.controller;

import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.provider.dto.ProductCart;
import com.wyyx.provider.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************************
 *
 * @Project:  支付业务,获取支付码,回调修改订单信息
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-16 16:46
 * @description:
 ************************************************************/
@Slf4j
@Api(value = "支付业务")
@RestController
@RequestMapping(value = "/pay")
public class PayController {
    /**
     * @Author chddald
     * @Date 2019/10/17
     */
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CartService cartService;

    @ApiOperation("商品选购")
    @GetMapping(value = "/chooseToPay")
    public ReturnResult chooseToPay(@ApiParam(value = "商品Id") @RequestParam(value = "pID") Long pid,
                                    @ApiParam(value = "用户Id") @RequestParam(value = "userId")Long userId){
        //通过pId和userId查询用户购物车信息
        ProductCart productCart = cartService.selectByPidAndUserId(pid, userId);
        //通过pId从商品表中查出商品的数据（img，name，price...）
        cartService.
    }
}
