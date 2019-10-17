package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * **********************************************************
 *
 * @Project: 用户登录注册,积分系统等信息
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
    RedisUtil redisUtil;    /*装配redisUtil工具类*/
    @Reference
    UserService userService;
    //用户注册命名空间
    String RegisterNameSpace = CommonContants.REGISTER_NAME_SPACE;
    //用户登录命名空间
    String LoginNameSpace = CommonContants.LOGIN_NAME_SPACE;

    @ApiOperation(value = "注册")
    @GetMapping(value = "/register")
    public ReturnResult UserRegister(UserRegisterVo userRegisterVo, HttpServletRequest request) {
        //把sessionid作为userToken
        String userToken = request.getSession().getId();
        //redis中没有该用户名则插入数据库
        if (null == redisUtil.get(RegisterNameSpace + userToken)) {
            ComUser comUser = new ComUser();
            comUser.setPhone(userRegisterVo.getPhone());
            comUser.setPassword(userRegisterVo.getPassword());
            try {
                if (1 == userService.insertUser(comUser)) {
                    //注册完登录(三分钟过期)
                    redisUtil.set(LoginNameSpace + userToken, userRegisterVo.getPhone(),180);
                    return ReturnResultUtils.returnSuccess();
                }
            } catch (Exception e) {
                log.error("register error");
                e.printStackTrace();
            }
        }
        return ReturnResultUtils.returnFail(UserContants.USER_IS_LOGIN_FAIL_CODE, "该用户已存在！");
    }


}
