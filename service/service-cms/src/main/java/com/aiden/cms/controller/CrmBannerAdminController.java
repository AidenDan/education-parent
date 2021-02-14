package com.aiden.cms.controller;


import com.aiden.cms.entity.CrmBanner;
import com.aiden.cms.service.CrmBannerService;
import com.aiden.commenUtils.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 首页banner表 前端控制器
 * 后台的crud接口
 * </p>
 *
 * @author Aiden
 * @since 2021-02-14
 */

@Api(description = "banner的后台管理接口")
@RestController
@RequestMapping("/cms/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    CrmBannerService crmBannerService;

    // 分页查询banner 查询到的数据都封装Page中
    @ApiOperation(value = "分页查询banner")
    @GetMapping("/page/{current}/{limit}")
    public CommonResult findByPagination(@PathVariable("current") long current, @PathVariable("limit") long limit, @RequestBody CrmBanner crmBanner) {
        return crmBannerService.findByPagination(current, limit, crmBanner);
    }

    @ApiOperation(value = "增加banner")
    @PostMapping("/addBanner")
    public CommonResult addBanner(@RequestBody CrmBanner crmBanner) {
        return crmBannerService.addBanner(crmBanner);
    }

    @ApiOperation(value = "删除banner")
    @DeleteMapping("/deleteBanner/{bannerId}")
    public CommonResult deleteBanner(@PathVariable("bannerId") String bannerId) {
        return crmBannerService.deleteBanner(bannerId);
    }

    @ApiOperation(value = "修改banner")
    @PutMapping("/updateBanner")
    public CommonResult updateBanner(@RequestBody CrmBanner crmBanner) {
        return crmBannerService.updateBanner(crmBanner);
    }
}

