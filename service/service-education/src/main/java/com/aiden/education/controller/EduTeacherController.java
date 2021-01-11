package com.aiden.education.controller;


import com.aiden.education.entity.EduTeacher;
import com.aiden.education.service.impl.EduTeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
@RestController
@RequestMapping("/education/edu-teacher")
public class EduTeacherController {
    @Autowired
    EduTeacherServiceImpl eduTeacherService;

    @GetMapping("/findAll")
    public List<EduTeacher> findAllTeacher() {
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return teacherList;
    }
}

