import request from '@/utils/request'

export default {
  // 增加小节信息
  addXChapterInfo(videoInfo) {
    return request({
      url: `/education/edu-video/addXChapterInfo`,
      method: 'post',
      data: videoInfo
    })
  },
  // 修改小节信息
  updateXChapterInfo(videoInfo) {
    return request({
      url: `/education/edu-video/updateXChapterInfo`,
      method: 'put',
      data: videoInfo
    })
  },
  // 删除小节信息
  deleteXChapterInfo(id) {
    return request({
      url: `/education/edu-video/deleteXChapterInfo/${id}`,
      method: 'delete',
    })
  },
  // 查询小节信息
  getXChapterInfo(id) {
    return request({
      url: `/education/edu-video/getXChapterInfo/${id}`,
      method: 'get',
    })
  },
}

