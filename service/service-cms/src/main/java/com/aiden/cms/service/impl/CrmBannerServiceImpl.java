package com.aiden.cms.service.impl;

import com.aiden.cms.entity.CrmBanner;
import com.aiden.cms.mapper.CrmBannerMapper;
import com.aiden.cms.service.CrmBannerService;
import com.aiden.commenUtils.CommonResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Aiden
 * @since 2021-02-14
 */

@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public CommonResult findByPagination(long current, long limit, CrmBanner crmBanner) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(crmBanner.getTitle())) {
            wrapper.like("title", crmBanner.getTitle());
        }
        Page<CrmBanner> page = new Page<>(current, limit);
        this.page(page, wrapper);

        // 当前页数据
        List<CrmBanner> teacherList = page.getRecords();

        // 总的记录数
        long total = page.getTotal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("rows", teacherList);
        return CommonResult.success().data(resultMap);
    }

    /**
     * 增加banner
     *
     * @param crmBanner 封装banner数据
     * @return 结果
     */
    @Override
    public CommonResult addBanner(CrmBanner crmBanner) {
        this.save(crmBanner);
        return CommonResult.success();
    }

    /**
     * 删除banner
     *
     * @param bannerId banner id
     * @return 结果
     */
    @Override
    public CommonResult deleteBanner(String bannerId) {
        this.removeById(bannerId);
        return CommonResult.success();
    }

    /**
     * 修改banner
     *
     * @param crmBanner 封装banner数据
     * @return 结果
     */
    @Override
    public CommonResult updateBanner(CrmBanner crmBanner) {
        this.updateById(crmBanner);
        return CommonResult.success();
    }

    /**
     * 根据id查询banner
     *
     * @param bannerId banner id
     * @return 结果
     */
    @Override
    public CommonResult getBannerById(String bannerId) {
        CrmBanner crmBanner = this.getById(bannerId);
        return CommonResult.success().data("crmBanner", crmBanner);
    }

    /**
     * 查询所有的banner
     *
     * @return 结果
     */
    @Override
    public CommonResult getTotalBanner() {
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return CommonResult.success().data("bannerList", bannerList);
    }
}
