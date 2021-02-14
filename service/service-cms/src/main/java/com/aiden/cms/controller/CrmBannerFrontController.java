package com.aiden.cms.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-02-14
 */

@Api(description = "banner的前台管理接口")
@RestController
@RequestMapping("/cms/bannerfront")
@CrossOrigin
public class CrmBannerFrontController {

}

