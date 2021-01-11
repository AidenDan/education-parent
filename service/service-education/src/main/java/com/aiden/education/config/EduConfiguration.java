package com.aiden.education.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-11 20:13:51
 */

@Configuration
@MapperScan({"com.aiden.education.mapper"})
public class EduConfiguration {
}
