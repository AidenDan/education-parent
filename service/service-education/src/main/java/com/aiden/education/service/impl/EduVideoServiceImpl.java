package com.aiden.education.service.impl;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.entity.EduVideo;
import com.aiden.education.feignClient.VodClient;
import com.aiden.education.mapper.EduVideoMapper;
import com.aiden.education.service.EduVideoService;
import com.aiden.exceptionHandler.EduException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    VodClient vodClient;

    @Override
    public void addXChapterInfo(EduVideo eduVideo) {
        this.save(eduVideo);
    }

    /**
     * 修改课程小节信息
     *
     * @param eduVideo 封装小节信息
     */
    @Override
    public void updateXChapterInfo(EduVideo eduVideo) {
        this.updateById(eduVideo);
    }

    /**
     * 查询课程小节信息
     *
     * @param id 小节id
     */
    @Override
    public EduVideo getXChapterInfo(String id) {
        return this.getById(id);
    }

    /**
     * 删除小节信息
     *
     * @param id 小节id
     */
//    @Transactional(propagation = Propagation.REQUIRED)
//    @LcnTransaction // 分布式事务支持
    @Override
    public void deleteXChapterInfo(String id) {
        // 删除小节里面对应的视频信息
        // 先根据小节id获取其中的videoId
        EduVideo eduVideo = this.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            CommonResult commonResult = vodClient.deleteVideoByVideoId(eduVideo.getVideoSourceId());
            if (20001 == commonResult.getCode()) {
                throw new EduException(20001, "调用删除小节视频的微服务失败!");
            }
        }
        this.removeById(id);
    }
}
