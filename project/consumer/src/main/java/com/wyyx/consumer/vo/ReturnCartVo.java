package com.wyyx.consumer.vo;
import lombok.Data;

import	java.io.Serializable;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-23 09:19
 * @description:
 ************************************************************/
@Data
public class ReturnCartVo implements  Serializable {
    private static final long serialVersionUID = -778679250919180569L;
    private int cartNum=0;

    private List CartGoods;
}
