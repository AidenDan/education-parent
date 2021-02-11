import request from '@/utils/request'

export default {
  // 添加课程信息
  addCourseInfo(courseInfoVO) {
    return request({
      url: `/education/edu-course/addCourseInfo`,
      method: 'post',
      data: courseInfoVO
    })
  },

  // 查询课程信息
  getCourseInfoById(courseId) {
    return request({
      url: `/education/edu-course/getCourseInfoById/${courseId}`,
      method: 'get',
    })
  },

  // 更新课程信息
  updateCourseInfo(courseInfoVO) {
    return request({
      url: `/education/edu-course/updateCourseInfo`,
      method: 'post',
      data: courseInfoVO
    })
  },

  // 获取要发布课程的信息
  publishCourseInfo(courseId) {
    return request({
      url: `/education/edu-course/publishCourseInfo/${courseId}`,
      method: 'get'
    })
  },
  // 最终发布课程信息
  finalPublish(courseId) {
    return request({
      url: `/education/edu-course/finalPublish/${courseId}`,
      method: 'post',
    })
  },
  // 按条件分页查询所有的数据
  getCourseList(current, limit, courseQuery) {
    return request({
      url: `/education/edu-course/pageCondition/${current}/${limit}`,
      method: 'post',
      data: courseQuery // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  },
  // 删除课程信息
  deleteCourseById(courseId) {
    return request({
      url: `/education/edu-course/deleteCourseById/${courseId}`,
      method: 'delete',
    })
  }
}

