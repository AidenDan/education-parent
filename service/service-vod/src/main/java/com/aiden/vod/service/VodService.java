package com.aiden.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-11 15:53:34
 */
public interface VodService {

    /**
     * 上传视屏
     *
     * @param file 视屏文件
     * @return 阿里云中视屏id
     */
    String uploadVideo(MultipartFile file);

    /**
     * 根据videoId删除视屏
     *
     * @param videoId 视频id
     */
    void deleteVideoByVideoId(String videoId);

    /**
     * 删除课程时删除该课程下面的小节中所有视频
     *
     * @param videoIdList 视频列表
     */
    void deleteVideoByBatchVideoId(List<String> videoIdList);
}
