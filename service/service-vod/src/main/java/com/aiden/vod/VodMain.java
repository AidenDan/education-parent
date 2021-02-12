package com.aiden.vod;

import org.springframework.boot.SpringApplication;
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

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.aiden"})
public class VodMain {
    public static void main(String[] args) {
        SpringApplication.run(VodMain.class, args);
    }
}
