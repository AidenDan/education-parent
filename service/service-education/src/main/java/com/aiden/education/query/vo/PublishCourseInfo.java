package com.aiden.education.query.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-9 19:43:06
 */

@Data
@ApiModel(description = "封装最终要发布的课程信息")
public class PublishCourseInfo {
    private static final long serialVersionUID = 1L;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price; //只用于显示
}
