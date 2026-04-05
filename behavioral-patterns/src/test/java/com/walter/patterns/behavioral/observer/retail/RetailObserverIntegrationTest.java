package com.walter.patterns.behavioral.observer.retail;

import com.walter.patterns.behavioral.observer.retail.model.Order;
import com.walter.patterns.behavioral.observer.retail.model.OrderStatus;
import com.walter.patterns.behavioral.observer.retail.model.Product;
import com.walter.patterns.behavioral.observer.retail.service.InventoryService;
import com.walter.patterns.behavioral.observer.retail.service.OrderService;
import com.walter.patterns.behavioral.observer.retail.service.PriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Observer 模式零售场景集成测试
 *
 * <p>验证三个核心场景的事件发布与监听是否正常工作。
 */
@SpringBootTest(classes = RetailObserverConfig.class)
class RetailObserverIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PriceService priceService;

    // ------------------------------------------------
    // 场景一：订单状态变更
    // ------------------------------------------------

    @Test
    @DisplayName("场景一: 订单支付后通知仓库拣货和发送客户短信")
    void testOrderStatusChange_PaidToShipped() {
        Order order = Order.builder()
                .orderId("ORD-20260404-001")
                .customerId("C001")
                .customerPhone("138****8888")
                .customerEmail("customer@example.com")
                .shippingAddress("上海市浦东新区XXX路100号")
                .totalAmount(new BigDecimal("299.00"))
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        // CREATED -> PAID：CustomerNotificationListener + WarehouseListener 均会收到事件
        orderService.updateOrderStatus(order, OrderStatus.PAID);

        // PAID -> PACKED：LogisticsListener 申请运单
        orderService.updateOrderStatus(order, OrderStatus.PACKED);

        // PACKED -> SHIPPED：CustomerNotificationListener 推送发货短信
        orderService.updateOrderStatus(order, OrderStatus.SHIPPED);
    }

    @Test
    @DisplayName("场景一: 订单取消后仓库释放锁定库存")
    void testOrderStatusChange_Cancel() {
        Order order = Order.builder()
                .orderId("ORD-20260404-002")
                .customerId("C002")
                .customerPhone("139****9999")
                .shippingAddress("北京市朝阳区XXX大厦")
                .totalAmount(new BigDecimal("599.00"))
                .status(OrderStatus.PAID)
                .createdAt(LocalDateTime.now())
                .build();

        orderService.updateOrderStatus(order, OrderStatus.CANCELLED);
    }

    // ------------------------------------------------
    // 场景二：低库存预警
    // ------------------------------------------------

    @Test
    @DisplayName("场景二: 库存降至阈值以下，自动触发补货和供应商通知")
    void testInventoryLowAlert() {
        Product product = Product.builder()
                .productId("P001")
                .productName("无线蓝牙耳机 Pro")
                .skuCode("SKU-BT-PRO-001")
                .price(new BigDecimal("399.00"))
                .stock(15)
                .lowStockThreshold(10)
                .build();

        // 扣减 8 件，剩余 7，低于阈值 10，触发预警事件
        inventoryService.deductStock(product, 8);
    }

    // ------------------------------------------------
    // 场景三：商品价格变动
    // ------------------------------------------------

    @Test
    @DisplayName("场景三: 商品降价后通知心愿单用户并同步促销系统")
    void testProductPriceDown() {
        Product product = Product.builder()
                .productId("P002")
                .productName("智能手表 S8")
                .skuCode("SKU-WATCH-S8")
                .price(new BigDecimal("1299.00"))
                .stock(100)
                .lowStockThreshold(20)
                .build();

        // 降价到 999，触发心愿单通知 + 促销同步
        priceService.updatePrice(product, new BigDecimal("999.00"));
    }

    @Test
    @DisplayName("场景三: 商品涨价后仅同步促销系统（不打扰心愿单用户）")
    void testProductPriceUp() {
        Product product = Product.builder()
                .productId("P003")
                .productName("运动跑鞋 X10")
                .skuCode("SKU-SHOE-X10")
                .price(new BigDecimal("399.00"))
                .stock(50)
                .lowStockThreshold(10)
                .build();

        // 涨价，WishlistNotificationListener 上有 condition 过滤，不会触发
        priceService.updatePrice(product, new BigDecimal("459.00"));
    }
}
