package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author chddald
 * @Date 2019/10/17
 */
@Api(tags = "购物车")
@RestController
@RequestMapping(value = "cart")
public class CartController {

    @Autowired
    private RedisUtil redisUtil;
    @Reference
    private CartService cartService;
    @Reference
    private ShopService shopService;

    //存放临时购物车信息
    private HashMap<Long, Long> cartTemp = new HashMap<Long, Long>();

    @ApiOperation("加入购物车")
    @GetMapping(value = "addCart")
    //Todo 修改检测登录的方法
    public ReturnResult addCart(@Valid UserVo userVo,
                                @ApiParam(value = "商品ID") @RequestParam(value = "pID") Long pID,
                                @ApiParam(value = "选购商品数量") @RequestParam(value = "pNum") Long pNum,
                                HttpServletRequest request) {
        HttpSession session = request.getSession();
        //判断用户是否登录
        if (!redisUtil.hasKey(CommonContants.LOGIN_NAME_SPACE + userVo.getUserID())) {
            cartTemp.put(pID, pNum);
            session.setAttribute("cartTemp", cartTemp);
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);
        } else {
            //java8中的遍历,将临时购物车中的数据遍历出来
            HashMap<Long, Long> cartTemp = (HashMap<Long, Long>) session.getAttribute("cartTemp");
            if (cartTemp.isEmpty()) {
                cartService.insert(pID, userVo.getUserID(), pNum);
            } else {
                //查该用户的购物车
                List<ProductCart> productCarts = cartService.queryAllByUserID(userVo.getUserID());
                //拿数据,已经存的商品的id
                List<Long> collect = productCarts.stream().map(p -> p.getProductId()).collect(Collectors.toList());
                cartTemp.forEach((k, v) -> {
                    //调用service方法，实现数据插入购物车表
                    if (collect.contains(k)) {
                        //已经有这个数据了，从之前的数据中拿出该数据   比较long类型数据相等  long.longValue()
                        //Todo long类型比较的坑
                        List<ProductCart> carts = productCarts.stream().filter(p -> k.longValue() == p.getProductId().longValue()).collect(Collectors.toList());
                        long finalCount = carts.get(0).getProductCount() + v;
                        //修改数据库，商品id 用户id，商品数量
                        cartService.updateProductCount(k, userVo.getUserID(), finalCount);
                    } else {
                        cartService.insert(k, userVo.getUserID(), v);
                    }
                });
                session.removeAttribute("cartTemp");
            }
            return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);
        }
    }

    @ApiOperation("查看购物车")
    @GetMapping(value = "/allCart")
    public ReturnResult allCart(@Valid ProductCart productCart, @Valid UserVo userVo, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (!redisUtil.hasKey(CommonContants.LOGIN_NAME_SPACE + userVo.getUserID())) {
            //未登录时的购物车
            //java8中的遍历,将临时购物车中的数据遍历出来
            //购物车返回集
            ArrayList<CartVo> cartVos = new ArrayList<>();
            HashMap<Long, Long> cartTemp = (HashMap<Long, Long>) session.getAttribute("cartTemp");
            if (null!=cartTemp) {
                //k为商品pid，v为商品数量
                cartTemp.forEach((k, v) -> {
                    CartVo cartVo = new CartVo();
                    //通过商品pid查询商品售价
                    ComProduct comProduct = shopService.selectByPrimaryKey(k);
                    BigDecimal sellPrice = comProduct.getSellPrice();
                    //计算金额
                    BigDecimal totalPrice = sellPrice.multiply(new BigDecimal(v));
                    //塞值
                    cartVo.setProductId(comProduct.getId());
                    cartVo.setName(comProduct.getName());
                    cartVo.setCount(v);
                    cartVo.setDescription(comProduct.getDescription());
                    cartVo.setImgurl(comProduct.getImgurl());
                    cartVo.setTotalPrice(totalPrice);
                    //把cartvo塞到cartvos中
                    cartVos.add(cartVo);
                });
                return ReturnResultUtils.returnSuccess(cartVos);
            }
        } else if (cartService.queryAllByUserID(userVo.getUserID()) != null) {
            List<ProductCart> carts = cartService.queryAllByUserID(userVo.getUserID());
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
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_CART_EMPTY, ReturnResultContants.MSG_CART_EMPTY);
    }


    @ApiOperation("删除商品")
    @GetMapping(value = "/delCart")
    public ReturnResult delCart(@ApiParam(value = "商品Id") @RequestParam(value = "pID") Long pid,
                                @ApiParam(value = "用户Id") @RequestParam(value = "userId")Long userId) {
        int result = cartService.deleteProdectById(pid,userId);
        if (result != 1) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_DEL_CART_WRONG, ReturnResultContants.MSG_DEL_CART_WRONG);
        }
        return ReturnResultUtils.returnSuccess(ReturnResultContants.SUCCESS);
    }


    @ApiOperation("修改购物车商品数量并计算价格")
    @GetMapping(value = "/updateCart")
    public ReturnResult updateCart( @ApiParam(value = "商品ID") @RequestParam(value = "pID") Long pID,
                                    @ApiParam(value = "选购商品数量") @RequestParam(value = "pNum") Long pNum,
                                    @ApiParam(value = "用户Id") @RequestParam(value = "userId")Long userId){
       cartService.updateProductCount(pID,userId,pNum);
       return null;
    }
}