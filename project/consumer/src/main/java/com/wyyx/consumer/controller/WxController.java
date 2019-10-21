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
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComOauthUser;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.dto.OauthUser;
import com.wyyx.provider.service.ComOauthService;
import com.wyyx.provider.service.ComUserService;
import com.wyyx.provider.service.OauthUserService;
import com.wyyx.provider.service.PerCenterService;
import com.wyyx.provider.util.DateUtils;
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

    @Reference
    private PerCenterService perCenterService;

    //获取code
    @ApiOperation(value = "获取微信登录的链接")
    @GetMapping(value = "/getCodeUrl")
    public ReturnResult<String> getCodeUrl() {
        //重定向到微信登录的链接
        return ReturnResultUtils.returnSuccess(oauthUserService.getCodeUrl());
    }

    //用户授权后的回调方法
    @GetMapping(value = "/callBack")
    public ReturnResult callBack(String code) {
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


            //将用户信息字符串转为用户对象
            OauthUser oauthUser = JSONObject.parseObject(userInfoStr, OauthUser.class);
            //添加该用户注册的时间
            oauthUser.setCreateTime(new Date());
            //再将用户信息插入数据库
            if (1 == oauthUserService.register(oauthUser)) {  //插入成功，实现授权用户注册
                //用openid查出授权用户信息
                OauthUser user = oauthUserService.selectByOpenId(openid);

                //将用户id存到中间表中，为绑定手机做铺垫
                ComOauthUser comOauthUser = new ComOauthUser();
                comOauthUser.setOauthUserId(user.getId());
                comOauthService.insertId(comOauthUser);

                //未注册过时先将用户信息存在redis的注册组
                redisUtil.set(CommonContants.REGISTER_NAME_SPACE + openid, 1);

                return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_BIND_PHONE,
                                                       ReturnResultContants.MSG_BIND_PHONE, user.getId());
            }
        } else {
            //已经注册过的用户,存redis
            OauthUser user = oauthUserService.selectByOpenId(openid);

            //从OauthUser id获取中间表信息
            ComOauthUser comOauthUser = comOauthService.selectMiddleInfo(user.getId());

            //手机号不为空时，将用户信息存入redis中，实现登录，且三分钟过期
            if (null != comOauthUser.getPhone()) {

                ComUser comUser = comUserService.selectByPhone(comOauthUser.getPhone());

                //new插入redis的用户vo
                UserVo userVo = new UserVo();
                userVo.setUserID(comUser.getId());
                userVo.setPhone(comOauthUser.getPhone());

                String jsonString = JSONObject.toJSONString(userVo);

                redisUtil.set(CommonContants.LOGIN_NAME_SPACE + comUser.getId(), jsonString, 180);

                return ReturnResultUtils.returnSuccess(comUser.getId());

            } else {

                return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_BIND_PHONE,
                                                       ReturnResultContants.MSG_BIND_PHONE, user.getId());
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_CALL_BACK_FAIL,
                                            ReturnResultContants.MSG_CALL_BACK_FAIL);
    }

    @ApiOperation(value = "/绑定手机号")
    @GetMapping(value = "/bindPhone")
    public ReturnResult bindPhone(@ApiParam(value = "授权用户表id") @RequestParam(value = "userId") Long userId,
                                  @ApiParam(value = "要绑定的手机号") @RequestParam(value = "phone") String phone) {

        ComOauthUser comOauthUser = comOauthService.selectMiddleInfo(userId);
        //先查中间表中有无该userId
        if (null == comOauthUser.getOauthUserId()) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_REGISTER_WRONG,
                                                ReturnResultContants.MSG_REGISTER_WRONG);
        }
        if (null == comOauthUser.getPhone()) {   //若手机号为空,调用方法绑定手机

            comOauthUser.setPhone(phone);

            if (1 == comOauthService.insertPhone(comOauthUser)) {   //手机号绑定成功
                //从用户表中查数据,如果没数据,就在普通用户表中加这个数据
                ComUser comUser = comUserService.selectByPhone(phone);
                if (null == comUser) {
                    ComUser user = new ComUser();
                    user.setPhone(phone);

                    int registerResult = comUserService.register(user);

                    ComUser comUserNew = comUserService.selectByPhone(phone);

                    //new插入redis的用户vo
                    UserVo userVo = new UserVo();
                    userVo.setUserID(comUserNew.getId());
                    userVo.setPhone(comUserNew.getPhone());

                    String jsonString = JSONObject.toJSONString(userVo);

                    redisUtil.set(CommonContants.LOGIN_NAME_SPACE + comUser.getId(), jsonString, 180);

                    return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_BIND_PHONE_SUCCESS,
                                                           ReturnResultContants.MSG_BIND_PHONE_SUCCESS, comUserNew.getId());
                } else {

                    //new插入redis的用户vo
                    UserVo userVo = new UserVo();

                    userVo.setUserID(comUser.getId());
                    userVo.setPhone(comUser.getPhone());

                    String jsonString = JSONObject.toJSONString(userVo);

                    redisUtil.set(CommonContants.LOGIN_NAME_SPACE + comUser.getId(), jsonString, 180);

                    //加经验,上锁
                    if (redisUtil.lock(CommonContants.LOCK_LOGIN_EXP + comUser.getId(), 1, DateUtils.getLeftSecond())) {

                        perCenterService.updateExp(10, comUser.getId());

                    }

                    return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_BIND_PHONE_SUCCESS,
                                                           ReturnResultContants.MSG_BIND_PHONE_SUCCESS, comUser.getId());
                }
            } else {
                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_BIND_PHONE_FAIL,
                                                    ReturnResultContants.MSG_PHONE_IS_BIND);
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_BIND_PHONE_FAIL,
                                            ReturnResultContants.MSG_BIND_PHONE_FAIL);
    }
}
