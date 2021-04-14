package com.ccqstark.orderservice.dto;

import lombok.Data;

//库存商品对象
@Data
public class Stock {
    private Long skuId; //商品品类编号
    private String title; //商品与品类名称
    private Integer quantity; //库存数量
    private String unit; //单位
    private String description; //描述信息

    @Override
    public String toString() {
        return "Stock{" +
                "skuId=" + skuId +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }

}