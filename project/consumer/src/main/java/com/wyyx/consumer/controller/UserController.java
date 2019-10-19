package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.GetIpAddressUtil;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.UserRegisterVo;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.service.ComUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

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
@RestController
@RequestMapping(value = "/user")
public class UserController {
    /**
     * @author ltl
     */

    @Autowired
    RedisUtil redisUtil;    /*装配redis缓存工具类*/
    @Autowired
    GetIpAddressUtil getIpAddressUtil;  /*装配获取ip地址工具类*/
    @Reference
    ComUserService comUserService;


    @ApiOperation(value = "用户注册")
    @GetMapping(value = "/register")
    public ReturnResult UserRegister(@Valid UserRegisterVo userRegisterVo) {

        //先检测该用户是否注册,若redis中没有该手机号对应的值进行注册
        if (!redisUtil.hasKey(CommonContants.REGISTER_NAME_SPACE + userRegisterVo.getPhone())) {
            ComUser comUser = new ComUser();
            //将userRegisterVo的值赋给comUser
            BeanUtils.copyProperties(userRegisterVo, comUser);
            //增加该用户的注册时间
            comUser.setCreateTime(new Date());
            try {
                if (1 == comUserService.register(comUser)) {    //向数据库插入该用户的信息,实现用户的注册
                    //并将用户手机号存到redis中的注册组
                    redisUtil.set(CommonContants.REGISTER_NAME_SPACE + userRegisterVo.getPhone(), 1);

                    //再用手机号和密码去库中获取用户信息,生成userJson字符串保存在redis中
                    ComUser user = comUserService.login(userRegisterVo.getPhone(), userRegisterVo.getPassword());
                    UserVo userVo = new UserVo();
                    userVo.setUserID(user.getId());
                    userVo.setPhone(user.getPhone());

                    //将用户的唯一id作为保存在redis中的token
                    Long userToken = user.getId();
                    //将用户对象转为Json字符串
                    String userJsonStr = JSONObject.toJSONString(userVo);

                    //并在redis中存登用录户的用户的信息,实现注册完登录(三分钟过期)
                    if (redisUtil.set(CommonContants.LOGIN_NAME_SPACE + userToken, userJsonStr, 180)) {
                        //返回token
                        return ReturnResultUtils.returnSuccess(userToken);
                    }
                } else {
                    return ReturnResultUtils.returnFail(ReturnResultContants.CODE_REGISTER_WRONG, ReturnResultContants.MSG_REGISTER_WRONG);
                }
            } catch (Exception e) {
                log.error(ReturnResultContants.MSG_REGISTER_WRONG);
                e.printStackTrace();
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_REGISTER_ALREADY_EXIST, ReturnResultContants.MSG_REGISTER_ALREADY_EXIST);
    }

    @ApiOperation(value = "用户登录")
    @GetMapping(value = "/login")
    public ReturnResult UserLogin(@Valid UserVo userVo, HttpServletRequest request) {
        //未勾选"我同意",提示勾选
        if (0 == userVo.getIsAgree()) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_IS_NOT_AGREE, ReturnResultContants.MSG_IS_NOT_AGREE);
        }

        //通过手机号和密码去数据库查出用户对象
        ComUser comUser = comUserService.login(userVo.getPhone(), userVo.getPassword());
        if (null != comUser) {
             String address=CommonContants.IS_COM_IP_ADDRESS;
            //获取当前位置的ip地址
            String curIpAddr = request.getLocalAddr();
            if (!curIpAddr.equals(redisUtil.get(CommonContants.COM_IP_ADDRESS))) {  //检测是否为常用地登录
                log.error(CommonContants.NOT_COM_IP_ADDRESS);
                address=CommonContants.NOT_COM_IP_ADDRESS;
                redisUtil.set(CommonContants.COM_IP_ADDRESS,curIpAddr);
            }

            //获取用户信息并转为Json字符串
            UserVo user = new UserVo();
            user.setUserID(comUser.getId());
            user.setPhone(comUser.getPhone());
            String userStr = JSONObject.toJSONString(user);
            //将用户id作为token
            Long userToken = comUser.getId();
            //将用户信息存入redis，并设置三分钟过期
            if (redisUtil.set(CommonContants.LOGIN_NAME_SPACE + userToken, userStr,180)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("address",address);
                map.put("token",userToken.toString());
                return ReturnResultUtils.returnSuccess(map);
            }
        } else {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_LOGIN_WRONG, ReturnResultContants.MSG_WRONG_LOGIN);
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_LOGIN_WRONG, ReturnResultContants.MSG_WRONG_LOGIN);
    }
}
