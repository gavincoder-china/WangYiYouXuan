package com.wyyx.provider.service;



import com.wyyx.provider.dto.ProductOrder;

import java.util.Map;

public interface WxService {

    String wxPay(ProductOrder productOrder) throws Exception;

    boolean wxPayNotify(Map<String, String> resultMap) ;
}
