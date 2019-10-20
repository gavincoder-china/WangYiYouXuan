package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductComment;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.mapper.ProductCommentMapper;
import com.wyyx.provider.service.GoodsService;
import com.wyyx.provider.util.TotalPriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-20 16:07
 * @description:
 ************************************************************/
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private ComProductMapper comProductMapper;

    @Autowired
    private ProductCommentMapper productCommentMapper;
    @Override
    public ComProduct selectById(Long id) {
        return comProductMapper.selectById(id);
    }


    @Autowired
    private TotalPriceUtil priceUtil;

    @Override
    public boolean checkInventory(HashMap<String, String> map) {
        int size = map.size();
        final int[] count = {0};
        map.forEach((k, v) -> {

            ComProduct product = comProductMapper.selectById(Long.parseLong(k));
            long l = Long.parseLong(v);
            Long inventory = product.getInventory();
            //购买量大于库存
            if (l > inventory) {
                count[0]++;
            }
        });

        if (count[0] >= 1) {
            return false;
        }
        return true;
    }

    //好评率
    @Override
    public int goodsNiceRatio(Long productId) {

        List<ProductComment> productComments = productCommentMapper.selectByProductId(productId);
        int sumComment = productComments.stream().mapToInt(list -> list.getRateLevel()).sum();
        int sumProduct = productComments.size();
        double favorableRate = ((double) sumComment / (double) (10 * sumProduct)) * 100;
        int favorableRateInt = (int) favorableRate;
        return favorableRateInt;

    }

    @Override
    public BigDecimal getVIPPrice(Long userId, BigDecimal totalPrice) {
        return priceUtil.finalPrice(userId,totalPrice);
    }




}
