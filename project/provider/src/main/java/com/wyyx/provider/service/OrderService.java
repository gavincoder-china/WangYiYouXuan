package com.wyyx.provider.service;

import com.wyyx.provider.dto.ProductComment;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.mapper.ProductOrderMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-18 11:15
 */
public interface OrderService {

    //kitty_zhu：查询全部订单
    List<ProductOrder> selectOrderAll(int start, int offset);

    //kitty_zhu：查询订单状态 state:订单状态
    List<ProductOrder> selectOrderByClass(int state, int start, int offset);

    //kitty_zhu：根据商品名称查询订单
    List<ProductOrder> selectByGoodsName(String name, byte state, int start, int offset);

    //kitty_zhu:逻辑删除
    boolean updateByPrimaryKeySelective(ProductOrder productOrder);

    //kitty_zhu:查询is_del的订单（回收站）
    List<ProductOrder> selectByIsDel(boolean isDelete, int start, int offset);

    //kitty_zhu:删除订单
    int delOrder(long id);

    //kitty_zhu:查询isdel
    boolean selectIsDel(long id);

    //kitty_zhu：查询订单状态
    byte selectOrderState(Long id);
    //kitty_zhu：插入用户评价
    int insertSelective(ProductComment productComment);
}
