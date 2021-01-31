package com.aiden.education.service.impl;

import com.aiden.commonBase.exceptionHandler.EduException;
import com.aiden.education.entity.EduSubject;
import com.aiden.education.entity.excel.SubjectData;
import com.aiden.education.entity.subject.LevelOneSubject;
import com.aiden.education.entity.subject.LevelTwoSubject;
import com.aiden.education.listener.SubjectExcelListener;
import com.aiden.education.mapper.EduSubjectMapper;
import com.aiden.education.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void uploadSubject(MultipartFile file) {
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(this)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(20001, "添加课程分类失败");
        }
    }

    @Override
    public List<LevelOneSubject> getTotalSubject() {
        List<LevelOneSubject> finalList = new ArrayList<>();
        // 获取所有的课程列表
        List<EduSubject> totalList = this.list(null);
        // 区分一级二级课程
        // 一级课程列表
        totalList.stream().forEach(subject -> {
            if ("0".equals(subject.getParentId())) {
                LevelOneSubject levelOneSubject = new LevelOneSubject();
                BeanUtils.copyProperties(subject, levelOneSubject);
                finalList.add(levelOneSubject);
            }
        });
        // 根据二级列表的parent_id与一级列表的id相同来获取二级列表
        for (LevelOneSubject levelOneSubject : finalList) {

            List<LevelTwoSubject> tempList = new ArrayList<>();
            for (EduSubject eduSubject : totalList) {
                if (levelOneSubject.getId().equals(eduSubject.getParentId())) {
                    LevelTwoSubject levelTwoSubject = new LevelTwoSubject();
                    BeanUtils.copyProperties(eduSubject, levelTwoSubject);
                    tempList.add(levelTwoSubject);
                }
            }
            levelOneSubject.setChildren(tempList);
        }

        return finalList;
    }
}
