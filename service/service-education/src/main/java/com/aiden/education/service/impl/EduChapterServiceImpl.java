package com.aiden.education.service.impl;

import com.aiden.education.entity.EduChapter;
import com.aiden.education.entity.EduVideo;
import com.aiden.education.entity.chapter.ChapterVo;
import com.aiden.education.entity.chapter.VideoVo;
import com.aiden.education.mapper.EduChapterMapper;
import com.aiden.education.service.EduChapterService;
import com.aiden.education.service.EduVideoService;
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
        chapterList.stream().forEach(chapter->{
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            finalList.add(chapterVo);
        });
        // 封装节数据集
        finalList.stream().forEach(chapterVo -> {
            List<VideoVo> tempVideo = new ArrayList<>();
            videoList.forEach(video->{
                if (chapterVo.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    tempVideo.add(videoVo);
                }
            });
            chapterVo.setChildren(tempVideo);
        });
        return finalList;
    }
}
