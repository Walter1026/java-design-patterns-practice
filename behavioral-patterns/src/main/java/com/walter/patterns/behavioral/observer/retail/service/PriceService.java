package com.walter.patterns.behavioral.observer.retail.service;

import com.walter.patterns.behavioral.observer.retail.event.ProductPriceChangedEvent;
import com.walter.patterns.behavioral.observer.retail.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 商品价格服务（事件发布方 / Subject）
 *
 * <p>运营通过后台调整价格后，只需调用此方法；
 * 心愿单通知、促销同步等由各自的 Listener 独立处理。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PriceService {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 更新商品售价并发布价格变动事件
     *
     * @param product  目标商品
     * @param newPrice 新售价
     */
    public void updatePrice(Product product, BigDecimal newPrice) {
        BigDecimal oldPrice = product.getPrice();
        product.setPrice(newPrice);
        // 此处省略持久化

        log.info("[PriceService] 商品【{}】价格调整: {} -> {}", product.getProductName(), oldPrice, newPrice);
        eventPublisher.publishEvent(
                new ProductPriceChangedEvent(this, product, oldPrice, newPrice)
        );
    }
}
