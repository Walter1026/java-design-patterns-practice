package com.walter.patterns.behavioral.observer.retail.event;

import com.walter.patterns.behavioral.observer.retail.model.Product;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

/**
 * 商品价格变动事件
 *
 * <p>适用场景：运营调整商品售价（促销降价 / 恢复原价）时，
 * 触发心愿单用户通知和促销系统同步，实现各模块解耦。
 */
@Getter
public class ProductPriceChangedEvent extends ApplicationEvent {

    private final Product product;
    private final BigDecimal previousPrice;
    private final BigDecimal currentPrice;

    public ProductPriceChangedEvent(Object source, Product product,
                                     BigDecimal previousPrice,
                                     BigDecimal currentPrice) {
        super(source);
        this.product       = product;
        this.previousPrice = previousPrice;
        this.currentPrice  = currentPrice;
    }

    /** 是否为降价（适合各 Listener 快速判断） */
    public boolean isPriceDown() {
        return currentPrice.compareTo(previousPrice) < 0;
    }

    /** 降价幅度百分比（保留两位小数） */
    public BigDecimal discountPercent() {
        if (previousPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return previousPrice.subtract(currentPrice)
                .multiply(BigDecimal.valueOf(100))
                .divide(previousPrice, 2, java.math.RoundingMode.HALF_UP);
    }
}
