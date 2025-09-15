import request from '@/utils/request'

// 查询绑定管理列表
export function listBind(query) {
  return request({
    url: '/system/bind/list',
    method: 'get',
    params: query
  })
}

// 查询绑定管理详细
export function getBind(id) {
  return request({
    url: '/system/bind/' + id,
    method: 'get'
  })
}

// 新增绑定管理
export function addBind(data) {
  return request({
    url: '/system/bind',
    method: 'post',
    data: data
  })
}

// 修改绑定管理
export function updateBind(data) {
  return request({
    url: '/system/bind',
    method: 'put',
    data: data
  })
}

// 删除绑定管理
export function delBind(id) {
  return request({
    url: '/system/bind/' + id,
    method: 'delete'
  })
}
