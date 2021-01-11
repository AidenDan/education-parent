package com.aiden.education.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-11 21:19:59
 */

@Configuration
public class EduFilterConfigurer {

    /**
     * 自定义过滤器
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean customFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        EduFilter eduFilter = new EduFilter();
        //注入过滤器
        registration.setFilter(eduFilter);
        //拦截规则
        registration.addUrlPatterns("/*");
        //过滤器名称
        registration.setName("eduFilter");
        //过滤器顺序
        registration.setOrder(1);
        return registration;
    }
}
