package com.wyyx.consumer.contants;

/**
 * **********************************************************
 *
 * @Project: 结果返回集使用的常量信息, 必须写注解, code与msg写在一起
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-09-28 22:03
 * @description:
 ************************************************************/
public final class ReturnResultContants {

    public static final int SUCCESS = 0;

    //登录失败
    public static final int CODE_LOGIN_WRONG = 250;
    public static final String MSG_WRONG_LOGIN = "登录失败";

    //用户注销失败
    public static final int CODE_DEL_WRONG = 251;
    public static final String MSG_DEL_WRONG = "用户注销失败";

    //用户注册失败
    public static final int CODE_REGISTER_WRONG = 290;
    public static final String MSG_REGISTER_WRONG = "注册失败";

    //用户注册时用户名重复
    public static final int CODE_REGISTER_ALREADY_EXIST = 291;
    public static final String MSG_REGISTER_ALREADY_EXIST = "该用户名已被注册";

    //登录过期
    public static final int CODE_LOGIN_EXPIRE = 292;
    public static final String MSG_LOGIN_EXPIRE = "登陆过期,需要重新登录";

    //未登录提示
    public static final int CODE_INTERCPTOR_LOGIN_ERROR = 293;
    public static final String MSG_INTERCPTOR_LOGIN_ERROR = "未登录,请先登录";

    //抢购成功,生成订单
    public static final int CODE_SECKILL_CREATE_ORDER = 999;
    public static final String MSG_SECKILL_CREATE_ORDER = "恭喜您抢购成功,订单正在生成中";


}
