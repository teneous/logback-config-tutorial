package com.syoka.tutorial.logback.plugin.b.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.syoka.tutorial.logback.plugin.b.BInterceptor;

/**
 * @author syoka
 */
@Configuration
public class LogbackPluginBAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public BInterceptor pluginBInterceptor() {
        return new BInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pluginBInterceptor());
    }
}
