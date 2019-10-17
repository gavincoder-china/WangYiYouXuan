package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.model.UserRedisModel;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.service.CartService;
import com.wyyx.provider.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project: 商品信息,购物车信息
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-16 16:44
 * @description:
 ************************************************************/
@Slf4j
@Api(value = "购物业务")
@RestController
@RequestMapping(value = "/shop")
public class ShopController {
    @Autowired
    private RedisUtils redisUtils;

    @Reference
    private CartService cartService;
    /**
     * @Author : chddald
     */
    @ApiOperation("商品选购")
    @GetMapping(value = "addCart")
    public ReturnResult addCart(@Valid UserRedisModel userRedisModel){
        //判断用户是否登录
        boolean userLogin = null == redisUtils.get(CommonContants.LOGIN_NAME_SPACE + userRedisModel.getUserName()) ? false : true;
        if(! userLogin){

        }
        return null;
    }

}
