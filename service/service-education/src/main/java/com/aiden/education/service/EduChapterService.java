package com.aiden.education.service;

import com.aiden.education.entity.EduChapter;
import com.aiden.education.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduChapterService extends IService<EduChapter> {
    /**
     * 根据课程id获取该课程的章节信息
     *
     * @param courseId courseId
     * @return List
     */
    List<ChapterVo> getChapterInfoByCourseId(String courseId);
}
