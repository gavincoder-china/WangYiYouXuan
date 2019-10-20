package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.wyyx.consumer.annotationCustom.method.RequireLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.RequireLoginParam;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.OrderVo;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.contants.OrderStatus;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.service.OrderService;
import com.wyyx.provider.service.WxService;
import com.wyyx.provider.util.wx.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-15 13:45
 * @description:
 ************************************************************/
@Slf4j
@Api(value = "微信支付")
@RestController
@RequestMapping(value = "/wx")
public class WxPayController {

    @Reference
    private WxService wxService;

    @Reference
    private OrderService orderService;


    @Autowired
    private RedisUtil redisUtil;

    @RequireLoginMethod
    @ApiOperation("订单支付")
    @PostMapping(value = "/pay")
    public ReturnResult wxPay(@Valid OrderVo orderVo,
                              @RequireLoginParam UserVo userVo) {


        ProductOrder productOrder = orderService.selectOrder(userVo.getUserID(), orderVo.getId());


        if (null != productOrder) {

            if (redisUtil.hasKey(CommonContants.ORDER_EXPIRE + orderVo.getId())) {

                //拿其收件人信息
                BeanUtils.copyProperties(orderVo, productOrder);

                productOrder.setUserId(userVo.getUserID());


                try {
                    String resultStr = wxService.wxPay(productOrder);

                    if (!StringUtils.isEmpty(resultStr)) {
                        return ReturnResultUtils.returnSuccess(resultStr);
                    } else {
                        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_CREATE_PAY_QCODE_FAIL,
                                                            ReturnResultContants.MSG_CREATE_PAY_QCODE_FAIL);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //修改订单状态
                orderService.updateOrderState(OrderStatus.ORDER_CANCELED.getoStatus(), orderVo.getId());
                //返回订单失效通知
                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_ORDER_EXPIRE,
                                                    ReturnResultContants.MSG_ORDER_EXPIRE);
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_CREATE_PAY_QCODE_FAIL,
                                            ReturnResultContants.MSG_CREATE_PAY_QCODE_FAIL);


    }


    @RequestMapping(value = "/wxPayNotify")
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream = request.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        StringBuffer sb = new StringBuffer();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        inputStream.close();

        Map<String, String> resultMap = WXPayUtil.xmlToMap(sb.toString());


        //检验并且修改订单状态
        boolean checkResult = wxService.wxPayNotify(resultMap);


        if (checkResult) {


            Map<String, String> returnMap = Maps.newHashMap();
            returnMap.put("return_code", "SUCCESS");
            returnMap.put("return_msg", "OK");

            String xml = WXPayUtil.mapToXml(returnMap);

            response.getWriter().write(xml);
        }


    }
}

