package com.aiden.education.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.entity.EduChapter;
import com.aiden.education.entity.EduVideo;
import com.aiden.education.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * 小节信息管理
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */

@Api(description = "课程小节信息管理接口")
@RestController
@RequestMapping("/education/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    EduVideoService eduVideoService;

    @ApiOperation(value = "添加课程小节信息")
    @PostMapping("/addXChapterInfo")
    public CommonResult addXChapterInfo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改课程小节信息")
    @PutMapping("/updateXChapterInfo")
    public CommonResult updateXChapterInfo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return CommonResult.success();
    }

    @ApiOperation(value = "查询课程小节信息")
    @GetMapping("/getXChapterInfo/{id}")
    public CommonResult getXChapterInfo(@PathVariable("id") String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return CommonResult.success().data("eduVideo", eduVideo);
    }

    @ApiOperation(value = "删除课程小节信息")
    @DeleteMapping("/deleteXChapterInfo/{id}")
    public CommonResult deleteXChapterInfo(@PathVariable("id") String id) {
        eduVideoService.removeById(id);
        return CommonResult.success();
    }
}

