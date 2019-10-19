package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.contants.OrderStatus;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.vo.CommentVo;
import com.wyyx.consumer.vo.OrderVo;
import com.wyyx.consumer.vo.PageVo;
import com.wyyx.provider.dto.ProductComment;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "查询全部订单")
    @GetMapping(value = "/selectOrderAll")
    public ReturnResult selectOrderAll(PageVo pageVo) {
        List<ProductOrder> product_orders = orderService.selectOrderAll(pageVo.getStart(), pageVo.getPageSize());
        return ReturnResultUtils.returnSuccess(product_orders);
    }

    @ApiOperation(value = "根据订单状态查询相应订单")
    @GetMapping(value = "/selectOrderByClass")
    public ReturnResult selectOrderByClass(@ApiParam(value = "商品订单状态码") @RequestParam(value = "state") int state,
                                           PageVo pageVo) {
        List<ProductOrder> productOrders = orderService.selectOrderByClass(state, pageVo.getStart(), pageVo.getPageSize());
        //如果查询的订单不存在
        if (0 == productOrders.size()) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_ORDERS, ReturnResultContants.MSG_NOT_FIND_ORDERS);
        }
        return ReturnResultUtils.returnSuccess(productOrders);
    }

    @ApiOperation(value = "根据商品名称模糊查询相应订单")
    @GetMapping(value = "/selectByGoodsName")
    public ReturnResult selectByGoodsName(@ApiParam(value = "商品名称") @RequestParam(value = "name") String name,
                                          @ApiParam(value = "订单状态") @RequestParam(value = "state") byte state, PageVo pageVo) {
        List<ProductOrder> productOrders = orderService.selectByGoodsName(name, state, pageVo.getStart(), pageVo.getPageSize());
        //如果查询的订单不存在
        if (0 == productOrders.size()) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_ORDERS, ReturnResultContants.MSG_NOT_FIND_ORDERS);
        }
        return ReturnResultUtils.returnSuccess(productOrders);
    }

    @ApiOperation(value = "逻辑删除")
    @GetMapping(value = "/removeOrder")
    public boolean removeOrder(OrderVo orderVo) {
        ProductOrder productOrder = new ProductOrder();
        BeanUtils.copyProperties(orderVo, productOrder);
        return orderService.updateByPrimaryKeySelective(productOrder);
    }

    @ApiOperation(value = "查询isdel的订单-加入回收站")
    @GetMapping(value = "/selectByIsDel")
    public ReturnResult selectByIsDel(@ApiParam(value = "是否逻辑删除") @RequestParam(value = "isDel") boolean isDelete, PageVo pageVo) {
        List<ProductOrder> productOrders = orderService.selectByIsDel(isDelete, pageVo.getStart(), pageVo.getPageSize());
        return ReturnResultUtils.returnSuccess(productOrders);

    }

    @ApiOperation(value = "删除回收站")
    @GetMapping(value = "/delOrder")
    public ReturnResult delOrder(@ApiParam(value = "订单id") @RequestParam(value = "id") long id) {

        //判断是否在回收站
        if (true == orderService.selectIsDel(id)) {
            orderService.delOrder(id);
            return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_DEL_ORDER_ORDERS, ReturnResultContants.MSG_DEL_ORDER_ORDERS);
        }
        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_GOODS, ReturnResultContants.MSG_NOT_FIND_ORDERS);
    }

    @ApiOperation(value = "订单打分")
    @GetMapping(value = "/orderGrade")
    public ReturnResult orderGrade(@ApiParam(value = "订单id") @RequestParam(value = "id") long id, CommentVo commentVo) {

        byte b = orderService.selectOrderState(id);

        //判断订单状态是否已收货
        if (OrderStatus.ORDER_HAVE_RECEIVE.getoStatus().equals(b)  ) {

            ProductComment productComment = new ProductComment();
            BeanUtils.copyProperties(commentVo, productComment);
            orderService.insertSelective(productComment);

            return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_USER_COMMENT, ReturnResultContants.MSGUSER_COMMENT_SUCCESS);
        }

        return null;
    }
}