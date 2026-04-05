package com.walter.patterns.behavioral.observer.retail.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品领域模型
 */
@Data
@Builder
public class Product {
    private String productId;
    private String productName;
    private String skuCode;
    private BigDecimal price;
    private int stock;
    private int lowStockThreshold; // 低库存预警阈值
}
