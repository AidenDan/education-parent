package com.aiden.cms.controller;


        import com.aiden.cms.service.CrmBannerService;
        import com.aiden.commenUtils.CommonResult;
        import io.swagger.annotations.Api;
        import io.swagger.annotations.ApiOperation;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-02-14
 */

@Api(description = "banner的前台接口")
@RestController
@RequestMapping("/cms/bannerfront")
@CrossOrigin
public class CrmBannerFrontController {
    @Autowired
    CrmBannerService crmBannerService;

    @ApiOperation(value = "查询所有的banner用于首页轮播图")
    @GetMapping("/getTotalBanner")
    public CommonResult getTotalBanner() {
        return crmBannerService.getTotalBanner();
    }
}

