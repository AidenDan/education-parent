package com.aiden.education.service;

import com.aiden.education.entity.EduTeacher;
import com.aiden.education.query.TeacherQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 根据条件分页查询讲师
     *
     * @param current      current
     * @param limit        limit
     * @param teacherQuery teacherQuery
     * @return List<EduTeacher>
     */
    Map<String, Object> pageTeacher(long current, long limit, TeacherQuery teacherQuery);

    /**
     * 查询热门讲师
     *
     * @return 讲师数据
     */
    List<EduTeacher> getHotTeacher();
}
