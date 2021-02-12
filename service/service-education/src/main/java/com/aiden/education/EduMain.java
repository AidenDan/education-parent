package com.aiden.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-11 20:16:32
 */

@EnableFeignClients  // 开启Feign客户端调用支持 微服务的调用方才加
@EnableDiscoveryClient  // 开启注册中心支持
@SpringBootApplication
@ComponentScan(basePackages = {"com.aiden"}) // 扫描所有模块、子模块的com.aiden包下的类
public class EduMain {
    public static void main(String[] args) {
        SpringApplication.run(EduMain.class, args);
    }
}
