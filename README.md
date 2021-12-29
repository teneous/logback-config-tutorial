# logback-config-tutorial

## Background

When the SpringBoot application integrated plug-in has multiple logback configuration files, and these plug-in
configuration files need to be integrated into the application.

- Three-party rpc plugin logback-rpc.xml
- Three party mq plugin logback-mq.xml
- Three-party redis plugin logback-redis.xml

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

These configurations are all middleware configurations, not our business. But all business applications should rely on
them to make the middleware log format of all business outputs consistent. Whether this is for APM or issue analysis,
the consistency of the specifications of these logs reduces the cost of understanding for developers. convention over
configuration.

How to load these configurations when the service starts is the point.

## Quick start

1. Start the LogbackTutorialApplication
2. curl localhost:8080/heartbeat
3. Observe the classpath. /log will generate two log files of the logback-plugin package

## Testing case

- plugin-a The plugin and the application belong to the same LogContext, and there may be a risk of overwriting
- plugin-b Plug-in and application hold their own LogContext

## Design Detail

I refer to Nacos design ideas, **com.alibaba.nacos.client.logging.logback.LogbackNacosLogging**.

I extend **ApplicationContextInitializer**, when the container starts, it will load the specified configuration file on
the classpath and load it into the LogbackContext.

```java
    LoggerContext loggerContext=(LoggerContext)StaticLoggerBinder.getSingleton().getLoggerFactory();
    new ContextInitializer(loggerContext).configureByResource(ResourceUtils.getURL(location));
```