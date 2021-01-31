package com.aiden.education.service.impl;

import com.aiden.commonBase.exceptionHandler.EduException;
import com.aiden.education.entity.EduSubject;
import com.aiden.education.entity.excel.SubjectData;
import com.aiden.education.listener.SubjectExcelListener;
import com.aiden.education.mapper.EduSubjectMapper;
import com.aiden.education.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
}
