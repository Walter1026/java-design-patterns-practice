package com.walter.patterns.behavioral.observer.retail;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 零售 Observer 场景测试用 Spring Boot 配置类
 *
 * <p>仅用于集成测试，扫描本包下所有 @Component / @Service。
 * 生产项目中请合并到主 Application 类，并按需拆分模块。
 */
@EnableAsync
@SpringBootApplication
public class RetailObserverConfig {
}
