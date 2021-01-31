package com.aiden.education.service;

import com.aiden.education.entity.EduSubject;
import com.aiden.education.entity.subject.LevelOneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 上传xlsx文件
     *
     * @param file file
     */
    void uploadSubject(MultipartFile file);

    /**
     * 获取所有的课程列表
     *
     * @return List<LevelOneSubject>
     */
    List<LevelOneSubject> getTotalSubject();
}
