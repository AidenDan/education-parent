import request from '@/utils/request'

export default {
  // 按条件分页查询所有的数据
  getTotalSubject() {
    return request({
      url: `/education/edu-subject/getTotalSubject`,
      method: 'get'
    })
  }
}

