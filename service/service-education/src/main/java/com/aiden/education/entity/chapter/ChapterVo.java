package com.aiden.education.entity.chapter;

import lombok.Data;

import java.util.List;

/**
 * @author Aiden
 * @version 1.0
 * @description 章节信息 一个章节中含有其对应的多个小结
 * @date 2021-2-6 16:02:29
 */

@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children;
}
