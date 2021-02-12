package com.aiden.education.service;

import com.aiden.education.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
public interface EduVideoService extends IService<EduVideo> {
    /**
     * 添加课程小节信息
     *
     * @param eduVideo 封装小节信息
     */
    void addXChapterInfo(EduVideo eduVideo);

    /**
     * 修改课程小节信息
     *
     * @param eduVideo 封装小节信息
     */
    void updateXChapterInfo(EduVideo eduVideo);

    /**
     * 查询课程小节信息
     *
     * @param id 小节id
     */
    EduVideo getXChapterInfo(String id);

    /**
     * 删除小节信息
     *
     * @param id 小节id
     */
    void deleteXChapterInfo(String id);
}
