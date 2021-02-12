package com.aiden.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-30 11:53:02
 */

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 排除数据源的自动配置，否则报错
@ComponentScan(basePackages = {"com.aiden"}) // 扫描所有模块、子模块的com.aiden包下的类
public class OSSMain {
    public static void main(String[] args) {
        SpringApplication.run(OSSMain.class, args);
    }
}
