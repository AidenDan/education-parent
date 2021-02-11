package com.aiden.vod.service;

import org.springframework.web.multipart.MultipartFile;

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
}
