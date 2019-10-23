package com.wyyx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wyyx.consumer.annotationCustom.method.RequireLoginMethod;
import com.wyyx.consumer.annotationCustom.parameter.RequireLoginParam;
import com.wyyx.consumer.contants.ReturnResultContants;
import com.wyyx.consumer.queue.activeMQ.ActiveMQUtils;
import com.wyyx.consumer.result.ReturnResult;
import com.wyyx.consumer.result.ReturnResultUtils;
import com.wyyx.consumer.util.RedisUtil;
import com.wyyx.consumer.vo.GoodsVo;
import com.wyyx.consumer.vo.PageVo;
import com.wyyx.consumer.vo.SecGoodsVo;
import com.wyyx.consumer.vo.UserVo;
import com.wyyx.provider.contants.CommonContants;
import com.wyyx.provider.dto.SecProduct;
import com.wyyx.provider.service.SecKillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project: 秒杀业务
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-16 16:44
 * @description:
 ************************************************************/
@Slf4j
@Api(value = "秒杀业务")
@RestController
@RequestMapping(value = "/seckill")
public class SecKillController {

    @Reference
    private SecKillService secKillService;
    @Autowired
    private ActiveMQUtils activeMQUtils;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @return
     * @throws
     * @description 展示全部商品
     * @author Gavin
     * @date 2019-10-11 14:27
     * @since
     */

    @ApiOperation(value = "展示所有商品")
    @GetMapping("/showAllProducts")
    public ReturnResult showAllProducts(@Valid PageVo pageVo) {

        List<SecProduct> lists = secKillService.selectAllSecGoods(pageVo.getStart(), pageVo.getPageSize());

        ArrayList<GoodsVo> listTemp = new ArrayList<>();

        lists.forEach(list -> {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(list, goodsVo);
            listTemp.add(goodsVo);
        });

        SecGoodsVo secGoodsVo = new SecGoodsVo();

        secGoodsVo.setList(listTemp);
        secGoodsVo.setTotalSize(secKillService.count());
        secGoodsVo.setStartPage(pageVo.getStartPage());
        secGoodsVo.setPageSize(pageVo.getPageSize());

        return ReturnResultUtils.returnSuccess(secGoodsVo);
    }


    /**
     * @return
     * @throws
     * @description 通过商品id购买, 从redis中拿取用户的id(需要登录)  这边做消息队列操作,返回数据给支付逻辑
     * @author Gavin
     * @date 2019-10-11 14:28
     * @since
     */

    @RequireLoginMethod
    @ApiOperation(value = "选择商品,使用消息队列完成订单生成操作")
    @PostMapping("/buyProductByQueue")
    public ReturnResult buyProductByQueue(@ApiParam(value = "商品id", required = true)
                                          @RequestParam(value = "pID") Long pID,
                                          @RequireLoginParam UserVo userVo) {

        //加锁,检验锁
        boolean lockResult = redisUtil.lock(CommonContants.HOT_LOCK + pID, 1, 600);

        while (lockResult) {
            //查用户是否购买过?


            if (!redisUtil.hasKey(CommonContants.SECKILL_IS + pID + userVo.getUserID())) {

                //查该商品库存
                Long inventory = secKillService.selectInventoryById(pID);

                if (inventory >= 1) {

                    //发送信息到消息队列,让生成订单的方法消费
                    activeMQUtils.sendQueueMesage("seckill", pID + "@" + userVo.getUserID());

                    return ReturnResultUtils.returnSuccess(ReturnResultContants.CODE_SECKILL_CREATE_ORDER,
                                                           ReturnResultContants.MSG_SECKILL_CREATE_ORDER);

                }
            }
            //如果进来后不满足需求,则解锁
            redisUtil.delLock(CommonContants.HOT_LOCK + pID);
        }

        return ReturnResultUtils.returnFail(ReturnResultContants.CODE_SEC_KILL_FAIL, ReturnResultContants.MSG_SEC_KILL_FAIL);
    }



}
