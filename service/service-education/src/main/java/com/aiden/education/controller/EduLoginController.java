package com.aiden.education.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.entity.EduTeacher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-24 21:55:25
 */

@CrossOrigin
@Api(description = "讲师管理登录接口")
@RestController
@RequestMapping("/education/edu-teacher")
public class EduLoginController {

    // 登录接口，先写伪代码
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public CommonResult login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password) {
        // 登录成功返回一个token
        return CommonResult.success().data("token", "adminskywalker");
    }

    // 获取登录用户的详情，先写伪代码
    @ApiOperation(value = "登录")
    @GetMapping("/info")
    public CommonResult getInfo(@RequestParam(value = "token", required = false) String token) {
        // 返回用户信息详情
        return CommonResult.success().data("roles", "[admin]").data("name", "admin").data("avatar", "http://img.crcz.com/allimg/202003/17/1584410243529118-lp.jpg");
    }
}
