package com.wyyx.consumer.advanceData;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project:  自动执行一些事情
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-14 11:10
 * @description:
 ************************************************************/
@Component
public class DataToCache {



    /**
     * @return
     * @throws
     * @description 在服务启动时查询数据库数据将库存信息存到缓存中
     * @author Gavin
     * @date 2019-10-14 11:12
     * @since
     */

    @PostConstruct
    public void getGoodsToRedis() {


    }



}
