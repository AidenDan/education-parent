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

  // 添加课程信息
  getCourseInfoById(courseId) {
    return request({
      url: `/education/edu-course/getCourseInfoById/${courseId}`,
      method: 'get',
    })
  }
}

