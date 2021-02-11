package com.aiden.education.mapper;

import com.aiden.education.entity.EduCourse;
import com.aiden.education.query.CourseQuery;
import com.aiden.education.query.vo.CourseInfoDO;
import com.aiden.education.query.vo.PublishCourseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 动态拼接sql，同时分页查询和查询出所有记录行数
     *
     * @param current     当前页
     * @param limit       页大小
     * @param courseQuery 封装动态查询的参数
     * @return List<Object> 封装查询出的结果集
     */
    List<Object> getCourseListPage(@Param("current") long current, @Param("limit") long limit, @Param("courseQuery") CourseQuery courseQuery);
}
