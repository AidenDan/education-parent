package com.aiden.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-30 12:35:48
 */

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    public  static String oss_endPoint;
    public static String oss_keyId;
    public static String oss_keySecret;
    public static String oss_bucketName;

    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    @Override
    public void afterPropertiesSet() throws Exception {
        oss_endPoint = endPoint;
        oss_keyId = keyId;
        oss_keySecret = keySecret;
        oss_bucketName = bucketName;
    }
}
