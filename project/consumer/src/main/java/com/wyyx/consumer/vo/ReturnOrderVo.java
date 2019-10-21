package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-21 11:22
 * @description:
 ************************************************************/
@Data
public class ReturnOrderVo implements Serializable {
    private static final long serialVersionUID = 966883636330222424L;

    private Long totalSize;

    private int startPage;

    private int pageSize;

    private ArrayList list;

}
