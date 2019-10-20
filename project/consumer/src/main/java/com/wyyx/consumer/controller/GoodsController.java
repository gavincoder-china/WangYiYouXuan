package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.TempLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.TempLoginParam;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.vo.GoodsVo;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author kitty_zhu
 * @date 2019-10-18 18:51
 */
@Api(tags = "商品详情")
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;


    @TempLoginMethod
    @ApiOperation(value = "商品详情展示")
    public ReturnResult showGood(@ApiParam(value = "商品id")
                                 @RequestParam(value = "pId") long pId,
                                 @TempLoginParam UserVo userVo) {
        GoodsVo goodsVo = new GoodsVo();

        ComProduct product = goodsService.selectById(pId);
        BeanUtils.copyProperties(product, goodsVo);


        if (product.getInventory() >= 50) {
            goodsVo.setInventoryDesc(CommonContants.INVENTORY_FULL);
        } else if (product.getInventory() > 0 && product.getInventory() < 50) {
            goodsVo.setInventoryDesc(CommonContants.INVENTORY_HALF);
        } else if (product.getInventory() == 0) {
            goodsVo.setInventoryDesc(CommonContants.INVENTORY_ZERO);

        }

        int niceRatio = goodsService.goodsNiceRatio(pId);
        if (niceRatio >= 90) {
            goodsVo.setColor(CommonContants.NICE_90_RED);
        } else if (niceRatio >= 70 && niceRatio < 90) {
            goodsVo.setColor(CommonContants.NICE_70_GREED);
        } else if (niceRatio >= 50 && niceRatio < 70) {
            goodsVo.setColor(CommonContants.NICE_60_ORANGE);
        } else if (niceRatio < 50) {
            goodsVo.setColor(CommonContants.NICE_50_BLACK);
        }

        //登录的用户
        if (!StringUtils.isEmpty(userVo.getUserID().toString())) {


            BigDecimal vipPrice = goodsService.getVIPPrice(userVo.getUserID(), goodsVo.getSellPrice());

            goodsVo.setSellPrice(vipPrice);

            return ReturnResultUtils.returnSuccess(goodsVo);


        } else {

            return ReturnResultUtils.returnSuccess(goodsVo);
        }

    }

}
