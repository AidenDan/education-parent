import request from '@/utils/request'

export default {
  // 查询热门课程
  getHotCourseList() {
    return request({
      url: `/education/front/getHotCourse`,
      method: 'get',
    })
  },
  // 查询热门讲师
  getHotTeacherList() {
    return request({
      url: `/education/front/getHotTeacher`,
      method: 'get',
    })
  }
}

