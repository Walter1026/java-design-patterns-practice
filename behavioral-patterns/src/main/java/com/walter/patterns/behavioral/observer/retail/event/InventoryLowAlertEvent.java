package com.walter.patterns.behavioral.observer.retail.event;

import com.walter.patterns.behavioral.observer.retail.model.Product;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 商品低库存预警事件
 *
 * <p>当某 SKU 库存扣减后低于预警阈值时发布此事件，
 * 采购系统和供应商通知系统作为独立观察者自动响应。
 */
@Getter
public class InventoryLowAlertEvent extends ApplicationEvent {

    private final Product product;
    private final int currentStock;
    private final int threshold;

    public InventoryLowAlertEvent(Object source, Product product,
                                   int currentStock, int threshold) {
        super(source);
        this.product = product;
        this.currentStock = currentStock;
        this.threshold = threshold;
    }
}
