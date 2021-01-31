package com.aiden.education.entity.subject;

import lombok.Data;

import java.util.List;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-31 21:17:00
 */

@Data
public class LevelOneSubject {
    private String id;
    private String title;
    // 一级课程中包含了二级课程
    private List<LevelTwoSubject> children;
}
