# Java Design Patterns Practice
这是一个用来练习 **Java 设计模式** 的 Maven 多模块仓库。
## 目录结构
- `creational-patterns`：创建型模式练习
- `structural-patterns`：结构型模式练习
- `behavioral-patterns`：行为型模式练习
## 当前内容
- `Singleton`：提供了一个线程安全的懒汉式单例示例和单元测试
## 使用方式
```bash
mvn test
```
## 建议练习方式
1. 每种设计模式新建一个独立包，例如：`com.walter.patterns.structural.adapter`
2. 每个模式至少包含：
   - 核心实现类
   - 一个使用示例或测试
   - 简短说明（可写在测试名或 README）
3. 完成一个模式后提交一次，方便回顾学习轨迹
