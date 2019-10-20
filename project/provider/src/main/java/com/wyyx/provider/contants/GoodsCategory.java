package com.wyyx.provider.contants;

/**
 * **********************************************************
 *
 * @Project:
 * @Author : Gavincoder
 * @Mail : xunyegege@gmail.com
 * @Github : https://github.com/xunyegege
 * @ver : version 1.0
 * @Date : 2019-10-18 13:53
 * @description:
 ************************************************************/

public enum GoodsCategory {

    SHOES(1, "鞋子"),
    CLOTHES(2, "衣服"),
    HAT(3, "帽子"),
    COMPUTER(4, "电子设备"),
    KITCHEN(5, "厨房用品");



    Integer category;
    String desc;



    public static GoodsCategory getByValue(int value){
        for(GoodsCategory transactType : values()){
            if (transactType.getCategory().equals(value)) {
                return transactType;
            }
        }
        return null;
    }

    GoodsCategory(Integer category, String desc) {
        this.category = category;
        this.desc = desc;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
