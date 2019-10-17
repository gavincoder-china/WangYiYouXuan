package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-17 12:08
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ComProductMapper comProductMapper;

    //kitty_zhu :首页展示分类商品
    @Override
    public List<ComProduct> selectAll(int num) {

        return comProductMapper.selectAll(num);
    }

    @Override
    public List<ComProduct> selectByName(String name, int start, int offset) {

        return null;
    }
}
