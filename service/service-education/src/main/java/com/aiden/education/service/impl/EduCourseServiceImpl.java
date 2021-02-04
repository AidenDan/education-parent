package com.aiden.education.service.impl;

import com.aiden.commonBase.exceptionHandler.EduException;
import com.aiden.education.entity.EduCourse;
import com.aiden.education.entity.EduCourseDescription;
import com.aiden.education.mapper.EduCourseMapper;
import com.aiden.education.query.vo.CourseInfoVO;
import com.aiden.education.service.EduCourseDescriptionService;
import com.aiden.education.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addCourseInfo(CourseInfoVO courseInfoVO) {
        // 添加课程信息到课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        boolean save = this.save(eduCourse);
        if (!save) {
            throw new EduException(20001, "添加课程信息失败");
        }
        // 添加描述信息到课程描述表
        // 获取课程Id
        String courseId = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        boolean saveInfo = eduCourseDescriptionService.save(eduCourseDescription);
        if (!saveInfo) {
            throw new EduException(20001, "添加课程描述信息失败");
        }
        return courseId;
    }
}
