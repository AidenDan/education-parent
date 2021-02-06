import request from '@/utils/request'

export default {
  // 根据课程id获取课程信息
  getChapterInfoByCourseId(courseId) {
    return request({
      url: `/education/edu-chapter/getChapterInfoByCourseId/${courseId}`,
      method: 'get'
    })
  }
}

