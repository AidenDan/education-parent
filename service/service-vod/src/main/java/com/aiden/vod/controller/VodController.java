package com.aiden.vod.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-11 15:38:53
 */

@Slf4j
@Api(description = "视屏上传接口")
@RestController
@CrossOrigin
@RequestMapping("/eduvod")
public class VodController {
    @Autowired
    VodService videoService;

    @ApiOperation(value = "上传视屏")
    @PostMapping("/uploadVideo")
    public CommonResult uploadVideo(MultipartFile file) {
        String videoId = videoService.uploadVideo(file);
        return CommonResult.success().data("videoId", videoId);
    }

    @ApiOperation(value = "删除视频")
    @DeleteMapping("/deleteVideoByVideoId/{videoId}")
    public CommonResult deleteVideoByVideoId(@PathVariable("videoId") String videoId) {
        videoService.deleteVideoByVideoId(videoId);
        return CommonResult.success();
    }

    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("/deleteVideoByBatchVideoId")
    public CommonResult deleteVideoByBatchVideoId(@RequestBody List<String> videoIdList) {
        videoService.deleteVideoByBatchVideoId(videoIdList);
        log.info("删除成功:::{}", videoIdList);
        return CommonResult.success();
    }
}
