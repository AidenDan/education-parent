package com.aiden.user.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-15 13:16:59
 */

@Configuration
@MapperScan({"com.aiden.user.mapper"})
public class UserConfig {
    /**
     * 逻辑删除插件
     * 高版本不用手动配置这个逻辑删除插件了
     */
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

    /**
     * mp分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
