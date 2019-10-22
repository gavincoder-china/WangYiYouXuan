package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author kitty_zhu
 * @date 2019-10-17 12:08
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ComProductMapper comProductMapper;

    //kitty_zhu :首页展示分类商品
    @Override
    public List<ComProduct> selectAll(int num) {

        return comProductMapper.selectAll(num);
    }


    @Override
    public ComProduct randomProduct() {
        List<ComProduct> products = selectAll(1);
        Random random = new Random();
        ComProduct product = products.get(random.nextInt(products.size()));

        return product;
    }

    //kitty_zhu :根据商品类型查询商品
    @Override
    public List<ComProduct> selectByClass(int p_type, int start, int offset) {
        return comProductMapper.selectByClass(p_type, start, offset);
    }

    @Override
    public long selectByClassCount(int p_type) {
        return comProductMapper.selectByClassCount(p_type);
    }

    //kitty_zhu : 模糊查询
    @Override
    public List<ComProduct> selectByName(String name, int start, int offset) {

        return comProductMapper.selectByName(name, start, offset);
    }

    @Override
    public long selectByNameCount(String name) {

        return comProductMapper.selectByNameCount(name);
    }

    @Override
    public ComProduct selectByPrimaryKey(Long id) {

        return comProductMapper.selectByPrimaryKey(id);
    }

    //kitty_zhu :搜索框最火爆商品
    @Override
    public List<ComProduct> selectByHot() {
        return comProductMapper.selectByHot();

    }

    @Override
    public List<ComProduct> selectAllC(int start, int offset) {
        return comProductMapper.selectAllC(start, offset);
    }

    @Override
    public long selectAllCCount() {
        return comProductMapper.selectAllCCount();
    }


}
