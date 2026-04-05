package com.walter.patterns.behavioral.observer.retail.service;

import com.walter.patterns.behavioral.observer.retail.event.OrderStatusChangedEvent;
import com.walter.patterns.behavioral.observer.retail.model.Order;
import com.walter.patterns.behavioral.observer.retail.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 订单服务（事件发布方 / Subject）
 *
 * <p>职责：维护订单核心状态流转逻辑，通过 ApplicationEventPublisher
 * 发布 OrderStatusChangedEvent，不关心任何下游处理细节。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 更新订单状态，并发布变更事件通知所有观察者
     */
    public void updateOrderStatus(Order order, OrderStatus newStatus) {
        OrderStatus previousStatus = order.getStatus();
        order.setStatus(newStatus);
        // 此处省略持久化逻辑（updateById）

        log.info("[OrderService] 订单 {} 状态变更: {} -> {}", order.getOrderId(), previousStatus, newStatus);

        // 发布事件 —— 所有注册了该事件的 Listener 均会被通知
        eventPublisher.publishEvent(
                new OrderStatusChangedEvent(this, order, previousStatus, newStatus)
        );
    }
}
