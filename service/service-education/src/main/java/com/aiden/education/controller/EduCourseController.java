package com.aiden.education.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.query.CourseQuery;
import com.aiden.education.query.TeacherQuery;
import com.aiden.education.query.vo.CourseInfoVO;
import com.aiden.education.query.vo.PublishCourseInfo;
import com.aiden.education.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @ApiOperation(value = "查询最终要发布的课程信息")
    @GetMapping("/publishCourseInfo/{courseId}")
    public CommonResult publishCourseInfo(@PathVariable("courseId") String courseId) {
        PublishCourseInfo publishCourseInfo = eduCourseService.publishCourseInfo(courseId);
        return CommonResult.success().data("publishCourseInfo", publishCourseInfo);
    }

    @ApiOperation(value = "发布课程信息")
    @PostMapping("/finalPublish/{courseId}")
    public CommonResult finalPublish(@PathVariable("courseId") String courseId) {
        // 添加一个课程信息后返回该课程的课程id
        eduCourseService.finalPublish(courseId);
        return CommonResult.success();
    }

    // 分页查询课程，查询到的数据都封装Page中
    @ApiOperation(value = "按照条件分页查询课程")
    @PostMapping("/pageCondition/{current}/{limit}")
    public CommonResult pageCondition(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody CourseQuery courseQuery) {
        Map<String, Object> resultMap = eduCourseService.pageTeacher(current, limit, courseQuery);
        return CommonResult.success().data(resultMap);
    }

    @ApiOperation(value = "按照课程id删除课程")
    @DeleteMapping("/deleteCourseById/{courseId}")
    public CommonResult deleteCourseById(@PathVariable("courseId") String courseId) {
        eduCourseService.deleteCourseById(courseId);
        return CommonResult.success();
    }
}

