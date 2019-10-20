package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.RequireLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.RequireLoginParam;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.vo.CommentVo;
import com.wyyx.consumer.vo.PageVo;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.OrderStatus;
import com.wyyx.provider.dto.ProductComment;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @RequireLoginMethod
    @ApiOperation(value = "查询全部订单")
    @GetMapping(value = "/selectOrderAll")
    public ReturnResult selectOrderAll(@Valid PageVo pageVo,
                                       @RequireLoginParam UserVo userVo) {

        List<ProductOrder> product_orders = orderService.selectOrderAll(userVo.getUserID(),
                                                                        pageVo.getStart(),
                                                                        pageVo.getPageSize());
        return ReturnResultUtils.returnSuccess(product_orders);
    }


    @RequireLoginMethod
    @ApiOperation(value = "根据订单状态查询相应订单")
    @GetMapping(value = "/selectOrderByClass")
    public ReturnResult selectOrderByClass(@ApiParam(value = "商品订单状态码")
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
    public ReturnResult selectByGoodsName(@ApiParam(value = "商品名称") @RequestParam(value = "name") String name,
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
    @GetMapping(value = "/removeOrder")
    public boolean removeOrder(@ApiParam(value = "订单id") @RequestParam(value = "id") long id,
                               @RequireLoginParam UserVo userVo) {

        return orderService.delOrderTemp(userVo.getUserID(), id);
    }

    @RequireLoginMethod
    @ApiOperation(value = "查询isdel的订单-加入回收站")
    @GetMapping(value = "/selectByIsDel")
    public ReturnResult selectByIsDel(@ApiParam(value = "是否逻辑删除")
                                      @RequestParam(value = "isDel") boolean isDelete,
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
    @GetMapping(value = "/delOrder")
    public ReturnResult delOrder(@ApiParam(value = "订单id") @RequestParam(value = "id") long id,
                                 @RequireLoginParam UserVo userVo) {

        ProductOrder order = orderService.selectOrder(userVo.getUserID(), id);

        //判断是否在回收站
        if (order.getIsDelete()) {
            orderService.delOrder(userVo.getUserID(), id);
            return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_DEL_ORDER_ORDERS,
                                                   ReturnResultContants.MSG_DEL_ORDER_ORDERS);
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_GOODS,
                                            ReturnResultContants.MSG_NOT_FIND_ORDERS);
    }

    @RequireLoginMethod
    @ApiOperation(value = "订单打分")
    @GetMapping(value = "/orderGrade")
    public ReturnResult orderGrade(@ApiParam(value = "订单id") @RequestParam(value = "id") long id,
                                   @Valid CommentVo commentVo,
                                   @RequireLoginParam UserVo userVo) {

        byte b = orderService.selectOrderState(userVo.getUserID(), id);

        //判断订单状态是否已收货
        if (OrderStatus.ORDER_HAVE_RECEIVE.getoStatus().equals(b)) {

            ProductComment productComment = new ProductComment();
            BeanUtils.copyProperties(commentVo, productComment);
            productComment.setUserId(userVo.getUserID());
            orderService.insertSelective(productComment);

            return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_USER_COMMENT_SUCCESS,
                                                   ReturnResultContants.MSG_USER_COMMENT_SUCCESS);
        }

        return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_USER_COMMENT_FAIL,
                                               ReturnResultContants.MSG_USER_COMMENT_FAIL);

    }

    @RequireLoginMethod
    @ApiOperation(value = "生成订单")
    @PostMapping(value = "/createOrder")
    public ReturnResult createOrder(@RequireLoginParam UserVo userVo,
                                    @ApiParam(value = "选种商品的id与件数")
                                    @RequestParam HashMap<String, String> map) {

        ProductOrder order = orderService.createOrder(map, userVo.getUserID());

        return ReturnResultUtils.returnSuccess(order);
    }

}