package com.aiden.education.mapper;

import com.aiden.education.entity.EduCourse;
import com.aiden.education.query.vo.PublishCourseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 根据课程id查询显示最终要发布显示的信息
     *
     * @param courseId 课程id
     * @return PublishCourseInfo
     */
    PublishCourseInfo getPublishCourseInfo(@Param("courseId") String courseId);
}
