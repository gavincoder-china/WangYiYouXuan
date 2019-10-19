package com.wyyx.consumer.controller;

import com.wyyx.consumer.result.ReturnResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ltl
 * @date 2019/10/18 17:16
 */
@Api(tags = "查看个人中心")
@RestController
@RequestMapping(value = "/perCenter")
public class PerCenterController {
    //获取积分
    @ApiOperation(value = "/获取积分")
    @GetMapping(value = "/getPoints")
    public ReturnResult getPoints(){

        return null;
    }
    //获取经验值
    //获取等级
    //获取等级
    //获取是否为会员
    //获取是否为超级会员
}
