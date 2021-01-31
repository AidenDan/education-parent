package com.aiden.education.controller;


import com.aiden.commenUtils.CommonResult;
import com.aiden.education.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/uploadSubject")
    public CommonResult uploadSubject(MultipartFile file) {
        eduSubjectService.uploadSubject(file);
        return CommonResult.success();
    }
}

