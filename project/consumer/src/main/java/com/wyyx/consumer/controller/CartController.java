package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.vo.NoLoginProductCart;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductCart;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.mapper.ProductCartMapper;
import com.wyyx.provider.service.ShopService;
import com.wyyx.provider.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author chddald
 * @Date 2019/10/17
 */
@Api(tags = "购物车")
@RestController
@RequestMapping(value = "cart")
public class CartController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ComProductMapper comProductMapper;
    @Autowired
    private ProductCartMapper productCartMapper;

    @Reference
    private ShopService shopService;

    @ApiOperation("加入购物车")
    @GetMapping(value = "addCart")
    public ReturnResult addCart(@Valid UserVo userVo, @ApiParam(value = "商品ID") Long id, HttpServletRequest request) {
        //判断用户是否登录

        if (redisUtils.hasKey(CommonContants.LOGIN_NAME_SPACE + userVo.getUserID())) {
            //根据商品id获取商品
            ComProduct comProduct = comProductMapper.selectByPrimaryKey(id);
            NoLoginProductCart noLoginProductCart = new NoLoginProductCart();
            noLoginProductCart.setProductId(comProduct.getId());
            noLoginProductCart.setCreateTime(new Date());
            //赋值数量默认为1
            noLoginProductCart.setProductCount(1l);
            //将数据转化为BigDecimal类型的数据
            BigDecimal pCount = new BigDecimal(noLoginProductCart.getProductCount());
            BigDecimal totalPrice = pCount.multiply(comProduct.getSellPrice());
            noLoginProductCart.setTotalPrice(totalPrice);
            /*String productCartStr = JSONObject.toJSONString(productCart);
            redisUtils.set(CommonContants.NO_LOGIN_CART_SPACE + id,productCartStr);*/
            //如何在用户登录后将未登录时加入购物车的商品整合到用户购物车中
            request.setAttribute("noLogin", noLoginProductCart);
        } else {
            //获取用户购物车中的信息
            ProductCart productCart = new ProductCart();
            List<ProductCart> allProduct = productCartMapper.queryAll(productCart);
            if (allProduct != null) {
                NoLoginProductCart noLoginProductCart = new NoLoginProductCart();
                Long pId = noLoginProductCart.getId();
                //ProductCart productCart = productCartMapper.selectByPrimaryKey(pId);
            }
        }
        return null;
    }
}