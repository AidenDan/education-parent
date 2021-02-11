package com.aiden.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author Aiden
 * @version 1.0
 * @description 阿里云视屏点播的客户端工具类
 * @date 2021-2-11 19:31:24
 */
public class AliVideoClientUtils {

    /**
     * 获取阿里云客户端
     *
     * @param accessKeyId     用户的id
     * @param accessKeySecret 秘钥
     * @return 客户端
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) {
        String regionId = "cn-shanghai"; // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }
}
