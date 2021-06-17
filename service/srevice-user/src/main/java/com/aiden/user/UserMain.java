package com.aiden.user;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-15 13:12:21
 */

@EnableDistributedTransaction
@EnableDiscoveryClient  // 开启注册中心支持
@SpringBootApplication
@ComponentScan(basePackages = {"com.aiden"}) // 扫描所有模块、子模块的com.aiden包下的类
public class UserMain {
    public static void main(String[] args)       {
        SpringApplication.run(UserMain.class, args);
    }
}
