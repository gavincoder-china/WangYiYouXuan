package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.vo.GoodsVo;
import com.wyyx.consumer.vo.HomeVo;
import com.wyyx.consumer.vo.IndexVo;
import com.wyyx.consumer.vo.PageVo;
import com.wyyx.provider.contants.GoodsCategory;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
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
    public ReturnResult selectAllByClass(@ApiParam(value = "每个分类你需要的个数")
                                         @RequestParam(value = "num") int num,
                                         HttpServletRequest request) {

        IndexVo indexVo = new IndexVo();
        indexVo.setTempToken(request.getSession().getId());

        HashMap<Object, Object> map = new HashMap<>();
        map.put(GoodsCategory.SHOES.getCategory(),GoodsCategory.SHOES.getDesc());
        map.put(GoodsCategory.CLOTHES.getCategory(),GoodsCategory.CLOTHES.getDesc());
        map.put(GoodsCategory.HAT.getCategory(),GoodsCategory.HAT.getDesc());
        map.put(GoodsCategory.COMPUTER.getCategory(),GoodsCategory.COMPUTER.getDesc());
        map.put(GoodsCategory.KITCHEN.getCategory(),GoodsCategory.KITCHEN.getDesc());

        //每类的销量前几个
        List<ComProduct> comProducts = shopService.selectAll(num);
        indexVo.setMap(map);
        indexVo.setList(comProducts);


        return ReturnResultUtils.returnSuccess(indexVo);
    }




    /**
     * @author kitty_zhu
     */
    @ApiOperation(value = "根据商品类型查询")
    @GetMapping(value = "/selectByClass")
    public ReturnResult selectByClass(@ApiParam(value = "查询商品的分类")
                                      @RequestParam(value = "p_type") int p_type,
                                      @Valid PageVo pageVo) {
        List<ComProduct> comProducts = shopService.selectByClass(p_type, pageVo.getStart(),
                                                                 pageVo.getPageSize());
        long total = shopService.selectByClassCount(p_type);

        ArrayList<GoodsVo> listTemp = new ArrayList<>();

        comProducts.forEach(list -> {
            GoodsVo vo = new GoodsVo();
            BeanUtils.copyProperties(list, vo);
            listTemp.add(vo);
        });

        HomeVo homeVo = new HomeVo();
        homeVo.setTotalSize(total);
        homeVo.setStartPage(pageVo.getStartPage());
        homeVo.setPageSize(pageVo.getPageSize());
        homeVo.setList(listTemp);
        //todo 枚举当前的分类

        homeVo.setCategory(GoodsCategory.getByValue(p_type).getDesc());


        //判断用户搜索的商品是否存在(长度为0，就代表是空的)
        if (total == 0) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_GOODS,
                                                ReturnResultContants.MSG_NOT_FIND_GOODS);
        }
        return ReturnResultUtils.returnSuccess(homeVo);
    }

    /**
     * @author kitty_zhu
     */
    @ApiOperation(value = "模糊查询")
    @GetMapping(value = "/selectByName")
    public ReturnResult selectByName(@ApiParam(value = "查询的商品名字")
                                     @RequestParam(value = "name") String name,
                                     @Valid PageVo pageVo) {

        List<ComProduct> comProducts = shopService.selectByName(name, pageVo.getStart(),
                                                                pageVo.getPageSize());

        long total = shopService.selectByNameCount(name);

        ArrayList<GoodsVo> listTemp = new ArrayList<>();

        comProducts.forEach(list -> {
            GoodsVo vo = new GoodsVo();
            BeanUtils.copyProperties(list, vo);
            listTemp.add(vo);
        });

        HomeVo homeVo = new HomeVo();
        homeVo.setTotalSize(total);
        homeVo.setStartPage(pageVo.getStartPage());
        homeVo.setPageSize(pageVo.getPageSize());
        homeVo.setList(listTemp);


        //判断用户搜索的商品是否存在(长度为0，就代表是空的)
        if (total == 0) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_GOODS,
                                                ReturnResultContants.MSG_NOT_FIND_GOODS);
        }

        return ReturnResultUtils.returnSuccess(homeVo);
    }

    /**
     * @author kitty_zhu
     */
    @ApiOperation(value = "火爆商品默认查询")
    @GetMapping(value = "/selectByHot")
    public ReturnResult selectByHot() {

        List<ComProduct> comProducts = shopService.selectByHot();
        return ReturnResultUtils.returnSuccess(comProducts);
    }
}
