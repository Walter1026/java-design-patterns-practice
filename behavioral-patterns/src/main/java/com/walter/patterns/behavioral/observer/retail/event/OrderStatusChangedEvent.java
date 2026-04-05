package com.walter.patterns.behavioral.observer.retail.event;

import com.walter.patterns.behavioral.observer.retail.model.Order;
import com.walter.patterns.behavioral.observer.retail.model.OrderStatus;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 订单状态变更事件（Spring ApplicationEvent 实现 Observer 模式）
 *
 * <p>在零售场景中，一笔订单从创建到履约涉及多个子系统：
 * 短信/站内通知、仓储 WMS、物流 TMS。使用 Spring 事件机制可以做到
 * 业务主流程（OrderService）与各下游系统完全解耦，新增观察者无需改动主流程。
 */
@Getter
public class OrderStatusChangedEvent extends ApplicationEvent {

    private final Order order;
    private final OrderStatus previousStatus;
    private final OrderStatus currentStatus;

    public OrderStatusChangedEvent(Object source, Order order,
                                   OrderStatus previousStatus,
                                   OrderStatus currentStatus) {
        super(source);
        this.order = order;
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
    }
}
