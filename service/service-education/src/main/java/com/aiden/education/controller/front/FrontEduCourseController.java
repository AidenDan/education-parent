package com.aiden.education.controller.front;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.query.CourseQuery;
import com.aiden.education.query.vo.CourseInfoVO;
import com.aiden.education.query.vo.CourseQueryFront;
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
@RequestMapping("/education/frontcourse")
@CrossOrigin
public class FrontEduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    @ApiOperation(value = "根据课程id获取课程信息")
    @GetMapping("/getCourseInfoById/{courseId}")
    public CommonResult getCourseInfoById(@PathVariable("courseId") String courseId) {
        // 添加一个课程信息后返回该课程的课程id
        CourseInfoVO courseInfoVO = eduCourseService.getCourseInfoByIdFront(courseId);
        return CommonResult.success().data("courseInfo", courseInfoVO);
    }

    // 分页查询课程，查询到的数据都封装Page中
    @ApiOperation(value = "前台按照条件分页查询课程")
    @PostMapping("/pageCondition/{current}/{limit}")
    public CommonResult pageCondition(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody CourseQueryFront courseQueryFront) {
        Map<String, Object> resultMap = eduCourseService.pageTeacherFront(current, limit, courseQueryFront);
        return CommonResult.success().data(resultMap);
    }
}

