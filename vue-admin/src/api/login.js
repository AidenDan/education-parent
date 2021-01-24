import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/education/edu-teacher/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/education/edu-teacher/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/education/edu-teacher/logout',
    method: 'post'
  })
}
