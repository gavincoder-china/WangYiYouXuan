package com.wyyx.consumer.controller;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-17 14:14
 * @description:
 ************************************************************/

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComOauthUser;
import com.wyyx.provider.dto.OauthUser;
import com.wyyx.provider.service.ComOauthService;
import com.wyyx.provider.service.ComUserService;
import com.wyyx.provider.service.OauthUserService;
import com.wyyx.provider.util.UrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ltl
 */

@Api(tags = "微信登录")
@RestController
@RequestMapping(value = "/wxLogin")
public class WxController {

    @Autowired
    private RedisUtil redisUtil;
    @Reference
    private OauthUserService oauthUserService;
    @Reference
    private ComOauthService comOauthService;
    @Reference
    private ComUserService comUserService;

    //获取code
    @ApiOperation(value = "获取微信登录的链接")
    @GetMapping(value = "/getCodeUrl")
    public ReturnResult<String> getCodeUrl() {
        //重定向到微信登录的链接
        return ReturnResultUtils.returnSuccess(oauthUserService.getCodeUrl());
    }

    //用户授权后的回调方法
    @GetMapping(value = "/callBack")
    public ReturnResult<String> callBack(String code) {
        //生成获取access_token、openid等属性的url
        String accessTokenUri = oauthUserService.getAccessTokenUrl(code);
        //请求获取获取access_token、openid等属性的url
        String returnResult = UrlUtils.loadURL(accessTokenUri);

        //将返回结果转为Json对象
        JSONObject jsonObject = JSONObject.parseObject(returnResult);
        //从Json对象中取access_token和openid
        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        //用access_token和openid生成获取授权用户信息的url
        String userInfoUrl = oauthUserService.getUserInfoUrl(access_token, openid);
        //请求url，获取授权用户信息的str
        String userInfoStr = UrlUtils.loadURL(userInfoUrl);

        //先用wxToken从redis查有无注册过
        if (StringUtils.isEmpty(redisUtil.get(CommonContants.REGISTER_NAME_SPACE + openid))) {
            //未注册过时先将用户信息存在redis的注册组
            redisUtil.set(CommonContants.REGISTER_NAME_SPACE + openid, userInfoStr);

            //将用户信息字符串转为用户对象
            OauthUser oauthUser = JSONObject.parseObject(userInfoStr, OauthUser.class);
            //添加该用户注册的时间
            oauthUser.setCreateTime(new Date());
            //再将用户信息插入数据库
            if (1 == oauthUserService.register(oauthUser)) {  //插入成功，实现授权用户注册
                //用openid查出授权用户信息
                OauthUser user = oauthUserService.selectByOpenId(openid);
                //取出授权用户的id,作为存在redis的token
                Long wxToken = user.getId();

                //将用户id存到中间表中，为绑定手机做铺垫
                ComOauthUser comOauthUser = new ComOauthUser();
                comOauthUser.setOauthUserId(user.getId());
                comOauthUser.setCreateTime(new Date());
                comOauthService.insertId(comOauthUser);

                //从中间表查出手机号
                String phone = comOauthService.selectPhone(user.getId());
                if (!StringUtils.isEmpty(phone)) {    //手机号不为空时，将用户信息存入redis中，实现登录，且三分钟过期
                    redisUtil.set(CommonContants.LOGIN_NAME_SPACE + wxToken, userInfoStr);
                } else {
                    return ReturnResultUtils.returnFail(ReturnResultContants.CODE_BIND_PHONE, ReturnResultContants.MSG_BIND_PHONE);
                }
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_CALL_BACK_FAIL, ReturnResultContants.MSG_CALL_BACK_FAIL);
    }

    @ApiOperation(value = "/绑定手机号")
    @GetMapping(value = "/bindPhone")
    public ReturnResult bindPhone(@ApiParam(value = "授权用户id") @RequestParam(value = "userId") Long userId,
                                  @ApiParam(value = "要绑定的手机号") @RequestParam(value = "userId") String bindPhone) {

        //先查中间表中有无该userId
        if (null == comOauthService.selectId()) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_REGISTER_WRONG, ReturnResultContants.MSG_REGISTER_WRONG);
        }
        //根据输入的userId到中间表查询对应的手机号
        String phone = comOauthService.selectPhone(userId);
        if (StringUtils.isEmpty(phone)) {   //若手机号为空,调用方法绑定手机
            ComOauthUser comOauthUser = new ComOauthUser();
            comOauthUser.setPhone(bindPhone);
            comOauthUser.setCreateTime(new Date());
            if (1 == comOauthService.insertPhone(comOauthUser)) {   //手机号绑定成功
                return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_BIND_PHONE_SUCCESS, ReturnResultContants.MSG_BIND_PHONE_SUCCESS);
            } else {
                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_BIND_PHONE_FAIL, ReturnResultContants.MSG_PHONE_IS_BIND);
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_BIND_PHONE_FAIL, ReturnResultContants.MSG_BIND_PHONE_FAIL);
    }
}
