package com.aiden.message;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aiden
 * @version 1.0
 * @description 短信服务
 * @date 2021-2-15 11:19:44
 */

@EnableAutoConfiguration
@EnableDistributedTransaction
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.aiden"}) // 扫描所有模块、子模块的com.aiden包下的类
public class MessageMain {
    public static void main(String[] args) {
        SpringApplication.run(MessageMain.class, args);
    }
}
