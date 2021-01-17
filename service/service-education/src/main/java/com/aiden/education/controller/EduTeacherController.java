package com.aiden.education.controller;


import com.aiden.commenUtils.CommonResult;
import com.aiden.education.entity.EduTeacher;
import com.aiden.education.service.impl.EduTeacherServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

@Api(description = "讲师管理")
@RestController
@RequestMapping("/education/edu-teacher")
public class EduTeacherController {
    @Autowired
    EduTeacherServiceImpl eduTeacherService;

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
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("/page/{current}/{limit}")
    public CommonResult findByPagination(@PathVariable("current") long current, @PathVariable("limit") long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        eduTeacherService.page(page, null);

        // 当前页数据
        List<EduTeacher> teacherList = page.getRecords();

        // 总的记录数
        long total = page.getTotal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("rows", teacherList);
        return CommonResult.success().data(resultMap);
    }
}

