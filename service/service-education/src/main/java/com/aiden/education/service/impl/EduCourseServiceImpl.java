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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

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
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

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

        // 将该课程信息添加入缓存中
        try {
            redisTemplate.opsForHash().put(courseId, "eduCourse", eduCourse);
            redisTemplate.opsForHash().put(courseId, "eduCourseDescription", eduCourseDescription);
            redisTemplate.expire(courseId, 60, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new EduException(20001, "添加课程信息失败");
        }
        return courseId;
    }

    @Override
    public CourseInfoVO getCourseInfoById(String courseId) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        // 先查询缓存，若能命中缓存，就在缓存中区
        EduCourse eduCourse = (EduCourse) redisTemplate.opsForHash().get(courseId, "eduCourse");
        EduCourseDescription eduCourseDescription = (EduCourseDescription) redisTemplate.opsForHash().get(courseId, "eduCourseDescription");
        if (eduCourse != null
                && eduCourseDescription != null
                && courseId.equals(eduCourse.getId())
                && eduCourse.getId().equals(eduCourseDescription.getId())) {
            BeanUtils.copyProperties(eduCourse, courseInfoVO);
            courseInfoVO.setDescription(eduCourseDescription.getDescription());
        } else {
            // 查询mysql数据库
            EduCourse course = this.getById(courseId);
            EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
            BeanUtils.copyProperties(course, courseInfoVO);
            courseInfoVO.setDescription(description.getDescription());
        }
        return courseInfoVO;
    }

    @Override
    public String updateCourseInfo(CourseInfoVO courseInfoVO) {
        // 先更新缓存
        // 更新EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);

        // 更新EduCourseDescription
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVO.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        redisTemplate.opsForHash().put(courseInfoVO.getId(), "eduCourse", eduCourse);
        redisTemplate.opsForHash().put(courseInfoVO.getId(), "eduCourseDescription", eduCourseDescription);
        return courseInfoVO.getId();
    }
}
