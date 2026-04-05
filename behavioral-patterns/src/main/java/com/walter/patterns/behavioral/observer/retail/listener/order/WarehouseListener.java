package com.walter.patterns.behavioral.observer.retail.listener.order;

import com.walter.patterns.behavioral.observer.retail.event.OrderStatusChangedEvent;
import com.walter.patterns.behavioral.observer.retail.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 观察者②：仓储 WMS 监听器
 *
 * <p>订单支付后推送拣货任务；订单取消后释放已锁定库存。
 * 使用 @Async 异步处理，避免阻塞订单主流程。
 */
@Slf4j
@Component
public class WarehouseListener {

    @Async
    @EventListener
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        String orderId = event.getOrder().getOrderId();

        if (event.getCurrentStatus() == OrderStatus.PAID) {
            log.info("[WMS] 创建拣货任务，订单ID: {}, 收货地址: {}",
                    orderId, event.getOrder().getShippingAddress());
            // 调用 WMS 接口推送拣货单
            createPickingTask(orderId);
        }

        if (event.getCurrentStatus() == OrderStatus.CANCELLED
                && event.getPreviousStatus() == OrderStatus.PAID) {
            log.info("[WMS] 释放锁定库存，订单ID: {}", orderId);
            // 调用 WMS 接口回滚库存
            releaseLockedStock(orderId);
        }
    }

    private void createPickingTask(String orderId) {
        log.info("[WMS] 拣货任务已下发，orderId={}", orderId);
    }

    private void releaseLockedStock(String orderId) {
        log.info("[WMS] 锁定库存已释放，orderId={}", orderId);
    }
}
