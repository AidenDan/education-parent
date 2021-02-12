package com.aiden.vod.service.impl;

//import com.aiden.commonBase.exceptionHandler.EduException;

import com.aiden.vod.service.VodService;
import com.aiden.vod.utils.AliVideoClientUtils;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-11 15:54:44
 */

@Slf4j
@Service
public class VideoServiceImpl implements VodService {
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            // 上传到阿里云中显示的名称
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            // 创建请求对象
            UploadStreamRequest request = new UploadStreamRequest(keyId, keySecret, title, originalFilename, inputStream);
            // 创建请求对象
            UploadVideoImpl uploader = new UploadVideoImpl();
            // 上传后会有一个响应
            UploadStreamResponse response = uploader.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId)) {
                    throw new RuntimeException(errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new RuntimeException("视频服务上传失败");
        }
    }

    //    @Transactional(propagation = Propagation.REQUIRED)
//    @LcnTransaction
    @Override
    public void deleteVideoByVideoId(String videoId) {
        // 获取客户端
        DefaultAcsClient client = AliVideoClientUtils.initVodClient(keyId, keySecret);
        // 创建删除对象
        DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
        // 设置视频id，根据id进行删除
        deleteVideoRequest.setVideoIds(videoId);
        // 客户端进行删除
        try {
            client.getAcsResponse(deleteVideoRequest);
        } catch (ClientException e) {
            throw new RuntimeException("删除视频失败!");
        }
    }
}
