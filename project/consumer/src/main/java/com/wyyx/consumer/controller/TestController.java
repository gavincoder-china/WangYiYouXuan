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
        return "测试成功";
    }
}
