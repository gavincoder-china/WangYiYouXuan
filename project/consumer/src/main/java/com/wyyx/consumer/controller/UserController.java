package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.model.UserRedisModel;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.UserRegisterVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * **********************************************************
 *
 * @Project: 用户登录注册, 积分系统等信息
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-16 16:43
 * @description:
 ************************************************************/

@Slf4j
@Api(value = "用户业务")
@Controller
@RequestMapping(value = "/user")
public class UserController {
    /**
     * @author ltl
     */

    @Autowired
    RedisUtil redisUtil;

    @Reference
    UserService userService;


    @ApiOperation(value = "注册")
    @GetMapping(value = "/register")
    public ReturnResult UserRegister(@Valid UserRegisterVo userRegisterVo, HttpServletRequest request) {


        //检测该用户是否注册
        if (null == redisUtil.get(CommonContants.REGISTER_NAME_SPACE + userRegisterVo.getPhone())) {


            ComUser comUser = new ComUser();
            BeanUtils.copyProperties(userRegisterVo, comUser);

            try {
                if (1 == userService.register(comUser)) {
                    //先存redis中的注册组
                    redisUtil.set(CommonContants.REGISTER_NAME_SPACE + userRegisterVo.getPhone(), 1);

                    //去库中再获取用户的唯一标示id,生成保存redis的userJson字符串
                    ComUser user = userService.login(userRegisterVo.getPhone(), userRegisterVo.getPassword());
                    UserRedisModel userRedisModel = new UserRedisModel();
                    userRedisModel.setUserID(user.getId());
                    userRedisModel.setPhone(user.getPhone());


                    String userJsonStr = JSONObject.toJSONString(userRedisModel);
                    //存登录用户的phone,以及用户的信息  注册完登录(三分钟过期)
                    redisUtil.set(CommonContants.LOGIN_NAME_SPACE + user.getId(), userJsonStr, 180);


                    //返回token
                    return ReturnResultUtils.returnSuccess(user.getId());
                } else {
                    return ReturnResultUtils.returnFail(ReturnResultContants.CODE_REGISTER_WRONG, ReturnResultContants.MSG_REGISTER_WRONG);
                }
            }
            catch (Exception e) {
                log.error("register error");
                e.printStackTrace();
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_REGISTER_ALREADY_EXIST, ReturnResultContants.MSG_REGISTER_ALREADY_EXIST);
    }


}
