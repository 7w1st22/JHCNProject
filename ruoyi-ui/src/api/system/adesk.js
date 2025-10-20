import request from '@/utils/request'

// 查询IT工单列表
export function listAdesk(query) {
  return request({
    url: '/system/adesk/list',
    method: 'get',
    params: query
  })
}

// 查询IT工单详细
export function getAdesk(id) {
  return request({
    url: '/system/adesk/' + id,
    method: 'get'
  })
}

// 新增IT工单
export function addAdesk(data) {
  return request({
    url: '/system/adesk',
    method: 'post',
    data: data
  })
}

// 修改IT工单
export function updateAdesk(data) {
  return request({
    url: '/system/adesk',
    method: 'put',
    data: data
  })
}

// 删除IT工单
export function delAdesk(id) {
  return request({
    url: '/system/adesk/' + id,
    method: 'delete'
  })
}
