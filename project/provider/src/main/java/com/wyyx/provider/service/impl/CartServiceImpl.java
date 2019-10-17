package com.wyyx.provider.service.impl;

import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductCart;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.mapper.ProductCartMapper;
import com.wyyx.provider.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author chddald
 * @Date 2019/10/17
 */
public class CartServiceImpl implements CartService{
    @Autowired
    private ComProductMapper comProductMapper;

    @Autowired
    private ProductCartMapper productCartMapper;

    @Override
    public ComProduct selectByPrimaryKey(Long id) {
        return comProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(ProductCart record) {
        return productCartMapper.insert(record);
    }
}