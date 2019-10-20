package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.contants.OrderStatus;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.dto.SecProduct;
import com.wyyx.provider.mapper.ProductOrderMapper;
import com.wyyx.provider.mapper.SecProductMapper;
import com.wyyx.provider.service.SecKillService;
import com.wyyx.provider.util.IdWorker;
import com.wyyx.provider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-17 15:11
 */
@Service
@Transactional
public class SecKillServiceImpl implements SecKillService {
    @Autowired
    private SecProductMapper secProductMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<SecProduct> selectAllSecGoods(int start, int offset) {

        List<SecProduct> secProducts = secProductMapper.selectAll(start, offset);

        return secProducts;
    }

    @Override
    public long count() {

        return secProductMapper.count();
    }

    @Override
    public Long selectInventoryById(Long id) {
        return secProductMapper.selectInventoryById(id);
    }

    @Override
    public boolean createOrder(Long pID, Long uID) {
        ProductOrder order = new ProductOrder();

        SecProduct secProduct = secProductMapper.selectById(pID);
        order.setName(secProduct.getName());
        order.setTotalPrice(secProduct.getSellPrice());
        order.setState(OrderStatus.ORDER_TO_PAY.getoStatus());
        order.setUserId(uID);
        order.setCreateTime(new Date());
        //秒杀商品的id
        order.setProductId(pID);
        order.setId(idWorker.nextId());
        int i = productOrderMapper.insertSelective(order);
        if (i == 1) {
            //减库存  todo 搞redis热点
            secProductMapper.updateInventorybyid(pID);

            //删除分布式锁
            redisUtils.delLock(CommonContants.HOT_LOCK + pID);

            return true;
        }

        return false;
    }


}
