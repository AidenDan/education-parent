import request from '@/utils/request'

export default {
  // 获取验证码
  getMobile(phoneNumber) {
    return request({
      url: `/message/sendCode/${phoneNumber}`,
      method: 'get'
    })
  },
  // 注册请求
  submitRegister(registerVo) {
    return request({
      url: `/user/ucenter-member/register`,
      method: 'post',
      data: registerVo
    })
  }
}

