package com.aiden.education.controller;


import com.aiden.commenUtils.CommonResult;
import com.aiden.exceptionHandler.EduException;
import com.aiden.education.entity.subject.LevelOneSubject;
import com.aiden.education.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */

@Api(description = "课程管理接口")
@RestController
@RequestMapping("/education/edu-subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;

    @ApiOperation(value = "通过xlsx上传课程列表")
    @PostMapping("/uploadSubject")
    public CommonResult uploadSubject(MultipartFile file) {
        if (file == null) {
            return CommonResult.fail();
        }
        eduSubjectService.uploadSubject(file);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取所有的课程列表")
    @GetMapping("/getTotalSubject")
    public CommonResult getTotalSubject() {
        List<LevelOneSubject> totalSubject = eduSubjectService.getTotalSubject();
        return CommonResult.success().data("totalSubject", totalSubject);
    }
}

