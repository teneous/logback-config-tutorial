package com.syoka.tutorial.logback.plugin.b.autoconfigure.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.syoka.tutorial.logback.plugin.b.utils.LogUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;

/**
 * @author syoka
 */
public class PluginBLogbackInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String PLUGIN_LOGGING_CONFIG_PROPERTY = "plugin.b.logging.config";

    private static final String PLUGIN_LOGGING_DEFAULT_CONFIG_ENABLED_PROPERTY = "true";

    private static final String PLUGIN_LOGBACK_LOCATION = "classpath:plugin-b-logback.xml";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
        PropertySource<?> propertySource = propertySources.get("bootstrap");
        if (propertySource != null) {
            // boostrap do nothing
            return;
        }
        String location = getLocation(PLUGIN_LOGBACK_LOCATION);
        try {
            LoggerContext pluginBLoggerContext = LogUtils.getPluginLoggerContext();
            new ContextInitializer(pluginBLoggerContext).configureByResource(ResourceUtils.getURL(location));
        } catch (Exception e) {
            throw new IllegalStateException("Could not initialize Logback Plugin logging from " + location, e);
        }
    }

    /**
     * load plugin's logback config file
     *
     * @param defaultLocation defaultLocation
     * @return logback config location
     */
    protected String getLocation(String defaultLocation) {
        String location = System.getProperty(PLUGIN_LOGGING_CONFIG_PROPERTY);
        if (StringUtils.isEmpty(location)) {
            if (isDefaultConfigEnabled()) {
                return defaultLocation;
            }
            return null;
        }
        return location;
    }

    /**
     * weather enable default logback config
     *
     * @return true -> enable
     */
    private boolean isDefaultConfigEnabled() {
        String property = System.getProperty(PLUGIN_LOGGING_DEFAULT_CONFIG_ENABLED_PROPERTY);
        // The default value is true.
        return property == null || Boolean.getBoolean(property);
    }
}
