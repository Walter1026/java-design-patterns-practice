package com.walter.patterns.behavioral.observer.retail.service;

import com.walter.patterns.behavioral.observer.retail.event.InventoryLowAlertEvent;
import com.walter.patterns.behavioral.observer.retail.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 库存服务（事件发布方 / Subject）
 *
 * <p>负责扣减库存，并在库存低于阈值时发布预警事件。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 扣减库存后检测是否触发低库存预警
     *
     * @param product  目标商品
     * @param quantity 扣减数量
     */
    public void deductStock(Product product, int quantity) {
        int remaining = product.getStock() - quantity;
        if (remaining < 0) {
            throw new IllegalStateException("库存不足，SKU: " + product.getSkuCode());
        }
        product.setStock(remaining);
        log.info("[Inventory] SKU {} 库存扣减 {}，剩余: {}", product.getSkuCode(), quantity, remaining);
        // 此处省略持久化

        if (remaining <= product.getLowStockThreshold()) {
            log.warn("[Inventory] SKU {} 触发低库存预警，当前库存: {}", product.getSkuCode(), remaining);
            eventPublisher.publishEvent(
                    new InventoryLowAlertEvent(this, product, remaining, product.getLowStockThreshold())
            );
        }
    }
}
