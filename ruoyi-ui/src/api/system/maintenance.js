import request from '@/utils/request'

// 查询维护计划列表
export function listMaintenance(query) {
  return request({
    url: '/system/maintenance/list',
    method: 'get',
    params: query
  })
}

// 查询维护计划详细
export function getMaintenance(id) {
  return request({
    url: '/system/maintenance/' + id,
    method: 'get'
  })
}

// 新增维护计划
export function addMaintenance(data) {
  return request({
    url: '/system/maintenance',
    method: 'post',
    data: data
  })
}

// 修改维护计划
export function updateMaintenance(data) {
  return request({
    url: '/system/maintenance',
    method: 'put',
    data: data
  })
}

// 删除维护计划
export function delMaintenance(id) {
  return request({
    url: '/system/maintenance/' + id,
    method: 'delete'
  })
}
