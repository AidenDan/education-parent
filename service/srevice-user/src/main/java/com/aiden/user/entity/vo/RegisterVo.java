package com.aiden.user.entity.vo;

import com.aiden.user.entity.UcenterMember;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-15 15:08:57
 */

@ApiModel(description = "封装注册数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterVo extends UcenterMember implements Serializable {
    private String code;
}
