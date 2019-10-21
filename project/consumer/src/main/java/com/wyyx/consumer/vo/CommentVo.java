package com.wyyx.consumer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kitty_zhu
 * @date 2019-10-19 10:07
 */
@Data
@ApiModel(value = "商品评价")
public class CommentVo implements Serializable {
    /**
     * 商品评价表id
     */
    @ApiModelProperty(value = "商品评价表id")
    private Long id;

    /**
     * 商品的id
     */
    @ApiModelProperty(value = "商品的id")
    private Long productId;

    /**
     * 评价的用户id
     */
    @ApiModelProperty(value = "评价的用户id")
    private Long userId;

    /**
     * 评价等级(通过给0~5颗星评价)
     */
    @ApiModelProperty(value = "评价等级(通过给0~5颗星评价)")
    private Integer rateLevel;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String comment;

    /**
     * 该条记录的创建时间
     */
    @ApiModelProperty(value = "该条记录的创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
