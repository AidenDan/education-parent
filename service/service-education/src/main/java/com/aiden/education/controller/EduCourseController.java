package com.aiden.education.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.query.vo.CourseInfoVO;
import com.aiden.education.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */

@Api(description = "课程信息管理接口")
@RestController
@RequestMapping("/education/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("/addCourseInfo")
    public CommonResult addCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        // 添加一个课程信息后返回该课程的课程id
        String courseId = eduCourseService.addCourseInfo(courseInfoVO);
        return CommonResult.success().data("courseId", courseId);
    }

    @ApiOperation(value = "根据课程id获取课程信息")
    @GetMapping("/getCourseInfoById/{courseId}")
    public CommonResult getCourseInfoById(@PathVariable("courseId") String courseId) {
        // 添加一个课程信息后返回该课程的课程id
        CourseInfoVO courseInfoVO = eduCourseService.getCourseInfoById(courseId);
        return CommonResult.success().data("courseInfo", courseInfoVO);
    }

    @ApiOperation(value = "更新课程信息")
    @PostMapping("/updateCourseInfo")
    public CommonResult updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        // 添加一个课程信息后返回该课程的课程id
        String courseId = eduCourseService.updateCourseInfo(courseInfoVO);
        return CommonResult.success().data("courseId", courseId);
    }
}

