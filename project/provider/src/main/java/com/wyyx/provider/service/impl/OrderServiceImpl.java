package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.mapper.ProductOrderMapper;
import com.wyyx.provider.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-18 11:15
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductOrderMapper productOrderMapper;

    //kitty_zhu：查询全部订单
    @Override
    public List<ProductOrder> selectOrderAll(int start, int offset) {
        return productOrderMapper.selectOrderAll(start, offset);
    }

    //kitty_zhu：查询订单状态 state:订单状态
    @Override
    public List<ProductOrder> selectOrderByClass(int state, int start, int offset) {

        return productOrderMapper.selectOrderByClass(state, start, offset);
    }

    //kitty_zhu：根据订单状态 模糊查询相应订单 state:订单状态
    @Override
    public List<ProductOrder> selectByGoodsName(String name, byte state, int start, int offset) {
        return productOrderMapper.selectByGoodsName(name, state, start, offset);
    }

    //kitty_zhu 逻辑删除
    @Override
    public boolean updateByPrimaryKeySelective(ProductOrder productOrder) {
        return productOrderMapper.updateByPrimaryKeySelective(productOrder) == 1 ? true : false;
    }

    //kitty_zhu:查询is_del的订单（回收站）
    @Override
    public List<ProductOrder> selectByIsDel(boolean isDelete, int start, int offset) {
        return productOrderMapper.selectByIsDel(isDelete, start, offset);
    }

    //kitty_zhu:删除回收站
    @Override
    public int delOrder(long id) {
        return productOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public boolean selectIsDel(long id) {
        ProductOrder productOrder = productOrderMapper.selectOrder(id);

        if (productOrder==null){
            return false;
        }else {
            return true;
        }

    }

    //kitty_zhu：查询订单状态
    @Override
    public  List<ProductOrder>selectOrderState(Long id) {
        return productOrderMapper.selectOrderState(id);
    }
}
