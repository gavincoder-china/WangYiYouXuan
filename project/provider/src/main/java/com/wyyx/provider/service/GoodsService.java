package com.wyyx.provider.service;

import com.wyyx.provider.dto.ComProduct;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author kitty_zhu
 * @date 2019-10-18 18:51
 */
public interface GoodsService {

    //dkl:通过商品id查询商品信息
    ComProduct selectById(Long id);

    /**
     * @description  检测库存
     * @author Gavin
     * @date 2019-10-20 16:30

     * @return
     * @throws
     * @since
    */
    boolean checkInventory(HashMap<String, String> map);

    int goodsNiceRatio(Long productId);

   BigDecimal getVIPPrice(Long userId, BigDecimal totalPrice);




}
