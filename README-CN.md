# logback-config-tutorial

## 背景

当SpringBoot应用集成的插件有多个logback配置文件，并且需要将这些插件配置文件集成到应用中时。

- 三方rpc插件 logback-rpc.xml
- 三方mq插件 logback-mq.xml
- 三方redis插件 logback-redis.xml

### logback-rpc.xml

```xml

<appender>
    ...
</appender>
<logger>
...
</logger>
```

### logback-mq.xml

```xml

<appender>
    ...
</appender>
<logger>
...
</logger>
```

### logback-redis.xml

```xml

<appender>
    ...
</appender>
<logger>
...
</logger>
```

这些配置都是中间件的配置，它们与业务无关。但是所有的业务应用都应该依赖它们，使其所有业务输出的中间件日志格式一致。 这无论是在后面做性能分析，还是排查错误，这些日志的规格一致性降低了开发人员的理解成本，所谓约定大于配置。

如何使服务启动时装载这些配置则成了难点。

## 快速开始

1. 启动LogbackTutorialApplication项目
2. curl localhost:8080/heartbeat
3. 观察类路径./log会生成logback-plugin包的两个日志文件

## 测试场景

- plugin-a 插件和应用同属一个LogContext,可能存在覆盖风险（依托于springboot）
- plugin-b 插件和应用各自独享自己的LogContext,互不干扰

## 设计细节

我参考了Nacos设计思想，**com.alibaba.nacos.client.logging.logback.LogbackNacosLogging**
结合**ApplicationContextInitializer**，在容器启动的时候会去装载类路径上的指定配置文件，装载到LogbackContext中。

```java
  LoggerContext loggerContext=(LoggerContext)StaticLoggerBinder.getSingleton().getLoggerFactory();
new ContextInitializer(loggerContext).configureByResource(ResourceUtils.getURL(location));
```


