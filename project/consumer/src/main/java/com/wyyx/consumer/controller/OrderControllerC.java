package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.RequireLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.RequireLoginParam;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.OrderVoC;
import com.wyyx.consumer.vo.PageVo;
import com.wyyx.consumer.vo.ReturnOrderVoC;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.dto.ProductOrder;
import com.wyyx.provider.model.OrderGoodsInfo;
import com.wyyx.provider.service.GoodsService;
import com.wyyx.provider.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kitty_zhu
 * @date 2019-10-22 15:29
 */
@Slf4j
@Api(value = "我的订单中心")
@RestController
@RequestMapping(value = "/orderC")
public class OrderControllerC {

    @Reference
    private OrderService orderService;

    @Autowired
    private RedisUtil redisUtil;

    @Reference
    private GoodsService goodsService;

    @RequireLoginMethod
    @ApiOperation(value = "查询我的订单")
    @GetMapping(value = "/selectMyOrder")
    public ReturnResult selectMyOrder(@Valid PageVo pageVo,
                                      @RequireLoginParam UserVo userVo) {

        //把自己的订单全部查出
        List<ProductOrder> productOrders = orderService.selectOrderAll(userVo.getUserID(), pageVo.getStart(), pageVo.getPageSize());

        ArrayList<Object> listTemp = new ArrayList<>();
        //遍历删选敏感字段
        productOrders.stream().forEach(list -> {
            OrderVoC orderVoC = new OrderVoC();
            List<OrderGoodsInfo> orderGoods = orderService.getOrderGoods(list.getId());
            orderVoC.setGoods(orderGoods);
            orderVoC.setId(list.getId());
            orderVoC.setState(list.getState());
            orderVoC.setTotalPrice(list.getTotalPrice());
            listTemp.add(orderVoC);
        });
        //创建返回vo,并赋值
        ReturnOrderVoC returnOrderVoC = new ReturnOrderVoC();
        returnOrderVoC.setOrderList(listTemp);
        returnOrderVoC.setPageSize(pageVo.getPageSize());
        returnOrderVoC.setStartPage(pageVo.getStartPage());
        //总个数
        returnOrderVoC.setTotalSize(orderService.selectOrderAllCount(userVo.getUserID()));

        return ReturnResultUtils.returnSuccess(returnOrderVoC);
    }
}
