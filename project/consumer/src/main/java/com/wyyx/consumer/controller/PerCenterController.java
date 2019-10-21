package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.RequireLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.RequireLoginParam;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.PerInfoUtil;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.PerCenterVo;
import com.wyyx.consumer.vo.UserInfoVo;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.service.ComUserService;
import com.wyyx.provider.service.PerCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    PerInfoUtil perInfoUtil;

    @RequireLoginMethod
    @ApiOperation(value = "查看个人信息")
    @GetMapping(value = "/getPerInfo")
    public ReturnResult getPerInfo(@RequireLoginParam UserVo userVo) {

        Long userId = userVo.getUserID();
        /*获取积分*/
        int point = perCenterService.getUserPoint(userId);

        /*获取经验值*/
        int expValue = perCenterService.getUserExperience(userId);

        if (4 == perInfoUtil.getLevel(expValue)) {  //调用计算等级的工具类计算经验值对应的等级
            //等级为3时更新该用户为会员
            ComUser comUser = new ComUser();
            comUser.setId(userId);
            byte role = 1;
            comUser.setRole(role);
            comUserService.updateRole(comUser);
        }

        /*获取用户的角色(0--非会员;1--会员,用户等级为三级即为会员;2--超级会员,可以通过充一分钱获得一个月特权或者在生日当天送一天特权)*/
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

    @RequireLoginMethod
    @ApiOperation(value = "修改个人信息")
    @GetMapping(value = "/modifyUserInfo")
    public ReturnResult modifyUserInfo(@Valid UserInfoVo userInfoVo,
                                       @RequireLoginParam UserVo userVo) {
        //新建普通用户对象
        ComUser comUser = new ComUser();
        //将userInfoVo赋给comUser
        BeanUtils.copyProperties(userInfoVo, comUser);
        Date date = new Date();
        //修改日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!ObjectUtils.isEmpty(userInfoVo.getBirthday())) {

            try {
                date = dateFormat.parse(userInfoVo.getBirthday());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        comUser.setBirthday(date);


        comUser.setId(userVo.getUserID());

        if (null != comUser) {    //输入用户信息不为空时候可以修改个人信息

            if (null == redisUtil.get(CommonContants.IS_MODIFY_BIRTHDAY + userInfoVo.getUserId())) {

                comUserService.updateUserInfo(comUser);  //修改用户信息成功后在redis中记录一下
                redisUtil.set(CommonContants.IS_MODIFY_BIRTHDAY + userInfoVo.getUserId(), 1);

                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_MODIFY_USER_INFO_SUCCESS,
                                                    ReturnResultContants.MSG_MODIFY_USER_INFO_SUCCESS);
            } else {

                comUser.setBirthday(null);

                comUserService.updateUserInfo(comUser);   //修改用户信息成功后在redis中记录一下
                redisUtil.set(CommonContants.IS_MODIFY_BIRTHDAY + userInfoVo.getUserId(), 1);

                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_MODIFY_USER_INFO_SUCCESS,
                                                    ReturnResultContants.MSG_MODIFY_USER_INFO_SUCCESS);
            }
        } else {
            return ReturnResultUtils.returnFail(672, "用户信息不能为空！");
        }
    }
}
