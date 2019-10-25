package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.RequireLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.RequireLoginParam;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.PerInfoUtilC;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.*;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComUser;
import com.wyyx.provider.service.ComUserService;
import com.wyyx.provider.service.PerCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author ltl
 * @date 2019/10/18 17:16
 */
@Api(tags = "查看个人中心1")
@RestController
@RequestMapping(value = "/perCenter1")
public class PerCenterControllerC {

    @Reference
    PerCenterService perCenterService;
    @Reference
    ComUserService comUserService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    PerInfoUtilC perInfoUtilC;

    @RequireLoginMethod
    @ApiOperation(value = "查看个人信息1")
    @GetMapping(value = "/getPerInfo1")
    public ReturnResult<PerCenterVoC> getPerInfo(@RequireLoginParam UserVo userVo) {

        //获取当前登录的用户id
        Long userId = userVo.getUserID();

        /*先根据用户id 获取当前的经验值*/
        int curExpValue = perCenterService.getUserExperience(userId);

        //调用工具类 计算当前等级
        int level = perInfoUtilC.getLevel(curExpValue);
        //调用工具类 计算下一级的经验值下限
        int nextExpValue = perInfoUtilC.getNextExpValue(level);

        if (4 == level) {
            //等级为4时更新该用户为会员
            byte role = 1;
            ComUser comUser = new ComUser();
            comUser.setId(userVo.getUserID());
            comUser.setRole(role);
            comUserService.updateRole(comUser);
        }

        //获取当前id的对象
        ComUser comUser = comUserService.selectByUserId(userId);

        /*获取积分*/
        int point = comUser.getPoints();
        /*获取用户的角色(0--非会员;1--会员,用户等级为三级即为会员;2--超级会员,可以通过充一分钱获得一个月特权或者在生日当天送一天特权)*/
        byte role = comUser.getRole();

        /**
         * String转Date: Date day = new SimpleDateFormat("yyyy-MM-dd").parse(String day)
         * Date转String: String day = new new SimpleDateFormat("yyyy-MM-dd").format(Date day)
         */
        String birthday = null;
        /*获取生日 (将date转为string)*/
        if (!ObjectUtils.isEmpty(comUser.getBirthday())) {

            birthday = new SimpleDateFormat("yyyy-MM-dd").format(comUser.getBirthday());
        }
        /*获取收货地址*/
        String address = comUser.getAddress();
        //将用户id,积分,当前经验值,下一级经验下限,角色放入个人中心对象中
        PerCenterVoC perCenterVoC = new PerCenterVoC();
        perCenterVoC.setUserId(userId);
        perCenterVoC.setUserPoint(point);
        perCenterVoC.setCurExpValue(curExpValue);
        perCenterVoC.setNextExpValue(nextExpValue);
        perCenterVoC.setRole(role);
        perCenterVoC.setBirthday(birthday);
        perCenterVoC.setAddress(address);

        //返回个人中心对象
        return ReturnResultUtils.returnSuccess(perCenterVoC);
    }

    @RequireLoginMethod
    @ApiOperation(value = "修改基本信息1")
    @PostMapping(value = "/modifyUserInfo1")
    public ReturnResult modifyUserInfo(@Valid UserInfoVoC userInfoVoC,
                                       @RequireLoginParam UserVo userVo) {
        //新建普通用户对象
        ComUser comUser = new ComUser();
        //将userInfoVo赋给comUser
        BeanUtils.copyProperties(userInfoVoC, comUser);
        //将当前登录用户的id赋给comUser
        comUser.setId(userVo.getUserID());

        if (null != comUser) {    //输入用户信息不为空时候可以修改个人信息
            if (1 == comUserService.updateUserInfo(comUser)) {
                return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_MODIFY_USER_INFO_SUCCESS,
                        ReturnResultContants.MSG_MODIFY_USER_INFO_SUCCESS);
            } else {
                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_MODIFY_USER_INFO_FAIL,
                        ReturnResultContants.MSG_MODIFY_USER_INFO_FAIL);
            }
        } else {
            return ReturnResultUtils.returnFail(677, "用户信息不能全为空！");
        }
    }

    @RequireLoginMethod
    @ApiOperation(value = "修改生日1")
    @PostMapping(value = "/modifyBirthday1")
    public ReturnResult modifyBirthday(@ApiParam(value = "新的生日,格式为 yyyy-MM-dd", required = true)
                                       @RequestParam(value = "newBirthday") String newBirthday, @RequireLoginParam UserVo userVo) {

        //设置生日的格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ComUser comUser = new ComUser();
        comUser.setId(userVo.getUserID());
        //输入的生日不为空时，将新生日插入用户对象中
        if (!StringUtils.isEmpty(newBirthday)) {
            try {
                comUser.setBirthday(simpleDateFormat.parse(newBirthday));  //设置生日,将输入的日期转换为Date类
            } catch (ParseException PE) {
                PE.printStackTrace();
            }
        } else {
            return ReturnResultUtils.returnFail(222, "新的生日信息不能为空");
        }

        //先从redis缓存中的生日命名空间查一下之前有无修改过
        if (null == redisUtil.get(CommonContants.IS_MODIFY_BIRTHDAY + userVo.getUserID())) {
            if (1 == comUserService.updateUserInfo(comUser)) {
                //修改用户信息成功后在redis中记录一下
                redisUtil.set(CommonContants.IS_MODIFY_BIRTHDAY + userVo.getUserID(), 1);
                return ReturnResultUtils.returnFail(202,
                        "修改生日成功！");
            } else {
                return ReturnResultUtils.returnFail(204,
                        "修改生日失败！");
            }
        } else {
            return ReturnResultUtils.returnFail(224,
                    "对不起，您的生日已修改过，生日只能修改一次！");
        }
    }
}
