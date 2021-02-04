import request from '@/utils/request'

export default {
  // 添加课程信息
  addCourseInfo(courseInfoVO) {
    return request({
      url: `/education/edu-course/addCourseInfo`,
      method: 'post',
      data: courseInfoVO
    })
  }
}

