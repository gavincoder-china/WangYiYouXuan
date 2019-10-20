package com.wyyx.provider.contants;

import java.util.Scanner;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-18 13:53
 * @description:
 ************************************************************/

public enum OrderStatus {

    ORDER_CANCELED((byte)0, "订单取消"),
    ORDER_TO_PAY((byte)1, "订单待支付"),
    ORDER_PAY_SUCCESS((byte)2, "订单已支付"),
    ORDER_PAY_FAIL((byte)3, "订单支付失败"),
    ORDER_TO_RECEIVE((byte)4, "待收货"),
    ORDER_HAVE_RECEIVE((byte)5, "已收货"),
    ORDER_TO_RETURN((byte)6, "订单退款中"),
    ORDER_RETURN_SUCCESS((byte)7, "订单退款成功"),
    ORDER_RETURN_FAIL((byte)8, "订单退款失败");


    Byte oStatus;
    String oDesc;



    public static OrderStatus getByValue(Byte value){
        for(OrderStatus transactType : values()){
            if (transactType.getoStatus().equals(value)) {
                return transactType;

            }
        }
        return null;
    }

    OrderStatus(Byte oStatus, String oDesc) {
        this.oStatus = oStatus;
        this.oDesc = oDesc;
    }

    public Byte getoStatus() {
        return oStatus;
    }

    public void setoStatus(Byte oStatus) {
        this.oStatus = oStatus;
    }

    public String getoDesc() {
        return oDesc;
    }

    public void setoDesc(String oDesc) {
        this.oDesc = oDesc;
    }
}
