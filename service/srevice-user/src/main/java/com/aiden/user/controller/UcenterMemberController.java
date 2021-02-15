package com.aiden.user.controller;


import com.aiden.commenUtils.CommonResult;
import com.aiden.user.entity.UcenterMember;
import com.aiden.user.entity.vo.RegisterVo;
import com.aiden.user.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-02-15
 */

@Api(description = "用户中心接口")
@CrossOrigin
@RestController
@RequestMapping("/user/ucenter-member")
public class UcenterMemberController {
    @Autowired
    UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    @ApiOperation(value = "根据手机号与用户密码进行登录")
    public CommonResult userLogin(@RequestBody UcenterMember member) {
        String token = ucenterMemberService.login(member);
        return CommonResult.success().data("token", token);
    }

    @PostMapping("/register")
    @ApiOperation(value = "根据手机号与用户密码进行登录")
    public CommonResult register(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return CommonResult.success();
    }

    @GetMapping("/getUserInfoByToken")
    @ApiOperation(value = "根据token获取其中的用户信息")
    public CommonResult getUserInfoByToken(HttpServletRequest request) {
        UcenterMember member = ucenterMemberService.getUserInfoByToken(request);
        return CommonResult.success().data("member", member);
    }
}

