package com.wyyx.consumer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kitty_zhu
 * @date 2019-10-19 10:07
 */
@Data
public class CommentVo implements Serializable {
    /**
     * 商品评价表
     */
    private Long id;

    /**
     * 商品的id
     */
    private Long productId;

    /**
     * 评价的用户id
     */
    private Long userId;

    /**
     * 评价等级(通过给0~5颗星评价)
     */
    private Integer rateLevel;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 该条记录的创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
