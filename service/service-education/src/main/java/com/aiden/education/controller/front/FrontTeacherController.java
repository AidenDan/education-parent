package com.aiden.education.controller.front;


import com.aiden.commenUtils.CommonResult;
import com.aiden.education.entity.EduCourse;
import com.aiden.education.entity.EduTeacher;
import com.aiden.education.query.TeacherQuery;
import com.aiden.education.service.EduCourseService;
import com.aiden.education.service.impl.EduTeacherServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */

@Api(description = "前台讲师显示接口")
@RestController
@RequestMapping("/education/frontteacher")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
    EduTeacherServiceImpl eduTeacherService;
    @Autowired
    EduCourseService eduCourseService;

    // 查询所有讲师
    @ApiOperation(value = "查询所有的讲师")
    @GetMapping("/findAll")
    public CommonResult findAllTeacher() {
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return CommonResult.success().data("itemList", teacherList);
    }

    // 逻辑删除讲师
    @ApiOperation(value = "根据id逻辑删除讲师")
    @DeleteMapping("/{id}")
    public CommonResult deleteTeacherLogic(@PathVariable("id") String id) {
        boolean isReomve = eduTeacherService.removeById(id);
        return isReomve ? CommonResult.success() : CommonResult.fail();
    }

    // 分页查询讲师 查询到的数据都封装Page中
    @ApiOperation(value = "前台分页查询讲师")
    @GetMapping("/page/{current}/{limit}")
    public CommonResult findByPagination(@PathVariable("current") long current, @PathVariable("limit") long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        eduTeacherService.page(page, null);

        // 当前页数据
        List<EduTeacher> teacherList = page.getRecords();

        // 总的记录数
        long total = page.getTotal();
        // 当前页
        long pageCurrent = page.getCurrent();
        // 总页数
        long pages = page.getPages();
        // 页大小
        long size = page.getSize();
        // 上一页
        boolean hasPrevious = page.hasPrevious();
        // 下一页
        boolean hasNext = page.hasNext();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("rows", teacherList);
        resultMap.put("pageCurrent", pageCurrent);
        resultMap.put("pages", pages);
        resultMap.put("size", size);
        resultMap.put("hasPrevious", hasPrevious);
        resultMap.put("hasNext", hasNext);
        return CommonResult.success().data(resultMap);
    }

    // 分页查询讲师 查询到的数据都封装Page中
    @ApiOperation(value = "按照条件分页查询讲师")
    @PostMapping("/pageCondition/{current}/{limit}")
    public CommonResult pageCondition(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody TeacherQuery teacherQuery) {
        Map<String, Object> resultMap = eduTeacherService.pageTeacher(current, limit, teacherQuery);
        return CommonResult.success().data(resultMap);
    }

    // 添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public CommonResult addTeacher(@RequestBody EduTeacher teacher) {
        boolean save = eduTeacherService.save(teacher);
        return save ? CommonResult.success() : CommonResult.fail();
    }

    // 根据id查询讲师
    @ApiOperation(value = "查询讲师信息")
    @GetMapping("/searchTeacher/{id}")
    public CommonResult searchTeacherById(@PathVariable("id") String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return CommonResult.success().data("item", teacher).data("courseList", courseList);
    }

    // 根据id更新讲师
    @ApiOperation(value = "更新讲师")
    @PutMapping("/updateTeacher/{id}")
    public CommonResult updateTeacherById(@PathVariable("id") String id, @RequestBody EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        boolean update = eduTeacherService.updateById(eduTeacher);
        return update ? CommonResult.success() : CommonResult.fail();
    }
}

