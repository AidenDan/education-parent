package com.aiden.education.controller;


import com.aiden.commenUtils.CommonResult;
import com.aiden.commonBase.exceptionHandler.EduException;
import com.aiden.education.entity.subject.LevelOneSubject;
import com.aiden.education.service.EduSubjectService;
import io.swagger.annotations.Api;
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

    @PostMapping("/uploadSubject")
    public CommonResult uploadSubject(MultipartFile file) {
        if (file == null) {
            return CommonResult.fail();
        }
        eduSubjectService.uploadSubject(file);
        return CommonResult.success();
    }

    @GetMapping("/getTotalSubject")
    public CommonResult getTotalSubject() {
        List<LevelOneSubject> totalSubject = eduSubjectService.getTotalSubject();
        return CommonResult.success().data("totalSubject", totalSubject);
    }
}

