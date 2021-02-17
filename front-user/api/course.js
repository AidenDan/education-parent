import request from '@/utils/request'

export default {
  // 按条件分页查询所有的数据
  getCourseList(current, limit, courseQuery) {
    return request({
      url: `/education/frontcourse/pageCondition/${current}/${limit}`,
      method: 'post',
      data: courseQuery // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  },
  // 查询课程信息
  getCourseInfoById(courseId) {
    return request({
      url: `/education/frontcourse/getCourseInfoById/${courseId}`,
      method: 'get',
    })
  },
}
