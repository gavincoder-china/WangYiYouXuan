package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.CartCountsIndexUtil;
import com.wyyx.consumer.util.GetIpAddressUtil;
import com.wyyx.consumer.util.RandomGoodUtil;
import com.wyyx.consumer.vo.GoodsVoC;
import com.wyyx.consumer.vo.HomeVoC;
import com.wyyx.consumer.vo.IndexGoodsVo;
import com.wyyx.consumer.vo.PageVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.contants.GoodsCategory;
import com.wyyx.provider.dto.ComProduct;
import com.wyyx.provider.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-21 19:23
 * @description:
 ************************************************************/
@Slf4j
@Api(value = "购物业务")
@RestController
@RequestMapping(value = "/shopC")
public class ShopControllerC {

    @Reference
    private ShopService shopService;

    @Autowired
    private GetIpAddressUtil getIpAddressUtil;

    @Autowired
    private RandomGoodUtil randomGoodUtil;

    @Autowired
    private CartCountsIndexUtil cartCountsIndexUtil;

    @ApiOperation(value = "首页")
    @GetMapping(value = "/selectIndex")
    public ReturnResult<HomeVoC> selectIndex(HttpServletRequest request) {


        HomeVoC homeVoC = new HomeVoC();

        homeVoC.setIp(getIpAddressUtil.getIpAddr(request));


        HashMap<Object, Object> map = new HashMap<>();
        map.put(CommonContants.INDEX_IP + GoodsCategory.SHOES.getCategory(), GoodsCategory.SHOES.getDesc());
        map.put(CommonContants.INDEX_IP + GoodsCategory.CLOTHES.getCategory(), GoodsCategory.CLOTHES.getDesc());
        map.put(CommonContants.INDEX_IP + GoodsCategory.HAT.getCategory(), GoodsCategory.HAT.getDesc());
        map.put(CommonContants.INDEX_IP + GoodsCategory.COMPUTER.getCategory(), GoodsCategory.COMPUTER.getDesc());
        map.put(CommonContants.INDEX_IP + GoodsCategory.KITCHEN.getCategory(), GoodsCategory.KITCHEN.getDesc());


        homeVoC.setCategory(map);

        homeVoC.setDefaultProduct(shopService.randomProduct().getName());
        homeVoC.setTempToken(request.getSession().getId());


        return ReturnResultUtils.returnSuccess(homeVoC);

    }


    @ApiOperation(value = "根据商品类型查询")
    @GetMapping(value = "/selectByType")
    public ReturnResult<GoodsVoC> selectByType(@ApiParam(value = "查询商品的分类,1厨房用品, 2水果, 3衣服,4家电")
                                    @RequestParam(value = "p_type") int p_type,
                                     @Valid PageVo pageVo) {

        List<ComProduct> comProducts = shopService.selectByClass(p_type, pageVo.getStart(),
                                                                 pageVo.getPageSize());

        ComProduct defaultPro = randomGoodUtil.randomList(comProducts);


        ArrayList listTemp = new ArrayList<>();

        comProducts.forEach(list -> {
            IndexGoodsVo indexGoodsVo = new IndexGoodsVo();

            BeanUtils.copyProperties(list, indexGoodsVo);
            listTemp.add(indexGoodsVo);
        });


        GoodsVoC goodsVoC = new GoodsVoC();


        goodsVoC.setGoodsList(listTemp);
        goodsVoC.setDefaultProduct(defaultPro.getName());
        goodsVoC.setStartPage(pageVo.getStartPage());
        goodsVoC.setPageSize(pageVo.getPageSize());
        goodsVoC.setTotalSize(shopService.selectByClassCount(p_type));

        //判断用户搜索的商品是否存在(长度为0，就代表是空的)
        if (comProducts.size() == 0) {
            return ReturnResultUtils.returnFail(ReturnResultContants.CODE_NOT_FIND_GOODS,
                                                ReturnResultContants.MSG_NOT_FIND_GOODS);
        }


        return ReturnResultUtils.returnSuccess(goodsVoC);
    }

}
