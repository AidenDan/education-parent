package com.aiden.message.service;

import com.aiden.commenUtils.CommonResult;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-15 12:05:28
 */
public interface MessageService {

    /**
     * 发送短信方法
     *
     * @param phoneNumber 电话号码
     * @return 结果
     */
    CommonResult sendMessage(String phoneNumber);
}
