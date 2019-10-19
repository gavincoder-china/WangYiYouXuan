package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * **********************************************************
 *
 * @Project:  缓存专用的用户对象
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-17 11:51
 * @description:
 ************************************************************/
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = -567136965701406919L;

    private Long userID;
    private String userName;
    private String phone;
    private String password;
    //是否同意,0标识不同意,其他数字表示同意
    private int isAgree = 1;
    private String temp;


}
