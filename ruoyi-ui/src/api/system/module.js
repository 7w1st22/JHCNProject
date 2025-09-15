import request from '@/utils/request'

// 查询模组条码列表
export function listModule(query) {
  return request({
    url: '/system/module/list',
    method: 'get',
    params: query
  })
}

// 查询模组条码详细
export function getModule(id) {
  return request({
    url: '/system/module/' + id,
    method: 'get'
  })
}

// 新增模组条码
export function addModule(data) {
  return request({
    url: '/system/module',
    method: 'post',
    data: data
  })
}

// 修改模组条码
export function updateModule(data) {
  return request({
    url: '/system/module',
    method: 'put',
    data: data
  })
}

// 删除模组条码
export function delModule(id) {
  return request({
    url: '/system/module/' + id,
    method: 'delete'
  })
}
