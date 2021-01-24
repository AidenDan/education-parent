package com.aiden.commonBase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aiden
 * @version 1.0
 * @description 自定义异常类
 * @date 2021-1-23 19:15:17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException {
    private int code;
    private String msg;
}
