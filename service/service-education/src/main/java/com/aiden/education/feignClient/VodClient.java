package com.aiden.education.feignClient;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.feignClient.hystrix.VodClientHystrix;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Aiden
 * @version 1.0
 * @description 调用阿里云视频微服务
 * @date 2021-2-12 13:05:48
 */

@Component
@FeignClient(name = "service-vod", fallback = VodClientHystrix.class)
public interface VodClient {

    @ApiOperation(value = "删除视频")
    @DeleteMapping("/eduvod/deleteVideoByVideoId/{videoId}")
    CommonResult deleteVideoByVideoId(@PathVariable("videoId") String videoId);

    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("/eduvod/deleteVideoByBatchVideoId")
    CommonResult deleteVideoByBatchVideoId(@RequestBody List<String> videoIdList);
}
