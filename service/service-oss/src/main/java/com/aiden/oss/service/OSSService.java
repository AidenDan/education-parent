package com.aiden.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-30 12:58:17
 */
public interface OSSService {
    String upLoadTeacherAvatar(MultipartFile file);
}
