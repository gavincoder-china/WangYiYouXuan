package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ltl
 * @date 2019/10/19 14:06
 */
@ApiModel(value = "个人中心展示内容")
@Data
public class PerCenterVoC implements Serializable {
    private static final long serialVersionUID = -7212664987076927385L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户积分")
    private int userPoint;

    @ApiModelProperty(value = "用户当前的经验值")
    private int curExpValue;

    @ApiModelProperty(value = "下一级的经验值")
    private int nextExpValue;

    @ApiModelProperty(value = "用户角色(0-普通用户,1-会员,2-超级会员)")
    private Byte role;

    @ApiModelProperty(value = "用户生日")
    private String  birthday;

    @ApiModelProperty(value = "收货地址")
    private String address;



}
