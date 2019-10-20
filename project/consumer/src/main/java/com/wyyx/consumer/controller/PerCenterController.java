package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.vo.PerCenterVo;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.dto.UserExperience;
import com.wyyx.provider.dto.UserPoint;
import com.wyyx.provider.service.ComUserService;
import com.wyyx.provider.service.PerCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ltl
 * @date 2019/10/18 17:16
 */
@Api(tags = "查看个人中心")
@RestController
@RequestMapping(value = "/perCenter")
public class PerCenterController {

    @Reference
    PerCenterService perCenterService;
    @Reference
    ComUserService comUserService;

    @ApiOperation(value = "/查看个人信息")
    @GetMapping(value = "/getPerInfo")
    public ReturnResult getPerInfo(@ApiParam(value = "用户id") @RequestParam(value = "userId") Long userId) {

        /*获取积分*/
        //通过用户id获取到用户积分对象
        UserPoint userpoint = perCenterService.getUserPoint(userId);
        //从用户积分对象中获取到用户的积分
        int point = userpoint.getPoint();

        /*获取经验值*/
        //通过用户id获取用户经验对象
        UserExperience userExperience = perCenterService.getUserExperience(userId);
        int expValue = userExperience.getExpValue();

        /*判断等级*/
        int level = 0;
        if(0<=expValue&&expValue<30){
            level = 0;
        }else if(30<=expValue&&expValue<60){
            level = 1;
        }else if(60<=expValue&&expValue<90){
            level = 2;
        }else{
            level= 3;
        }

        if(3 == level){
            ComUser comUser = new ComUser();
            comUser.setId(userId);
            byte role = 1;
            comUser.setRole(role);
            //更新该用户为会员
            comUserService.updateRole(comUser);
        }

        /*获取是否为会员*/
        /*获取是否为超级会员 ,超级会员可以通过冲一分钱获得30天特权或者在生日当天赠送一天特权*/
        ComUser comUser = comUserService.selectByUserId(userId);
        int role = comUser.getRole();

        //将积分、经验值、角色放入个人中心对象中
        PerCenterVo perCenterVo = new PerCenterVo();
        perCenterVo.setUserId(userId);
        perCenterVo.setUserPoint(point);
        perCenterVo.setUserExperience(expValue);
        perCenterVo.setUserRole(role);

        return ReturnResultUtils.returnSuccess(perCenterVo);
    }
}
