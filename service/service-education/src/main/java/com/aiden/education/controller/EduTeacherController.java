package com.aiden.education.controller;


import com.aiden.education.entity.EduTeacher;
import com.aiden.education.service.impl.EduTeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<EduTeacher> findAllTeacher() {
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return teacherList;
    }

    // 逻辑删除讲师
    @ApiOperation(value = "根据id逻辑删除讲师")
    @DeleteMapping("/{id}")
    public boolean deleteTeacherLogic(@PathVariable String id) {
        boolean isReomve = eduTeacherService.removeById(id);
        return isReomve;
    }
}

