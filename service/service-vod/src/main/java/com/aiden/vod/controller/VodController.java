package com.aiden.vod.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-11 15:38:53
 */

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
}
