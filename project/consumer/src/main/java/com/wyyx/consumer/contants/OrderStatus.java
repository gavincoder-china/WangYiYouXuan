package com.wyyx.consumer.contants;

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

    ORDER_CANCELED(0, "订单取消"),
    OREDER_TO_PAY(1, "订单待支付"),
    OREDER_PAY_SUCCESS(2, "订单已支付"),
    OREDER_PAY_FAIL(3, "订单支付失败"),
    OREDER_TO_RECEIVE(4, "待收货"),
    OREDER_HAVE_RECEICE(5, "已收货"),
    ORDER_TO_RETURN(6, "订单退款中"),
    ORDER_RETURN_SUCCESS(7, "订单退款成功"),
    ORDER_RETURN_FAIL(8, "订单退款失败");


    int oStatus;
    String oDesc;

    OrderStatus(int oStatus, String oDesc) {
        this.oStatus = oStatus;
        this.oDesc = oDesc;
    }

    public int getoStatus() {
        return oStatus;
    }

    public void setoStatus(int oStatus) {
        this.oStatus = oStatus;
    }

    public String getoDesc() {
        return oDesc;
    }

    public void setoDesc(String oDesc) {
        this.oDesc = oDesc;
    }
}
