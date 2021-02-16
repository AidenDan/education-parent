import request from '@/utils/request'

export default {
  // 根据讲师id查询讲师信息
  feedBackTeacherInfo(id) {
    return request({
      url: `/education/frontteacher/searchTeacher/${id}`,
      method: 'get',
    })
  },
  // 登录成功后从token中获取用户信息
  getTeacherFrontList(current, limit) {
    return request({
      url: `/education/frontteacher/page/${current}/${limit}`,
      method: 'get'
    })
  }
}
