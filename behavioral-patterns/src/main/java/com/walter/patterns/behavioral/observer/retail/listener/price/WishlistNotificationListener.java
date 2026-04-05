package com.walter.patterns.behavioral.observer.retail.listener.price;

import com.walter.patterns.behavioral.observer.retail.event.ProductPriceChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 观察者①：心愿单用户通知监听器
 *
 * <p>商品降价时，向所有将该商品加入心愿单的用户推送降价通知，
 * 刺激转化。涨价场景无需打扰用户，静默处理。
 */
@Slf4j
@Component
public class WishlistNotificationListener {

    @Async
    @EventListener(condition = "#a0.priceDown") // 仅降价时触发，#a0 指方法第一个参数
    public void onPriceDown(ProductPriceChangedEvent event) {
        String productName = event.getProduct().getProductName();
        String productId   = event.getProduct().getProductId();

        List<String> wishlistUsers = queryWishlistUsers(productId);
        log.info("[Wishlist] 商品【{}】降价 {}%，向 {} 位用户推送通知",
                productName, event.discountPercent(), wishlistUsers.size());

        wishlistUsers.forEach(userId ->
                sendWishlistAlert(userId, productName,
                        event.getPreviousPrice(), event.getCurrentPrice())
        );
    }

    private List<String> queryWishlistUsers(String productId) {
        // 实际项目中查询心愿单表，返回关注该商品的用户 ID 列表
        return List.of("user_001", "user_002", "user_003");
    }

    private void sendWishlistAlert(String userId, String productName,
                                    java.math.BigDecimal oldPrice,
                                    java.math.BigDecimal newPrice) {
        // 发送站内信 / Push 通知
        log.info("[Wishlist] 通知用户 {} - 您关注的【{}】已降价：{} -> {}",
                userId, productName, oldPrice, newPrice);
    }
}
