package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.wyyx.provider.dto.ComProduct;

import com.wyyx.provider.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * **********************************************************
 *
 * @Project: 商品信息, 购物车信息
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-16 16:44
 * @description:
 ************************************************************/
@Slf4j
@Api(value = "购物业务")
@RestController
@RequestMapping(value = "/shop")
public class ShopController {
    @Reference
    private ShopService shopService;

    //kitty_zhu :首页展示分类商品
    @ApiOperation(value = "展示首页分类商品")
    @GetMapping(value = "/selectAllByClass")
    public List<ComProduct> selectAllByClass(@ApiParam(value = "每个分类你需要的个数") @RequestParam(value = "num") int num) {

        return shopService.selectAll(num);
    }

}
