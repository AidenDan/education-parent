package com.aiden.commonBase.exceptionHandler;

import com.aiden.commenUtils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-23 18:49:46
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理数学异常
     *
     * @param e e
     * @return CommonResult
     */
    @ExceptionHandler(ArithmeticException.class)
    public CommonResult mathException(ArithmeticException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return CommonResult.fail().message("数学异常");
    }

    /**
     * 处理空指针异常
     *
     * @param e e
     * @return CommonResult
     */
    @ExceptionHandler(NullPointerException.class)
    public CommonResult nullException(NullPointerException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return CommonResult.fail().message("空指针异常");
    }

    /**
     * 处理自定义异常
     *
     * @param e e
     * @return CommonResult
     */
    @ExceptionHandler(EduException.class)
    public CommonResult eduException(EduException e) {
        e.printStackTrace();
        log.error(e.getMsg());
        return CommonResult.fail().code(e.getCode()).message(e.getMsg());
    }

    /**
     * 处理所有的异常
     *
     * @param e e
     * @return CommonResult
     */
    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return CommonResult.fail().message(e.getMessage());
    }
}
