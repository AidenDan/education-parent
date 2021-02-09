package com.aiden.education.service;

import com.aiden.education.entity.EduCourse;
import com.aiden.education.query.vo.CourseInfoVO;
import com.aiden.education.query.vo.PublishCourseInfo;
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

    /**
     * 根据课程id获取课程信息 该方法主要用于上一步中回显数据进行修改操作
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    CourseInfoVO getCourseInfoById(String courseId);

    /**
     * 更新课程信息
     *
     * @param courseInfoVO 封装添加的课程信息
     */
    String updateCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 最终要发布的课程信息
     *
     * @param courseId 课程id
     * @return PublishCourseInfo
     */
    PublishCourseInfo publishCourseInfo(String courseId);

    /**
     * 最终发布该课程
     *
     * @param courseId 课程id
     */
    void finalPublish(String courseId);
}
