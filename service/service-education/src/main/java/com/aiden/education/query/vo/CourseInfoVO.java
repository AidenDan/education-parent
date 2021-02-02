package com.aiden.education.query.vo;

import com.aiden.education.entity.EduCourse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-2 21:41:27
 */

@ApiModel(value = "添加课程信息的封装类", description = "添加课程信息的封装类")
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseInfoVO extends EduCourse {
    @ApiModelProperty(value = "课程简介")
    private String description;
}
