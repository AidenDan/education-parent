import request from '@/utils/request'

export default {
  // 查询所有的banner用于首页显示
  getBannerList() {
    return request({
      url: `/cms/bannerfront/getTotalBanner`,
      method: 'get',
    })
  }
}

