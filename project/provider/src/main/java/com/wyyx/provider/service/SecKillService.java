package com.wyyx.provider.service;


import com.wyyx.provider.dto.SecProduct;


import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-17 15:11
 */
public interface SecKillService {
    List<SecProduct> selectAllSecGoods(int start, int offset);
    long count();
    Long selectInventoryById(Long id);
    boolean  createOrder(Long pID, Long uID);

    //todo 秒杀支付 消息队列

}
