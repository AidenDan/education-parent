package com.aiden.message.service.impl;

import com.aiden.commenUtils.CommonResult;
import com.aiden.commenUtils.RandomUtil;
import com.aiden.message.service.MessageService;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-15 12:05:48
 */

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 发送短信方法
     *
     * @param phoneNumber 电话号码
     * @return 结果
     */

    @Override
    public CommonResult sendMessage(String phoneNumber) {
        // 先从缓存中取验证码
        String code = (String) redisTemplate.opsForValue().get(phoneNumber);
        if (!StringUtils.isEmpty(code)) {
            return CommonResult.success().message("发送验证码成功");
        }

        if (StringUtils.isEmpty(phoneNumber)) {
            return CommonResult.fail().message("发送验证码失败");
        }

        code = RandomUtil.getFourBitRandom();
        log.info("验证码为:::{}", code);
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        // 固定写法
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G5TRTYRyKNmAN2WZSAu", "5UAUYjviRpJlbeCwZUCOhRdPE5K4s0");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "我的蓝天教育网站");
        request.putQueryParameter("TemplateCode", "SMS_211497791");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();

            // 如果发送短信成功,把验证码存到redis中5分钟
            if (success) {
                redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);
            }
            return success ? CommonResult.success().message("发送验证码成功") : CommonResult.fail().message("发送验证码失败");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return CommonResult.fail().message("发送验证码失败");
    }
}
