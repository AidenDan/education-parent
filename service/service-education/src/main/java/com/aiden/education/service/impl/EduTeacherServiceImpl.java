package com.aiden.education.service.impl;

import com.aiden.education.entity.EduTeacher;
import com.aiden.education.mapper.EduTeacherMapper;
import com.aiden.education.query.TeacherQuery;
import com.aiden.education.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public Map<String, Object> pageTeacher(long current, long limit, TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name", teacherQuery.getName());
        }
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
        this.page(page, wrapper);
        // 当前页数据
        List<EduTeacher> teacherList = page.getRecords();

        // 总的记录数
        long total = page.getTotal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("rows", teacherList);
        return resultMap;
    }
}
