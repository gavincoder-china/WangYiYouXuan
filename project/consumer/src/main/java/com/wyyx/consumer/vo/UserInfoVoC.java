package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ltl
 * @date 2019/10/19 13:46
 */
@Data
@ApiModel(value = "用户基本信息")
public class UserInfoVoC implements Serializable {

    @ApiModelProperty(value = "用户id", example = "123123")
    private Long userId;

    @ApiModelProperty(value = "用户昵称", example = "小张")
    private String nickname;

    @ApiModelProperty(value = "用户姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "性别", example = "1")
    private Byte sex;

    @ApiModelProperty(value = "收获地址", example = "南京江宁")
    private String address;

}
