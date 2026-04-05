package com.walter.patterns.behavioral.observer.retail.model;

/**
 * 零售订单状态枚举
 */
public enum OrderStatus {
    CREATED,        // 订单创建
    PAID,           // 已支付
    PICKING,        // 仓库拣货中
    PACKED,         // 打包完成
    SHIPPED,        // 已发货
    DELIVERED,      // 已送达
    CANCELLED       // 已取消
}
