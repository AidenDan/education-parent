package com.aiden.education.service.impl;

import com.aiden.education.entity.EduTeacher;
import com.aiden.education.mapper.EduTeacherMapper;
import com.aiden.education.query.TeacherQuery;
import com.aiden.education.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public IPage<EduTeacher> pageTeacher(long current, long limit, TeacherQuery teacherQuery) {
        IPage<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        wrapper.like(!StringUtils.isEmpty(teacherQuery.getName()), "name", teacherQuery.getName());

        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level", teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create", teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_modified", teacherQuery.getEnd());
        }
        // 按照创建时间倒序排列查询结果
        wrapper.orderByDesc("gmt_create");
        // 查询数据
        IPage<EduTeacher> iPage = this.page(page, wrapper);
        // 当前页数据
        List<EduTeacher> teacherList = page.getRecords();

        // 总的记录数
        long total = page.getTotal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("rows", teacherList);
        return iPage;
    }

    @Override
    public List<EduTeacher> getHotTeacher() {
        List<EduTeacher> hotTeacher = (List<EduTeacher>) redisTemplate.opsForValue().get("hotTeacher");
        if (hotTeacher != null && !hotTeacher.isEmpty()) {
            return hotTeacher;
        } else {
            QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("gmt_create");
            wrapper.last("limit 4");
            List<EduTeacher> teacherList = baseMapper.selectList(wrapper);
            redisTemplate.opsForValue().set("hotTeacher", teacherList);
            return teacherList;
        }
    }
}
