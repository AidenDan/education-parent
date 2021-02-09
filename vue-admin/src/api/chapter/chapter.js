import request from '@/utils/request'

export default {
  // 根据课程id获取课程章节信息
  getChapterInfoByCourseId(courseId) {
    return request({
      url: `/education/edu-chapter/getChapterInfoByCourseId/${courseId}`,
      method: 'get'
    })
  },
  // 增加章信息
  addChapterInfo(chapterInfo) {
    return request({
      url: `/education/edu-chapter/addChapterInfo`,
      method: 'post',
      data: chapterInfo
    })
  },
  // 修改章信息
  updateChapterInfo(chapterInfo) {
    return request({
      url: `/education/edu-chapter/updateChapterInfo`,
      method: 'put',
      data: chapterInfo
    })
  },
  // 删除章信息
  deleteChapterInfo(id) {
    return request({
      url: `/education/edu-chapter/deleteChapterInfo/${id}`,
      method: 'delete',
    })
  },
  // 查询章信息
  getChapterInfo(id) {
    return request({
      url: `/education/edu-chapter/getChapterInfo/${id}`,
      method: 'get',
    })
  },
}

