package com.aiden.education.feignClient.hystrix;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.feignClient.VodClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Aiden
 * @version 1.0
 * @description 微服务调用失败的处理机制
 * @date 2021-2-12 13:15:07
 */

@Component
public class VodClientHystrix implements VodClient {
    @Override
    public CommonResult deleteVideoByVideoId(String videoId) {
        return CommonResult.fail().message("删除阿里云视频的微服务调用失败");
    }
}
