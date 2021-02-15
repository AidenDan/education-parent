package com.aiden.message.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.message.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-2-15 11:21:51
 */

@Api(description = "message服务")
@RestController
@RequestMapping("/message")
@CrossOrigin
public class MessageController {
    @Autowired
    MessageService messageService;

    @ApiOperation(value = "发送验证码")
    @GetMapping("/sendCode/{phoneNumber}")
    public CommonResult sendCode(@PathVariable("phoneNumber") String phoneNumber) {
        return messageService.sendMessage(phoneNumber);
    }
}
