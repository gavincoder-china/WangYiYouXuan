package com.wyyx.provider.util;

import com.wyyx.provider.mapper.ProductDiscountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * **********************************************************
 *
 * @Project: 计算金额  uid totalPrice
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-20 15:15
 * @description:
 ************************************************************/
@Component
public class CalculatePriceUtil {
    @Autowired
    private ProductDiscountMapper productDiscountMapper;



}
