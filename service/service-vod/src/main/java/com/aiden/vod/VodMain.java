package com.aiden.vod;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-11 13:59:12
 */

@EnableDistributedTransaction // 开启分布式事务
@EnableDiscoveryClient
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.aiden"})
public class VodMain {
    public static void main(String[] args) {
        SpringApplication.run(VodMain.class, args);
    }
}
