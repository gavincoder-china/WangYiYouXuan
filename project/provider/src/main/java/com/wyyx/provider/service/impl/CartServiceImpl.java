package com.wyyx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductCart;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.mapper.ProductCartMapper;
import com.wyyx.provider.service.CartService;
import com.wyyx.provider.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author chddald
 * @Date 2019/10/17
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private ComProductMapper comProductMapper;

    @Autowired
    private ProductCartMapper productCartMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    public ComProduct selectByPrimaryKey(Long id) {
        return comProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(Long pID, Long userId, Long pCount) {
        ProductCart cart = new ProductCart();
        cart.setProductId(pID);
        cart.setUserId(userId);
        cart.setProductCount(pCount);
        cart.setCreateTime(new Date());

        cart.setTotalPrice( comProductMapper.selectByPrimaryKey(pID).getSellPrice().multiply(new BigDecimal(pCount)));
        cart.setId(idWorker.nextId());

        return productCartMapper.insert(cart);
    }

    @Override
    public List<ProductCart> queryAllByUserID(Long userId) {
        return productCartMapper.queryAllByUserID(userId);
    }

    @Override
    public ProductCart selectByPidAndUserId(Long pID, Long userId) {
        return productCartMapper.selectByPidAndUserId(pID,userId);
    }

    @Override
    public int updateProductCount(Long pID, Long userId, Long pCount) {
        BigDecimal totalPrice = comProductMapper.selectByPrimaryKey(pID).getSellPrice().multiply(new BigDecimal(pCount));
        return productCartMapper.updateProductCount(pID,userId,pCount,totalPrice);
    }

    @Override
    public int deleteProdectById(Long pid,Long userId) {
        return productCartMapper.deleteProdectById(pid,userId);
    }

}