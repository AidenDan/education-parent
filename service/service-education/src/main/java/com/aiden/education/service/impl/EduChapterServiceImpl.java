package com.aiden.education.service.impl;

import com.aiden.exceptionHandler.EduException;
import com.aiden.education.entity.EduChapter;
import com.aiden.education.entity.EduVideo;
import com.aiden.education.entity.chapter.ChapterVo;
import com.aiden.education.entity.chapter.VideoVo;
import com.aiden.education.mapper.EduChapterMapper;
import com.aiden.education.mapper.EduVideoMapper;
import com.aiden.education.service.EduChapterService;
import com.aiden.education.service.EduVideoService;
import com.aiden.exceptionHandler.EduException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    EduVideoMapper eduVideoMapper;

    @Override
    public List<ChapterVo> getChapterInfoByCourseId(String courseId) {
        // 查询该课程的章信息接
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = this.list(chapterQueryWrapper);
        // 查询该课程的节信息
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = eduVideoService.list(videoQueryWrapper);

        // 封装章数据集
        List<ChapterVo> finalList = new ArrayList<>();
        chapterList.forEach(chapter -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            finalList.add(chapterVo);
        });
        // 封装节数据集
        finalList.forEach(chapterVo -> {
            List<VideoVo> tempVideo = new ArrayList<>();
            videoList.forEach(video -> {
                if (chapterVo.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    tempVideo.add(videoVo);
                }
            });
            chapterVo.setChildren(tempVideo);
        });
        return finalList;
    }

    @Override
    public boolean addChapterInfo(EduChapter eduChapter) {
        return this.save(eduChapter);
    }

    @Override
    public boolean updateChapterInfo(EduChapter eduChapter) {
        return this.updateById(eduChapter);
    }

    @Override
    public boolean deleteChapterInfoById(String id) {
        // 如果这个章下面有节信息就不能删除该章
        int existVideo = eduVideoMapper.isExistVideo(id);
        if (existVideo > 0) {
            throw new EduException(20001, "有小节信息该章不能删除!");
        } else {
            return this.removeById(id);
        }
    }

    @Override
    public EduChapter getChapterInfo(String id) {
        return this.getById(id);
    }
}
