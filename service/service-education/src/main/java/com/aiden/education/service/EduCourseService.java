package com.aiden.education.service;

import com.aiden.education.entity.EduCourse;
import com.aiden.education.query.vo.CourseInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduCourseService extends IService<EduCourse> {
    /**
     * 添加课程信息
     *
     * @param courseInfoVO 封装添加的课程信息
     */
    String addCourseInfo(CourseInfoVO courseInfoVO);
}
