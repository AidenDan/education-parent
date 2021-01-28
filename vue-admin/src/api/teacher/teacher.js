import request from '@/utils/request'

export default {
  // 按条件分页查询所有的数据
  getTeacherList(current, limit, teacherQuery) {
    return request({
      url: `/education/edu-teacher/pageCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  },
  // 删除讲师的请求方法
  deleteTeacherById(id) {
    return request({
      url: `/education/edu-teacher/${id}`,
      method: 'delete',
    })
  },
  // 保存讲师的方法
  saveTeacher(teacher){
    return request({
      url: `/education/edu-teacher/addTeacher`,
      method: 'post',
      data: teacher // post请求提交参数对象，以json格式，封装到ResponseBody中
    })
  }
}

