package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
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
 * @Date : 2019-10-21 07:49
 * @description:
 ************************************************************/
@Data
public class IndexVo implements Serializable {
    private static final long serialVersionUID = 7825908223560929834L;

    private String tempToken;
    private HashMap map;
    private List list;

}
