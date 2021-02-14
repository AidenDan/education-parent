package com.aiden.cms;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-14 14:02:32
 */

@EnableDistributedTransaction
@EnableDiscoveryClient
@SpringBootApplication
public class BannerMain {
    public static void main(String[] args) {
        SpringApplication.run(BannerMain.class, args);
    }
}
