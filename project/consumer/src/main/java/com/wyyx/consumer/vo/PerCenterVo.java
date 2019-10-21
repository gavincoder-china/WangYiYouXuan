package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ltl
 * @date 2019/10/19 14:06
 */
@Data
@ApiModel(value = "个人中心")
public class PerCenterVo implements Serializable {
    private static final long serialVersionUID = -7212664987076927385L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户积分")
    private int userPoint;

    @ApiModelProperty(value = "用户经验值")
    private int userExperience;

    @ApiModelProperty(value = "用户角色(0-普通用户,1-会员,2-超级会员)")
    private Byte role;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    /**
     * 普通用户性别(1代表男;2代表女,0是未确定)
     */
    private Byte sex;

    /**
     * 普通用户地址
     */
    private String address;


    /**
     * 普通用户积分
     */
    private Integer points;


    private Integer experience;
    private int userRole;
}
