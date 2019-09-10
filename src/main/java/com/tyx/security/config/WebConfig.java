package com.tyx.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create By C  2019-09-09 21:17
 */
@Component
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MenuFilter menuFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(menuFilter).addPathPatterns("/**").excludePathPatterns("/plugins/**");
    }
}
