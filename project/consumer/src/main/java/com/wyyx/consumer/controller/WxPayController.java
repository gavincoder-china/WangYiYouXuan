package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gavin.provider.model.OrderInfo;
import com.gavin.provider.service.WxService;
import com.gavin.provider.util.wx.WXPayUtil;
import com.google.common.collect.Maps;
import com.wyyx.provider.service.WxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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


  //  private WxPayModel wxPayModel;

    @ApiOperation(value = "支付")
    @PostMapping(value = "/pay")
    public String wxPay(@Valid OrderInfo orderInfo) throws Exception {

        String resultStr = wxService.wxPay(orderInfo);


        if (!StringUtils.isEmpty(resultStr)) {
            return resultStr;
        } else {
            return "no";
        }
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

