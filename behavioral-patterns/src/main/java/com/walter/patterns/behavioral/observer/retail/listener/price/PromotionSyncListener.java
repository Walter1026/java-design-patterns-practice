package com.walter.patterns.behavioral.observer.retail.listener.price;

import com.walter.patterns.behavioral.observer.retail.event.ProductPriceChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 观察者②：促销系统同步监听器
 *
 * <p>价格变动后，同步刷新大促活动页（如满减、秒杀）中的展示价格，
 * 保证促销展示价与商品实际售价一致，避免价格不一致的纠纷。
 */
@Slf4j
@Component
public class PromotionSyncListener {

    @EventListener
    public void onProductPriceChanged(ProductPriceChangedEvent event) {
        String productId   = event.getProduct().getProductId();
        String productName = event.getProduct().getProductName();

        log.info("[Promotion] 同步价格到促销系统 - 商品: {}, 新价格: {}",
                productName, event.getCurrentPrice());

        refreshPromotionPrice(productId, event.getCurrentPrice());
        invalidateProductCache(productId);
    }

    private void refreshPromotionPrice(String productId, java.math.BigDecimal newPrice) {
        // 调用促销系统 RPC / MQ 同步最新展示价
        log.info("[Promotion] 促销展示价已更新，productId={}, price={}", productId, newPrice);
    }

    private void invalidateProductCache(String productId) {
        // 清除商品详情页缓存（Redis），触发下次访问时重新加载
        log.info("[Promotion] 商品缓存已失效，productId={}", productId);
    }
}
