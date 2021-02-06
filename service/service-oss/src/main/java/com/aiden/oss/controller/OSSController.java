package com.aiden.oss.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.oss.service.OSSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-30 12:57:56
 */

@Api("oss服务")
@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OSSController {
    @Autowired
    OSSService ossService;

    @ApiOperation("上传图片")
    @PostMapping("/uploadAvatar")
    public CommonResult upLoadTeacherAvatar(MultipartFile file) {
        String url = ossService.upLoadTeacherAvatar(file);
        return CommonResult.success().data("url", url);
    }
}
