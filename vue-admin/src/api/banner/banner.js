import request from '@/utils/request'

export default {
  // 按条件分页查询所有的数据
  getBannerList(current, limit, banner) {
    return request({
      url: `/cms/banneradmin/page/${current}/${limit}`,
      method: 'post',
      data: banner // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  },
  // 删除banner的请求方法
  deleteBannerById(id) {
    return request({
      url: `/cms/banneradmin/deleteBanner/${id}`,
      method: 'delete',
    })
  },
  // 保存banner的方法
  saveBanner(banner) {
    return request({
      url: `/cms/banneradmin/addBanner`,
      method: 'post',
      data: banner // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  },
  // 根据id查询banner信息
  feedBackBannerInfo(id) {
    return request({
      url: `/cms/banneradmin/getBannerById/${id}`,
      method: 'get',
    })
  },
  // 更新banner信息
  updateBanner(banner) {
    return request({
      url: `/cms/banneradmin/updateBanner`,
      method: 'put',
      data: banner // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  },
}

