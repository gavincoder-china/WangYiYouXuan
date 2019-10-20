package com.wyyx.consumer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-17 06:56
 * @description:
 ************************************************************/
@Slf4j
@Api(value = "测试环境")
@RestController
@RequestMapping(value = "/test")
public class TestController {
    @ApiOperation(value = "测试")
    @GetMapping(value = "/test")
    public String test() {

        switch (OrderStatus.getByValue((byte) 1)) {
            case ORDER_CANCELED:
                return OrderStatus.ORDER_CANCELED.getoDesc();
            case ORDER_TO_PAY:
                return OrderStatus.ORDER_TO_PAY.getoDesc();
            case ORDER_PAY_SUCCESS:
                return OrderStatus.ORDER_PAY_SUCCESS.getoDesc();
            case ORDER_PAY_FAIL:
                return OrderStatus.ORDER_PAY_FAIL.getoDesc();
            case ORDER_TO_RECEIVE:
                return OrderStatus.ORDER_TO_RECEIVE.getoDesc();
            case ORDER_HAVE_RECEIVE:
                return OrderStatus.ORDER_HAVE_RECEIVE.getoDesc();
            case ORDER_TO_RETURN:
                return OrderStatus.ORDER_TO_RETURN.getoDesc();
            case ORDER_RETURN_SUCCESS:
                return OrderStatus.ORDER_RETURN_SUCCESS.getoDesc();
            case ORDER_RETURN_FAIL:
                return OrderStatus.ORDER_RETURN_FAIL.getoDesc();

        }

        return "测试成功";
    }
}
