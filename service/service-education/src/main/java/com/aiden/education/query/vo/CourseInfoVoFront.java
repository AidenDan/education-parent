package com.aiden.education.query.vo;

import com.aiden.education.entity.EduCourse;
import com.aiden.education.entity.EduCourseDescription;
import com.aiden.education.entity.EduSubject;
import com.aiden.education.entity.EduTeacher;
import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-17 12:02:24
 */

@Api(description = "封装前端课程详情中显示的信息")
@Data
public class CourseInfoVoFront implements Serializable {
    private EduCourse eduCourse;
    private EduTeacher eduTeacher;
    private EduSubject eduSubject;
    private EduCourseDescription eduCourseDescription;
}
