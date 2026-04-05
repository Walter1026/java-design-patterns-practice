package com.walter.patterns.behavioral.observer.retail.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 零售订单领域模型
 */
@Data
@Builder
public class Order {
    private String orderId;
    private String customerId;
    private String customerPhone;
    private String customerEmail;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
