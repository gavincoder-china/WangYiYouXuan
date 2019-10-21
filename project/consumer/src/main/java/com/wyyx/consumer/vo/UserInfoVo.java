package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ltl
 * @date 2019/10/19 13:46
 */
@Data
@ApiModel(value = "用户信息")
public class UserInfoVo implements Serializable {

    @ApiModelProperty(value = "用户id", example = "123123")
    private Long userId;

    @ApiModelProperty(value = "用户昵称", example = "小张")
    private String nickName;

    @ApiModelProperty(value = "用户姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "性别", example = "1")
    private int sex;

    @ApiModelProperty(value = "收获地址", example = "南京江宁")
    private String address;

    //用户生日(只可修改一次)
    @ApiModelProperty(value = "用户生日", example = "用户生日")
    private String birthday;
}
