import request from '@/utils/request'

// 查询employeeInfo列表
export function listEmployee(query) {
  return request({
    url: '/system/employee/list',
    method: 'get',
    params: query
  })
}

// 查询employeeInfo详细
export function getEmployee(id) {
  return request({
    url: '/system/employee/' + id,
    method: 'get'
  })
}

// 新增employeeInfo
export function addEmployee(data) {
  return request({
    url: '/system/employee',
    method: 'post',
    data: data
  })
}

// 修改employeeInfo
export function updateEmployee(data) {
  return request({
    url: '/system/employee',
    method: 'put',
    data: data
  })
}

// 删除employeeInfo
export function delEmployee(id) {
  return request({
    url: '/system/employee/' + id,
    method: 'delete'
  })
}
