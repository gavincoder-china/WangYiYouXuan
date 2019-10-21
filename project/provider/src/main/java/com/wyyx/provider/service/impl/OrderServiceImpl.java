package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.contants.OrderStatus;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.OrderInfo;
import com.wyyx.provider.dto.ProductComment;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.mapper.OrderInfoMapper;
import com.wyyx.provider.mapper.ProductCommentMapper;
import com.wyyx.provider.mapper.ProductOrderMapper;
import com.wyyx.provider.service.OrderService;
import com.wyyx.provider.util.IdWorker;
import com.wyyx.provider.util.TotalPriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private ProductCommentMapper productCommentMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ComProductMapper comProductMapper;

    @Autowired
    private TotalPriceUtil priceUtil;

    //kitty_zhu：查询全部订单
    @Override
    public List<ProductOrder> selectOrderAll(long uId, int start, int offset) {

        return productOrderMapper.selectOrderAll(uId, start, offset);
    }

    //kitty_zhu：查询订单状态 state:订单状态
    @Override
    public List<ProductOrder> selectOrderByClass(long uId, int state, int start, int offset) {

        return productOrderMapper.selectOrderByClass(uId, state, start, offset);
    }

    //kitty_zhu：根据订单状态 模糊查询相应订单 state:订单状态
    @Override
    public List<ProductOrder> selectByGoodsName(long uId, String name, byte state, int start, int offset) {
        return productOrderMapper.selectByGoodsName(uId, name, state, start, offset);
    }

    //kitty_zhu 逻辑删除
    @Override
    public boolean delOrderTemp(long uId, long id) {
        int result = productOrderMapper.updateIsDeleteByProductIdAndUserId(true, uId, id);
        if (result==1){
            return true;
        }else {
            return false;
        }

    }

    //kitty_zhu:查询is_del的订单（回收站）
    @Override
    public List<ProductOrder> selectByIsDel(long uId, boolean isDelete, int start, int offset) {
        return productOrderMapper.selectByIsDel(uId, isDelete, start, offset);
    }

    //kitty_zhu:删除回收站
    @Override
    public int delOrder(long uId, long id) {
        return productOrderMapper.deleteByPrimaryKey(id);
    }


    //kitty_zhu：查询订单状态
    @Override
    public byte selectOrderState(long uId, Long id) {
        ProductOrder productOrder = productOrderMapper.selectById(uId, id);
        if (productOrder == null) {
            return -1;
        } else {
            return productOrder.getState();
        }
    }

    //插入用户评价
    @Override
    public int insertSelective(ProductComment productComment) {
        productComment.setId(idWorker.nextId());
        return productCommentMapper.insertSelective(productComment);
    }

    @Override
    public ProductOrder selectOrder(long uId, long id) {

        return productOrderMapper.selectById(uId, id);
    }

    @Override
    public ProductOrder createOrder(HashMap<String, String> map, long uId, String name) {

        //生成订单号
        long oId = idWorker.nextId();


        /**
         * 为什么 Lambda 表达式(匿名类) 不能访问非 final 的局部变量呢？
         * 因为实例变量存在堆中，而局部变量是在栈上分配，
         * Lambda 表达(匿名类) 会在另一个线程中执行。
         * 如果在线程中要直接访问一个局部变量，
         * 可能线程执行时该局部变量已经被销毁了，
         * 而 final 类型的局部变量在 Lambda 表达式(匿名类) 中其实是局部变量的一个拷贝。
         */
        //Todo 兰姆达表达式累加求金额
        final BigDecimal[] totalPrice = {new BigDecimal(0)};

        map.forEach((k, v) -> {

            //往info中插值
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(oId);
            orderInfo.setProductId(Long.parseLong(k));
            orderInfo.setCount(Long.parseLong(v));
            orderInfoMapper.insertSelective(orderInfo);

            //拿pid,数量去算钱

            ComProduct product = comProductMapper.selectById(Long.parseLong(k));

            BigDecimal multiply = product.getSellPrice().multiply(new BigDecimal(v));

            totalPrice[0] = totalPrice[0].add(multiply);

        });


        ProductOrder order = new ProductOrder();
        order.setId(oId);

        //得到计算过积分的价格
        BigDecimal priceTemp = priceUtil.finalPrice(uId, totalPrice[0]);
        //得到计算过运费的金额
        BigDecimal finalPrice = priceUtil.goodsFinalPrice(uId, priceTemp);

        order.setTotalPrice(finalPrice);

        order.setUserId(uId);
        order.setCreateTime(new Date());
        order.setState(OrderStatus.ORDER_TO_PAY.getoStatus());
        order.setName(name);

        //生成订单
        productOrderMapper.insertSelective(order);

        return order;
    }

    @Override
    public int updateOrderState(Byte updatedState, Long id) {

        return productOrderMapper.updatestateBYidAndUserId(updatedState, id);
    }

    @Override
    public List<OrderInfo> selectByOrderId(Long orderId) {

        return orderInfoMapper.selectByOrderId(orderId);
    }

    @Override
    public long getProCount(long id) {

        List<OrderInfo> orderInfos = orderInfoMapper.selectByOrderId(id);

        long sum = orderInfos.stream().mapToLong(k -> k.getCount()).sum();

        return sum;
    }

    @Override
    public int updateById(ProductOrder updated, Long id) {

        return   productOrderMapper.updateById(updated, id);
    }


}