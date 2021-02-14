package com.aiden.cms.service;

import com.aiden.cms.entity.CrmBanner;
import com.aiden.commenUtils.CommonResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Aiden
 * @since 2021-02-14
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 按条件分页查询banner
     *
     * @param current   当前页
     * @param limit     页大小
     * @param crmBanner 封装条件
     * @return 查询的数据
     */
    CommonResult findByPagination(long current, long limit, CrmBanner crmBanner);

    /**
     * 增加banner
     * @param crmBanner 封装banner数据
     * @return 结果
     */
    CommonResult addBanner(CrmBanner crmBanner);

    /**
     * 删除banner
     * @param bannerId banner id
     * @return 结果
     */
    CommonResult deleteBanner(String bannerId);

    /**
     * 修改banner
     * @param crmBanner 封装banner数据
     * @return 结果
     */
    CommonResult updateBanner(CrmBanner crmBanner);
}
