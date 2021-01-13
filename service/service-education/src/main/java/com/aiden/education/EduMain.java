package com.aiden.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-11 20:16:32
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.aiden"}) // 扫描所有模块、子模块的com.aiden包下的类
public class EduMain {
    public static void main(String[] args) {
        SpringApplication.run(EduMain.class, args);
    }
}
