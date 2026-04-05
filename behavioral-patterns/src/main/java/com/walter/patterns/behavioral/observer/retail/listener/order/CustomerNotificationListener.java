package com.walter.patterns.behavioral.observer.retail.listener.order;

import com.walter.patterns.behavioral.observer.retail.event.OrderStatusChangedEvent;
import com.walter.patterns.behavioral.observer.retail.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 观察者①：客户通知监听器
 *
 * <p>监听订单关键状态节点，通过短信/站内信告知客户。
 * 新增或删除通知逻辑只需改动本类，不影响 OrderService。
 */
@Slf4j
@Component
public class CustomerNotificationListener {

    @EventListener
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        OrderStatus current = event.getCurrentStatus();
        String orderId = event.getOrder().getOrderId();
        String phone   = event.getOrder().getCustomerPhone();

        switch (current) {
            case PAID ->
                sendSms(phone, "您的订单 [" + orderId + "] 已付款成功，仓库正在备货，请耐心等待~");
            case SHIPPED ->
                sendSms(phone, "您的订单 [" + orderId + "] 已发货，请注意查收！");
            case DELIVERED ->
                sendSms(phone, "您的订单 [" + orderId + "] 已签收，感谢您的购买！欢迎评价。");
            case CANCELLED ->
                sendSms(phone, "您的订单 [" + orderId + "] 已取消，如有疑问请联系客服。");
            default -> { /* 其他状态无需通知客户 */ }
        }
    }

    private void sendSms(String phone, String message) {
        // 实际项目中调用短信网关 SDK（阿里云 SMS / 腾讯云 SMS 等）
        log.info("[SMS] -> {} : {}", phone, message);
    }
}
