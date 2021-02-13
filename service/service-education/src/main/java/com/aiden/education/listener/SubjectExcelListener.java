package com.aiden.education.listener;

import com.aiden.exceptionHandler.EduException;
import com.aiden.education.entity.EduSubject;
import com.aiden.education.entity.excel.SubjectData;
import com.aiden.education.service.EduSubjectService;
import com.aiden.exceptionHandler.EduException;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-31 11:45:28
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 读取excel中的类容，一行一行的读并加到数据库中
     *
     * @param subjectData     封装好了的每行数据的对象
     * @param analysisContext analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        // 如果数据为null,报错
        if (subjectData == null) {
            throw new EduException(20001, "文件中的数据为空");
        }
        // 数据库中不存在当前一级课程时才添加到数据库
        EduSubject oneSubject = existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (oneSubject == null) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }
        // 数据库中不存在当前二级课程时才添加到数据库
        EduSubject towSubject = existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), oneSubject.getId());
        if (towSubject == null) {
            towSubject = new EduSubject();
            towSubject.setParentId(oneSubject.getId());
            towSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(towSubject);
        }
    }

    /**
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     * 判断当前一级课程名称是否存在,如果已经存在则不应该继续加入到数据库中
     * parent_id=0表示为一级课程目录
     *
     * @param eduSubjectService eduSubjectService
     * @param name              name 当前课程名称
     * @return SubjectData
     */
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }

    /**
     * 判断当前二级课程名称是否存在,如果已经存在则不应该继续加入到数据库中
     * 二级课程的parent_id应该和一级课程的id相同
     *
     * @param eduSubjectService eduSubjectService
     * @param name              name 当前课程名称
     * @return SubjectData
     */
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", parentId);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }
}
