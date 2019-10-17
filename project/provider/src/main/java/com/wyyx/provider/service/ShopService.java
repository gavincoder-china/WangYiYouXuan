package com.wyyx.provider.service;

import com.wyyx.provider.dto.ComProduct;

import java.util.List;

public interface ShopService {
    /**
     * @author kitty_zhu
     * @date 2019-10-17 12:11
     * 网页首页的分组商品信息
     */
    List<ComProduct> selectAll();


}
