package com.ccqstark.warehouseservicedubbo.dubbo;

import com.ccqstark.warehouseservicedubbo.dto.Stock;

//Provider接口
public interface WarehouseService {
    //查询库存
    public Stock getStock(Long skuId);
}