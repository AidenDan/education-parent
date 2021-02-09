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
  finalPublish(courseId){
    return request({
      url: `/education/edu-course/finalPublish/${courseId}`,
      method: 'post',
    })
  }
}

