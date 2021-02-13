package com.aiden.education.mapper;

import com.aiden.education.entity.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduVideoMapper extends BaseMapper<EduVideo> {
    /**
     * 判断该章下是否存在节
     *
     * @param chapterId 章id
     * @return int
     */
    int isExistVideo(@Param("chapterId") String chapterId);

    /**
     * 根据课程id查询所有的视频id
     * @param courseId
     * @return
     */
    List<String> getVideoSourceIdListByCourseId(@Param("courseId") String courseId);
}
