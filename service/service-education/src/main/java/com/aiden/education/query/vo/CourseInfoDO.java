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

@ApiModel(value = "课程信息的封装类", description = "课程信息的封装类")
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseInfoDO extends EduCourse {
    @ApiModelProperty(value = "讲师名称")
    private String name;
}
