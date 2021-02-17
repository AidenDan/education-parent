package com.aiden.education.service.impl;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.query.vo.*;
import com.aiden.exceptionHandler.EduException;
import com.aiden.education.entity.*;
import com.aiden.education.feignClient.VodClient;
import com.aiden.education.mapper.EduCourseMapper;
import com.aiden.education.mapper.EduVideoMapper;
import com.aiden.education.query.CourseQuery;
import com.aiden.education.service.EduChapterService;
import com.aiden.education.service.EduCourseDescriptionService;
import com.aiden.education.service.EduCourseService;
import com.aiden.education.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    EduCourseMapper eduCourseMapper;
    @Autowired
    EduVideoMapper eduVideoMapper;
    @Autowired
    VodClient vodClient;

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
        // 使缓存的热门课程失效
        redisTemplate.delete("hotCourse");
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

        // 更新数据库
        // 更新 EduCourse
        this.updateById(eduCourse);
        // 更新课程描述
        eduCourseDescriptionService.updateById(eduCourseDescription);

        // 使缓存的热门课程失效
        redisTemplate.delete("hotCourse");
        return courseInfoVO.getId();
    }

    @Override
    public PublishCourseInfo publishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void finalPublish(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        this.updateById(eduCourse);
    }

    @Override
    public Map<String, Object> pageTeacher(long current, long limit, CourseQuery courseQuery) {
        List<Object> list = baseMapper.getCourseListPage(current, limit, courseQuery);
        // 当前结果集
        List<CourseInfoDO> courseList = (List<CourseInfoDO>) list.get(0);
        // 总数据量
        Integer total = ((List<Integer>) list.get(1)).get(0);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("rows", courseList);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteCourseById(String courseId) {
        // 先根据课程id查询出该课程下的所有小节videoId
        List<String> videoIdList = eduVideoMapper.getVideoSourceIdListByCourseId(courseId);
        // 调用微服务删除视频
        if (!videoIdList.isEmpty()) {
            CommonResult commonResult = vodClient.deleteVideoByBatchVideoId(videoIdList);
            if (20001 == commonResult.getCode()) {
                throw new EduException(20001, "调用批量删除小节视频的微服务失败!");
            }
        }

        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        eduVideoService.remove(videoWrapper);

        eduCourseDescriptionService.removeById(courseId);

        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        eduChapterService.remove(chapterWrapper);

        this.removeById(courseId);
        // 使缓存的热门课程失效
        redisTemplate.delete("hotCourse");
    }

    /**
     * redis中不做复杂查询,查询去db
     * 查询热门课程
     * 待做接口访问次数统计
     * 缓存的是根据观看次数多少排序的 每次访问该课程都要刷新缓存
     *
     * @return 课程数据
     */
    @Override
    public List<EduCourse> getHotCourse() {
        List<EduCourse> hotCourse = (List<EduCourse>) redisTemplate.opsForValue().get("hotCourse");
        if (hotCourse != null && !hotCourse.isEmpty()) {
            return hotCourse;
        } else {
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("view_count");
            wrapper.last("limit 8");
            List<EduCourse> courseList = baseMapper.selectList(wrapper);
            redisTemplate.opsForValue().set("hotCourse", courseList);
            return courseList;
        }
    }

    /**
     * 前台分页查询课程
     *
     * @param current          当前页
     * @param limit            页大小
     * @param courseQueryFront 封装查询参数
     * @return 结果
     */
    @Override
    public Map<String, Object> pageTeacherFront(long current, long limit, CourseQueryFront courseQueryFront) {
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryFront.getTeacherId())) {
            wrapper.eq("teacher_id", courseQueryFront.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseQueryFront.getTitle())) {
            wrapper.eq("title", courseQueryFront.getTitle());
        }
        if (!StringUtils.isEmpty(courseQueryFront.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseQueryFront.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryFront.getSubjectId())) {
            wrapper.eq("subject_id", courseQueryFront.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryFront.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count", courseQueryFront.getBuyCountSort());
        }
        if (!StringUtils.isEmpty(courseQueryFront.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create", courseQueryFront.getGmtCreateSort());
        }
        if (!StringUtils.isEmpty(courseQueryFront.getPriceSort())) {
            wrapper.orderByDesc("price", courseQueryFront.getPriceSort());
        }

        // 数据封装到page中
        baseMapper.selectPage(page, wrapper);
        // 当前页数据
        List<EduCourse> courseList = page.getRecords();

        // 总的记录数
        long total = page.getTotal();
        // 当前页
        long pageCurrent = page.getCurrent();
        // 总页数
        long pages = page.getPages();
        // 页大小
        long size = page.getSize();
        // 上一页
        boolean hasPrevious = page.hasPrevious();
        // 下一页
        boolean hasNext = page.hasNext();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("rows", courseList);
        resultMap.put("pageCurrent", pageCurrent);
        resultMap.put("pages", pages);
        resultMap.put("size", size);
        resultMap.put("hasPrevious", hasPrevious);
        resultMap.put("hasNext", hasNext);
        return resultMap;
    }

    /**
     * 根据课程id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    @Override
    public CourseInfoVO getCourseInfoByIdFront(String courseId) {
        CourseInfoVoFront courseInfoVoFront = new CourseInfoVoFront();
        // 查询mysql数据库
        EduCourse course = this.getById(courseId);
        BeanUtils.copyProperties(course, courseInfoVoFront.getEduCourse());

        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(description, courseInfoVoFront.getEduCourseDescription());

//        eduChapterService.list()


        return null;
    }
}
