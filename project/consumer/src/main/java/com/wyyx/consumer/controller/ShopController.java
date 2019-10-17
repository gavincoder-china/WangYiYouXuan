package com.wyyx.consumer.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wyyx.consumer.vo.HomeVo;
import com.wyyx.consumer.vo.PageVo;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.mapper.ComProductMapper;
import com.wyyx.provider.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    /**
     * @author kitty_zhu
     * @date 2019-10-17 12:11
     */
    @ApiOperation(value = "展示首页分类商品")
    @GetMapping(value = "/selectAllByClass")
    public List<ComProduct> selectAllByClass(@ApiParam(value = "每个分类你需要的个数") @RequestParam(value = "num") int num) {

        return shopService.selectAll(num);
    }

    /**
     * @author kitty_zhu
     */
    @ApiOperation(value = "根据商品类型查询")
    @GetMapping(value = "/selectByClass")
    public List<ComProduct> selectByClass(@ApiParam(value = "查询商品的分类") @RequestParam(value = "p_type") int p_type, PageVo pageVo) {
        return shopService.selectByClass(p_type, pageVo.getStart(), pageVo.getPageSize());
    }

    /**
     * @author kitty_zhu
     */
    @ApiOperation(value = "模糊查询")
    @GetMapping(value = "/selectByName")
    public List<ComProduct> selectByName(@ApiParam(value = "查询的商品名字") @RequestParam(value = "name") String name,
                                         @Valid PageVo pageVo) {
        return shopService.selectByName(name, pageVo.getStart(), pageVo.getPageSize());
    }
}
