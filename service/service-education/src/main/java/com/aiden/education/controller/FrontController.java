package com.aiden.education.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.entity.EduCourse;
import com.aiden.education.entity.EduTeacher;
import com.aiden.education.entity.EduVideo;
import com.aiden.education.service.EduCourseService;
import com.aiden.education.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-14 17:02:25
 */

@Api(description = "获取热门数据")
@RestController
@RequestMapping("/education/front")
@CrossOrigin
public class FrontController {
    @Autowired
    EduCourseService eduCourseService;
    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation(value = "查询热门课程8个")
    @GetMapping("/getHotCourse")
    public CommonResult getHotCourse() {
        List<EduCourse> hotCourse = eduCourseService.getHotCourse();
        return CommonResult.success().data("hotCourse", hotCourse);
    }

    @ApiOperation(value = "查询热门讲师4个")
    @GetMapping("/getHotTeacher")
    public CommonResult getHotTeacher() {
        List<EduTeacher> hotTeacher = eduTeacherService.getHotTeacher();
        return CommonResult.success().data("hotTeacher", hotTeacher);
    }
}
