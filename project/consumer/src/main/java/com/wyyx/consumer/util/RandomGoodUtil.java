package com.wyyx.consumer.util;

import com.wyyx.provider.dto.ComProduct;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * **********************************************************
 *
 * @Project: 随机展示商品
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-22 08:26
 * @description:
 ************************************************************/
@Component
public class RandomGoodUtil {

  public ComProduct randomList(List<ComProduct> list){

      Random random = new Random();
      ComProduct product = list.get(random.nextInt(list.size()));

      return product;


  }



}
