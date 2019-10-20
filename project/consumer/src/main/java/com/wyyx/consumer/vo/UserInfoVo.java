package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ltl
 * @date 2019/10/19 13:46
 */
@Data
public class UserInfoVo implements Serializable {
    /*用于修改个人信息*/
    //用户的id
    private Long userId;
    //用户昵称
    private String nickName;
    //用户姓名
    private String name;
    //用户性别
    private int sex;
    //收获地址
    private String address;
    //用户生日(只可修改一次)
    private Date birthday;
}
