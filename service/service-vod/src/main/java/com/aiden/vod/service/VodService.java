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
}
