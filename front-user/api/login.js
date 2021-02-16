import request from '@/utils/request'

export default {
  // 登录请求
  submitLogin(loginVo) {
    return request({
      url: `/user/ucenter-member/login`,
      method: 'post',
      data: loginVo
    })
  },
  // 登录成功后从token中获取用户信息
  getLoginInfo(loginVo) {
    return request({
      url: `/user/ucenter-member/getUserInfoByToken`,
      method: 'get',
      data: loginVo
    })
  }
}
