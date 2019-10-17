package com.wyyx.consumer.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author kitty_zhu
 * @date 2019-10-17 12:11
 * 网页首页的分组商品信息
 */
public class HomeVo {
    /**
     * 普通商品id
     */
    private Long id;

    /**
     * 普通商品名
     */
    private String name;

    /**
     * 普通商品的描述信息
     */
    private String description;

    /**
     * 普通商品的图片地址
     */
    private String imgurl;

    /**
     * 普通商品的好评率
     */
    private Integer goodratio;

    /**
     * 普通商品的进价
     */
    private BigDecimal buyPrice;

    /**
     * 普通商品的售价
     */
    private BigDecimal sellPrice;

    /**
     * 普通商品的库存
     */
    private Long inventory;

    /**
     * 普通商品的销量
     */
    private Long sales;

    /**
     * 普通商品的状态(-1代表已删除;0代表已下架;1代表已上架)
     */
    private Byte status;

    /**
     * 该条记录创建时间
     */
    private Date createTime;

    /**
     * 商品分类
     */
    private Integer pType;

    /**
     * 起始页
     */
    private int startPage;

    /**
     * 每页多少条
     */
    private int pageSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getGoodratio() {
        return goodratio;
    }

    public void setGoodratio(Integer goodratio) {
        this.goodratio = goodratio;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        int i = 1;
        if (startPage >= i) {
            startPage = (startPage - i) * pageSize;
        }
        this.startPage = startPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
