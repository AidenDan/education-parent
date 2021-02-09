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

    /**
     * 添加章信息
     *
     * @param eduChapter eduChapter封装章信息
     * @return 是否修改成功
     */
    boolean addChapterInfo(EduChapter eduChapter);

    /**
     * 修改章信息
     *
     * @param eduChapter 封装章信息
     * @return 是否修改成功
     */
    boolean updateChapterInfo(EduChapter eduChapter);

    /**
     * 删除课程章信息
     *
     * @param id 章id
     * @return 是否删除成功
     */
    boolean deleteChapterInfoById(String id);

    /**
     * 查询课程章信息
     *
     * @param id 章id
     * @return 章信息
     */
    EduChapter getChapterInfo(String id);
}
