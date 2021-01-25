import request from '@/utils/request'

export default {
  // 按条件分页查询所有的数据
  getTeacherList(current, limit, teacherQuery) {
    return request({
      url: `/education/edu-teacher/pageCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  }
}

