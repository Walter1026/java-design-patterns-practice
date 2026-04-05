package com.walter.patterns.behavioral.observer.retail.listener.inventory;

import com.walter.patterns.behavioral.observer.retail.event.InventoryLowAlertEvent;
import com.walter.patterns.behavioral.observer.retail.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 观察者①：自动采购补货监听器
 *
 * <p>收到低库存预警后，向采购系统提交补货申请单。
 * 补货数量 = 安全库存的 2 倍 - 当前库存（简单策略，可替换为更复杂的算法）。
 */
@Slf4j
@Component
public class PurchaseOrderListener {

    @Async
    @EventListener
    public void onInventoryLowAlert(InventoryLowAlertEvent event) {
        Product product = event.getProduct();
        int replenishQty = product.getLowStockThreshold() * 2 - event.getCurrentStock();

        log.info("[Purchase] 创建补货申请 - SKU: {}, 商品: {}, 补货数量: {}",
                product.getSkuCode(), product.getProductName(), replenishQty);
        // 调用采购系统 API 创建采购单
        createPurchaseOrder(product.getSkuCode(), replenishQty);
    }

    private void createPurchaseOrder(String skuCode, int qty) {
        log.info("[Purchase] 采购单已提交，SKU={}, qty={}", skuCode, qty);
    }
}
