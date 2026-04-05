package com.walter.patterns.behavioral.observer.retail.listener.order;

import com.walter.patterns.behavioral.observer.retail.event.OrderStatusChangedEvent;
import com.walter.patterns.behavioral.observer.retail.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 观察者③：物流 TMS 监听器
 *
 * <p>仓库打包完成后，自动向物流系统申请运单号并推送配送任务。
 */
@Slf4j
@Component
public class LogisticsListener {

    @Async
    @EventListener
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        if (event.getCurrentStatus() != OrderStatus.PACKED) {
            return;
        }

        String orderId = event.getOrder().getOrderId();
        String address = event.getOrder().getShippingAddress();
        log.info("[TMS] 申请运单，订单ID: {}, 目的地: {}", orderId, address);

        String trackingNo = applyWaybill(orderId, address);
        log.info("[TMS] 运单号: {}，已推送配送任务", trackingNo);
    }

    private String applyWaybill(String orderId, String address) {
        // 实际项目中调用顺丰/京东物流/菜鸟等 API
        return "SF" + System.currentTimeMillis();
    }
}
