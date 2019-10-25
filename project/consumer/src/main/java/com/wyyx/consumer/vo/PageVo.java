package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-12 12:23
 * @description:
 ************************************************************/
@Data
@ApiModel(value = "分页类")
public class PageVo implements Serializable {
    private static final long serialVersionUID = -3100127038228210025L;

    @ApiModelProperty(value = "起始页")
    private int startPage;

    @ApiModelProperty(value = "每页条数")
    private int pageSize;

    //获取起始limit的下标

    public int getStart() {

        if (startPage >= 1) {
            return (startPage - 1) * pageSize;
        }
        return 0;
    }
}
