package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.RequireLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.RequireLoginParam;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.util.UserUtil;
import com.wyyx.consumer.vo.*;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.contants.OrderStatus;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.OrderInfo;
import com.wyyx.provider.dto.ProductComment;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.service.GoodsService;
import com.wyyx.provider.service.OrderService;
import com.wyyx.provider.service.PerCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-18 11:18
 */
@Slf4j
@Api(value = "订单业务")
@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    @Autowired
    private RedisUtil redisUtil;

    @Reference
    private GoodsService goodsService;

    @Autowired
    private UserUtil userUtil;
    @Reference
    private PerCenterService perCenterService;

    @RequireLoginMethod
    @ApiOperation(value = "查询全部订单")
    @GetMapping(value = "/selectOrderAll")
    public ReturnResult<List<ProductOrder>> selectOrderAll(@Valid PageVo pageVo,
                                       @RequireLoginParam UserVo userVo) {

        List<ProductOrder> product_orders = orderService.selectOrderAll(userVo.getUserID(),
                                                                        pageVo.getStart(),
                                                                        pageVo.getPageSize());

        return ReturnResultUtils.returnSuccess(product_orders);
    }


    @RequireLoginMethod
    @ApiOperation(value = "根据订单状态查询相应订单")
    @GetMapping(value = "/selectOrderByClass")
    public ReturnResult<List<ProductOrder>> selectOrderByClass(@ApiParam(value = "商品订单状态码")
                                           @RequestParam(value = "state") int state,
                                           @Valid PageVo pageVo,
                                           @RequireLoginParam UserVo userVo) {
        List<ProductOrder> productOrders = orderService.selectOrderByClass(userVo.getUserID(),
                                                                           state, pageVo.getStart(),
                                                                           pageVo.getPageSize());
        //如果查询的订单不存在
        if (0 == productOrders.size()) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_ORDERS,
                                                ReturnResultContants.MSG_NOT_FIND_ORDERS);
        }
        return ReturnResultUtils.returnSuccess(productOrders);
    }


    @RequireLoginMethod
    @ApiOperation(value = "根据商品名称模糊查询相应订单")
    @GetMapping(value = "/selectByGoodsName")
    public ReturnResult<List<ProductOrder>> selectByGoodsName(@ApiParam(value = "商品名称") @RequestParam(value = "name") String name,
                                          @ApiParam(value = "订单状态") @RequestParam(value = "state") byte state,
                                          @Valid PageVo pageVo,
                                          @RequireLoginParam UserVo userVo) {
        List<ProductOrder> productOrders = orderService.selectByGoodsName(userVo.getUserID(),
                                                                          name,
                                                                          state,
                                                                          pageVo.getStart(),
                                                                          pageVo.getPageSize());

        //如果查询的订单不存在
        if (0 == productOrders.size()) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_ORDERS,
                                                ReturnResultContants.MSG_NOT_FIND_ORDERS);
        }
        return ReturnResultUtils.returnSuccess(productOrders);
    }


    @RequireLoginMethod
    @ApiOperation(value = "逻辑删除")
    @PostMapping(value = "/removeOrder")
    public ReturnResult removeOrder(@ApiParam(value = "订单id") @RequestParam(value = "id") long id,
                                    @RequireLoginParam UserVo userVo) {

        boolean result = orderService.delOrderTemp(userVo.getUserID(), id);
        if (result) {
            return ReturnResultUtils.returnSuccess();
        } else {

            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_DEL_ORDER_FAIL,
                                                ReturnResultContants.MSG_DEL_ORDER_FAIL);
        }
    }

    @RequireLoginMethod
    @ApiOperation(value = "查询isdel的订单-加入回收站")
    @GetMapping(value = "/selectByIsDel")
    public ReturnResult<List<ProductOrder>> selectByIsDel(@ApiParam(value = "是否逻辑删除")
                                      @RequestParam(value = "isDelete") boolean isDelete,
                                      @Valid PageVo pageVo,
                                      @RequireLoginParam UserVo userVo) {
        List<ProductOrder> productOrders = orderService.selectByIsDel(userVo.getUserID(),
                                                                      isDelete,
                                                                      pageVo.getStart(),
                                                                      pageVo.getPageSize());
        return ReturnResultUtils.returnSuccess(productOrders);

    }

    @RequireLoginMethod
    @ApiOperation(value = "删除回收站")
    @PostMapping(value = "/delOrder")
    public ReturnResult delOrder(@ApiParam(value = "订单id") @RequestParam(value = "id") long id,
                                 @RequireLoginParam UserVo userVo) {

        ProductOrder order = orderService.selectOrder(userVo.getUserID(), id);

        if (!ObjectUtils.isEmpty(order)) {
            //判断是否在回收站
            if (order.getIsDelete()) {
                orderService.delOrder(userVo.getUserID(), id);
                return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_DEL_ORDER_ORDERS,
                                                       ReturnResultContants.MSG_DEL_ORDER_ORDERS);
            }
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_GOODS,
                                            ReturnResultContants.MSG_NOT_FIND_ORDERS);
    }

    @RequireLoginMethod
    @ApiOperation(value = "订单打分")
    @PostMapping(value = "/orderGrade")
    public ReturnResult orderGrade(@ApiParam(value = "订单id") @RequestParam(value = "id") long id,
                                   @Valid CommentVo commentVo,
                                   @RequireLoginParam UserVo userVo) {

        byte b = orderService.selectOrderState(userVo.getUserID(), id);

        //判断订单状态是否已收货
        if (OrderStatus.ORDER_HAVE_RECEIVE.getoStatus().equals(b)) {

            ProductComment productComment = new ProductComment();

            BeanUtils.copyProperties(commentVo, productComment);
            if (ObjectUtils.isEmpty(commentVo.getRateLevel())) {
                productComment.setRateLevel(9);
            }
            productComment.setUserId(userVo.getUserID());

            orderService.insertSelective(productComment);

            return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_USER_COMMENT_SUCCESS,
                                                   ReturnResultContants.MSG_USER_COMMENT_SUCCESS);
        }
        return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_USER_COMMENT_FAIL,
                                               ReturnResultContants.MSG_USER_COMMENT_FAIL);

    }

    @RequireLoginMethod
    @ApiOperation(value = "商品选购,生成订单")
    @PostMapping(value = "/createOrder")
    public ReturnResult<ProductOrder> createOrder(@RequireLoginParam UserVo userVo,
                                    @ApiParam(value = "选商品的id与件数")
                                    @RequestParam HashMap<String, String> map) {

        boolean inventory = goodsService.checkInventory(map);
        if (inventory) {
            ProductOrder order = orderService.createOrder(map, userVo.getUserID(), "buy");

            //订单超时
            redisUtil.set(CommonContants.ORDER_EXPIRE + order.getId(), 1, 1000);

           // HashMap<Object, Object> returnMap = new HashMap<>();
           // returnMap.put("订单信息", order);
           // returnMap.put("订单状态", "订单请尽快支付");

            return ReturnResultUtils.returnSuccess(order);

        } else {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NO_INVENTORY,
                                                ReturnResultContants.MSG_NO_INVENTORY);

        }
    }


    @RequireLoginMethod
    @ApiOperation(value = "商品选购订单生成后的查看订单")
    @GetMapping(value = "/checkOrder")
    public ReturnResult<OrderDescVo> checkOrder(@RequireLoginParam UserVo userVo,
                                   @ApiParam(value = "订单id")
                                   @RequestParam(value = "id") long id) {

        ProductOrder order = orderService.selectOrder(userVo.getUserID(), id);
        List<OrderInfo> infos = orderService.selectByOrderId(id);

        OrderDescVo orderDescVo = new OrderDescVo();

        ArrayList<OrderInfoVo> lists = new ArrayList<>();

        //遍历订单详情表,获取信息
        infos.forEach(list -> {
            OrderInfoVo orderInfoVo = new OrderInfoVo();

            ComProduct product = goodsService.selectById(list.getProductId());

            BeanUtils.copyProperties(product, orderInfoVo);

            orderInfoVo.setCount(list.getCount());

            lists.add(orderInfoVo);

        });

        orderDescVo.setList(lists);
        orderDescVo.setFinalPrice(order.getFinalPrice());
        orderDescVo.setTotalPrice(order.getTotalPrice());
        orderDescVo.setName(order.getName());
        orderDescVo.setId(id);

        return ReturnResultUtils.returnSuccess(orderDescVo);
    }


    @RequireLoginMethod
    @ApiOperation(value = "收货")
    @PostMapping(value = "/takeGoods")
    public ReturnResult takeGoods(@RequireLoginParam UserVo userVo,
                                  @ApiParam(value = "订单id")
                                  @RequestParam(value = "id") long id) {

        int result = orderService.updateOrderState(OrderStatus.ORDER_HAVE_RECEIVE.getoStatus(), id);
        //加经验+加积分
        //找订单中商品总数
        long proCount = orderService.getProCount(id);
        //拿到最终实付价格
        ProductOrder order = orderService.selectOrder(userVo.getUserID(), id);
        BigDecimal finalPrice = order.getFinalPrice();

        int point = userUtil.getPayPoint(finalPrice);
        int expValue = userUtil.getPayExpValue((int) proCount, finalPrice);

        //插积分经验
        perCenterService.updatePointsAndExperiencebyid(point, expValue, userVo.getUserID());

        return ReturnResultUtils.returnSuccess(result);
    }

    @RequireLoginMethod
    @ApiOperation(value = "修改订单收货人信息")
    @PostMapping(value = "/updateReceiver")
    public ReturnResult updateReceiver(@Valid OrderVo orderVo) {

        ProductOrder order = new ProductOrder();

        BeanUtils.copyProperties(orderVo, order);

        int result = orderService.updateById(order, orderVo.getId());

        if (result == 1) {
            return ReturnResultUtils.returnSuccess();
        } else {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_UPDATE_ORDER_INFO_FAIL,
                                                ReturnResultContants.MSG_UPDATE_ORDER_INFO_FAIL);
        }
    }

}