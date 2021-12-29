package com.syoka.tutorial.logback.plugin.b.utils;

import org.slf4j.Logger;

import ch.qos.logback.classic.LoggerContext;

/**
 * @author syoka
 * @since 2021/12/29
 */
public class LogUtils {

    private static LoggerContext PLUGIN_LOGGER_CONTEXT = new LoggerContext();

    public static LoggerContext getPluginLoggerContext() {
        return PLUGIN_LOGGER_CONTEXT;
    }


    public static Logger getLogger(final Class<?> clazz) {
        return PLUGIN_LOGGER_CONTEXT.getLogger(clazz);
    }

    public static Logger getLogger(final String name) {
        return PLUGIN_LOGGER_CONTEXT.getLogger(name);
    }
}
