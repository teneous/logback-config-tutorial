package com.syoka.tutorial.logback.plugin.a.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.syoka.tutorial.logback.plugin.a.AInterceptor;

/**
 * @author syoka
 */
@Configuration
public class LogbackPluginAAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public AInterceptor pluginAInterceptor() {
        return new AInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pluginAInterceptor());
    }
}
