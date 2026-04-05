package com.walter.patterns.behavioral.observer.retail.listener.inventory;

import com.walter.patterns.behavioral.observer.retail.event.InventoryLowAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 观察者②：供应商预警通知监听器
 *
 * <p>低库存时同步通知对应供应商，提前启动备货流程，减少补货等待时间。
 */
@Slf4j
@Component
public class SupplierNotificationListener {

    @EventListener
    public void onInventoryLowAlert(InventoryLowAlertEvent event) {
        String skuCode     = event.getProduct().getSkuCode();
        String productName = event.getProduct().getProductName();
        int    stock       = event.getCurrentStock();

        log.info("[Supplier] 发送预警通知 - 商品【{}】(SKU:{}) 库存仅剩 {}，请尽快安排备货",
                productName, skuCode, stock);
        // 实际项目中发送邮件/企微机器人/ERP 接口通知供应商
        notifySupplier(skuCode, productName, stock);
    }

    private void notifySupplier(String skuCode, String productName, int stock) {
        log.info("[Supplier] 通知已发出，供应商将在 24h 内响应");
    }
}
