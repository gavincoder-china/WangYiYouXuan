package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.TempLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.TempLoginParam;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.CartVo;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductCart;
import com.wyyx.provider.service.CartService;
import com.wyyx.provider.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author chddald
 * @Date 2019/10/17
 */
@Slf4j
@Api(tags = "购物车")
@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private RedisUtil redisUtil;
    @Reference
    private CartService cartService;
    @Reference
    private ShopService shopService;


    @TempLoginMethod
    @ApiOperation("加入购物车")
    @GetMapping(value = "addCart")
    //Todo 修改检测登录的方法
    public ReturnResult addCart(@ApiParam(value = "商品ID") @RequestParam(value = "pID") Long pID,
                                @ApiParam(value = "选购商品数量") @RequestParam(value = "pNum") Long pNum,
                                @TempLoginParam UserVo userVo) {
        //登录的
//        if (!StringUtils.isEmpty(userVo.getUserID().toString())) {
        if (!ObjectUtils.isEmpty(userVo.getUserID())) {

            cartService.insert(pID, userVo.getUserID(), pNum);
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);

        } else if (!ObjectUtils.isEmpty(userVo.getTemp())) {
            //未登录的,设置个300秒过期
            redisUtil.hset(CommonContants.TEMP_CART + userVo.getTemp(), pID.toString(), pNum, 300);
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_INSERT_CART_FAIL,
                                            ReturnResultContants.MSG_INSERT_CARTL_FAIL);

    }


    @TempLoginMethod
    @ApiOperation("查看购物车")
    @GetMapping(value = "/allCart")
    public ReturnResult allCart(@TempLoginParam UserVo userVo) {

        if (!ObjectUtils.isEmpty(userVo.getUserID())) {

            if (!ObjectUtils.isEmpty(userVo.getTemp())) {

                HashMap<Object, Object> cartTemp = (HashMap<Object, Object>)
                        redisUtil.hmget(CommonContants.TEMP_CART + userVo.getTemp());

                if (!cartTemp.isEmpty()) {

                    //查该用户的购物车
                    List<ProductCart> productCarts = cartService.queryAllByUserID(userVo.getUserID());
                    //拿数据,已经存的商品的id
                    List<Long> collect = productCarts.stream().map(p -> p.getProductId()).collect(Collectors.toList());
                    cartTemp.forEach((k, v) -> {

                        Long tempID = Long.parseLong(k.toString());
                        //调用service方法，实现数据插入购物车表
                        if (collect.contains(tempID)) {
                            //已经有这个数据了，从之前的数据中拿出该数据   比较long类型数据相等  long.longValue()
                            //Todo long类型比较的坑
                            List<ProductCart> carts = productCarts.stream().filter(p -> tempID.longValue() == p.getProductId().longValue())
                                                                  .collect(Collectors.toList());

                            long finalCount = carts.get(0).getProductCount() + Long.parseLong(v.toString());
                            //修改数据库，商品id 用户id，商品数量
                            cartService.updateProductCount(tempID, userVo.getUserID(), finalCount);
                        } else {
                            cartService.insert(tempID, userVo.getUserID(), Long.parseLong(v.toString()));
                        }
                    });
                    try {
                        redisUtil.hdel(CommonContants.TEMP_CART + userVo.getTemp());
                    }
                    catch (Exception e) {
                        log.info("redis/expire");
                    }
                }
            }
        }


        if (!ObjectUtils.isEmpty(userVo.getUserID())) {

            List<ProductCart> carts = cartService.queryAllByUserID(userVo.getUserID());

            if (carts.size() != 0) {
                ArrayList<CartVo> cartVos = new ArrayList<>();
                carts.stream().forEach(cart -> {
                    CartVo cartVo = new CartVo();
                    //通过商品pid查询商品售价
                    ComProduct comProduct = shopService.selectByPrimaryKey(cart.getProductId());
                    BigDecimal sellPrice = comProduct.getSellPrice();
                    //计算金额
                    BigDecimal totalPrice = sellPrice.multiply(new BigDecimal(cart.getProductCount()));
                    //塞值
                    cartVo.setProductId(comProduct.getId());
                    cartVo.setName(comProduct.getName());
                    cartVo.setCount(cart.getProductCount());
                    cartVo.setDescription(comProduct.getDescription());
                    cartVo.setImgurl(comProduct.getImgurl());
                    cartVo.setTotalPrice(totalPrice);
                    cartVo.setId(comProduct.getId());
                    //把cartvo塞到cartvos中
                    cartVos.add(cartVo);

                });
                return ReturnResultUtils.returnSuccess(cartVos);

            } else {
                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_CART_EMPTY,
                                                    ReturnResultContants.MSG_CART_EMPTY);
            }

        } else if (!ObjectUtils.isEmpty(userVo.getTemp())) {
            //未登录时的购物车
            ArrayList<CartVo> cartVos = new ArrayList<>();
            HashMap<Object, Object> cartTemp = (HashMap<Object, Object>) redisUtil.hmget(
                    CommonContants.TEMP_CART + userVo.getTemp());

            if (cartTemp.size() != 0) {
                //k为商品pid，v为商品数量
                cartTemp.forEach((k, v) -> {
                    long tempID = Long.parseLong(k.toString());
                    long tempCount = Long.parseLong(v.toString());
                    CartVo cartVo = new CartVo();
                    //通过商品pid查询商品售价
                    ComProduct comProduct = shopService.selectByPrimaryKey(tempID);
                    BigDecimal sellPrice = comProduct.getSellPrice();
                    //计算金额
                    BigDecimal totalPrice = sellPrice.multiply(new BigDecimal(tempCount));
                    //塞值
                    cartVo.setProductId(comProduct.getId());
                    cartVo.setName(comProduct.getName());
                    cartVo.setCount(tempCount);
                    cartVo.setDescription(comProduct.getDescription());
                    cartVo.setImgurl(comProduct.getImgurl());
                    cartVo.setTotalPrice(totalPrice);
                    //把cartvo塞到cartvos中
                    cartVos.add(cartVo);

                });
                return ReturnResultUtils.returnSuccess(cartVos);
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_CART_EMPTY, ReturnResultContants.MSG_CART_EMPTY);
    }

    @TempLoginMethod
    @ApiOperation("删除商品")
    @GetMapping(value = "/delCart")
    public ReturnResult delCart(@ApiParam(value = "商品Id") @RequestParam(value = "pID") Long pid,
                                @TempLoginParam UserVo userVo) {
        if (!ObjectUtils.isEmpty(userVo.getUserID())) {
            int result = cartService.deleteProdectById(pid, userVo.getUserID());
            if (result != 1) {
                return ReturnResultUtils.returnFail(ReturnResultContants.CODE_DEL_CART_WRONG, ReturnResultContants.MSG_DEL_CART_WRONG);
            }
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);
        } else if (!ObjectUtils.isEmpty(userVo.getTemp())) {
            redisUtil.hdel(CommonContants.TEMP_CART + userVo.getTemp(), pid.toString());
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_DEL_CART_WRONG, ReturnResultContants.MSG_DEL_CART_WRONG);

    }

    @TempLoginMethod
    @ApiOperation("修改购物车商品数量并计算价格")
    @GetMapping(value = "/updateCart")
    public ReturnResult updateCart(@ApiParam(value = "商品ID") @RequestParam(value = "pID") Long pID,
                                   @ApiParam(value = "选购商品数量") @RequestParam(value = "pNum") Long pNum,
                                   @TempLoginParam UserVo userVo) {
        if (!ObjectUtils.isEmpty(userVo.getUserID())) {

            cartService.updateProductCount(pID, userVo.getUserID(), pNum);
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);

        } else if (!ObjectUtils.isEmpty(userVo.getTemp())) {

            redisUtil.hset(CommonContants.TEMP_CART + userVo.getTemp(), pID.toString(), pNum);
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_UPDATE_CART_FAIL, ReturnResultContants.MSG_UPDATE_CART_FAIL);
    }
}