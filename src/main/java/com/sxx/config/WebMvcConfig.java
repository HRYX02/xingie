package com.sxx.config;

import com.sxx.common.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author SxxStar
 * @description SpringMVC配置类
 * @Configuration 配置类注解
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * @author SxxStar
     * @description 路径映射
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * @author SxxStar
     * @description 扩展消息转换器处理JSON
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 消息转换器处理放在第一位
        converters.add(0,messageConverter);
        super.extendMessageConverters(converters);
    }
}
