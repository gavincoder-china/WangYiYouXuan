package com.wyyx.provider.service;

import com.wyyx.provider.dto.OrderInfo;
import com.wyyx.provider.dto.ProductComment;
import com.wyyx.provider.dto.ProductOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.zookeeper.data.Id;

import java.util.HashMap;
import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-18 11:15
 */
public interface OrderService {

    //kitty_zhu：查询全部订单
    List<ProductOrder> selectOrderAll(long uId,int start, int offset);

    //kitty_zhu：查询订单状态 state:订单状态
    List<ProductOrder> selectOrderByClass(long uId,int state, int start, int offset);

    //kitty_zhu：根据商品名称查询订单
    List<ProductOrder> selectByGoodsName(long uId,String name, byte state, int start, int offset);

    //kitty_zhu:逻辑删除
    boolean delOrderTemp(long uId,long id);

    //kitty_zhu:查询is_del的订单（回收站）
    List<ProductOrder> selectByIsDel(long uId,boolean isDelete, int start, int offset);

    //kitty_zhu:删除订单
    int delOrder(long uId,long id);


    //kitty_zhu：查询订单状态
    byte selectOrderState(long uId,Long id);

    //kitty_zhu：插入用户评价
    int insertSelective(ProductComment productComment);

    ProductOrder selectOrder(long uId,long id);

    /**
     * @description  创建订单
     * @author Gavin
     * @date 2019-10-20 09:41

     * @return
     * @throws
     * @since
    */
    ProductOrder createOrder(HashMap<String,String> map,long uId,String name);


    //修改订单状态
    int updateOrderState(Byte updatedState, Long id);

    //查看订单详情表
    List<OrderInfo> selectByOrderId(Long orderId);


    long getProCount(long id);

    /**
     * 修改订单信息
     * @param updated
     * @param id
     * @return
     */
    int updateById(ProductOrder updated,Long id);






}
