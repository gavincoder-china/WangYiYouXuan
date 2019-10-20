package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-12 13:18
 * @description:
 ************************************************************/
@Data
public class SecGoodsVo implements Serializable {
    private static final long serialVersionUID = 397543711087142855L;

    private Long totalSize;

    private int startPage;

    private int pageSize;

    private List<GoodsVo> list;


}
