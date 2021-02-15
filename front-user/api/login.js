import request from '@/utils/request'

export default {
  // 注册请求
  submitLogin(loginVo) {
    return request({
      url: `/user/ucenter-member/login`,
      method: 'post',
      data: loginVo
    })
  }
}
