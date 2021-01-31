package com.aiden.education.service;

import com.aiden.education.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduSubjectService extends IService<EduSubject> {

    void uploadSubject(MultipartFile file);
}
