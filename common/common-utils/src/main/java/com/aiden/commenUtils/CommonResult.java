package com.aiden.commenUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aiden
 * @version 1.0
 * @description 统一返回结果处理
 * @date 2021-1-16 11:25:10
 */

@Data
public class CommonResult {
    @ApiModelProperty(value = "是否成功")
    private Boolean status;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private CommonResult() {
    }

    /**
     * 请求成功的返回结果码
     *
     * @return CommonResult
     */
    public static CommonResult success() {
        CommonResult r = new CommonResult();
        r.setStatus(ResultCode.SUCCESS.getStatus());
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        return r;
    }

    /**
     * 失败的返回码
     *
     * @return CommonResult
     */
    public static CommonResult fail() {
        CommonResult r = new CommonResult();
        r.setStatus(ResultCode.FAIL.getStatus());
        r.setCode(ResultCode.FAIL.getCode());
        r.setMessage(ResultCode.FAIL.getMessage());
        return r;
    }

    /**
     * 设置状态
     *
     * @param success success
     * @return CommonResult
     */
    public CommonResult status(Boolean success) {
        this.setStatus(success);
        return this;
    }

    /**
     * 设置信息
     *
     * @param message message
     * @return CommonResult
     */
    public CommonResult message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 设置状态码
     *
     * @param code code
     * @return CommonResult
     */
    public CommonResult code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 设置返回数据
     *
     * @param key   key
     * @param value value
     * @return CommonResult
     */
    public CommonResult data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 设置返回数据
     *
     * @param map map
     * @return CommonResult
     */
    public CommonResult data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
