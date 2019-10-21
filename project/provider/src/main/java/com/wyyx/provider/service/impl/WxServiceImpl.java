package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.contants.OrderStatus;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.mapper.ProductOrderMapper;
import com.wyyx.provider.service.WxService;
import com.wyyx.provider.util.CommonUtil;
import com.wyyx.provider.util.UrlUtils;
import com.wyyx.provider.util.wx.WXPayUtil;
import com.wyyx.provider.util.wx.WxPayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-15 13:47
 * @description:
 ************************************************************/
@Service
@Transactional
public class WxServiceImpl implements WxService {


    @Autowired
    private WxPayModel wxPayModel;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private ComProductMapper comProductMapper;

    @Override
    public String wxPay(ProductOrder productOrder) throws Exception {
        SortedMap<String, String> param = new TreeMap<String, String>();

        param.put("appid", wxPayModel.getAppid());
        param.put("mch_id", wxPayModel.getMchid());
        param.put("nonce_str", CommonUtil.CreateUUID(32));
        param.put("body", productOrder.getName());
        param.put("out_trade_no", productOrder.getId().toString());
        // 修改价格 param.put("total_fee", String.valueOf(productOrder.getFinalPrice()));
        param.put("total_fee", String.valueOf(1));
        param.put("spbill_create_ip", "192.168.1.142");
        param.put("notify_url", wxPayModel.getNotifyurl());
        param.put("trade_type", wxPayModel.getType());

        //生成签名
        String sign = WXPayUtil.generateSignature(param, wxPayModel.getKey());
        param.put("sign", sign);


        //map转成xml

        String xml = WXPayUtil.mapToXml(param);

        //请求post

        String resultStr = UrlUtils.doPost(wxPayModel.getUnified(), xml, 5000);

        //xml转成map
        Map<String, String> resultMap = WXPayUtil.xmlToMap(resultStr);

        if (null != resultMap) {

            return resultMap.get("code_url");
        }
        return null;
    }

    @Override
    public boolean wxPayNotify(Map<String, String> resultMap) {


        if ("SUCCESS".equals(resultMap.get("return_code"))) {

            //Todo 验证签名与金额
            boolean isCheckSign = false;
            try {
                isCheckSign = WXPayUtil.checkSign(resultMap, wxPayModel.getKey());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (isCheckSign) {
                //修改订单状态
                productOrderMapper.updatestateBYidAndUserId(OrderStatus.ORDER_PAY_SUCCESS.getoStatus(),
                                                            Long.parseLong(resultMap.get("out_trade_no")));

                //减库存,加销量
                comProductMapper.updateInventoryAndSalesbyid(Long.parseLong(resultMap.get("out_trade_no")));


                return true;
            }
        }
        return false;
    }
}
