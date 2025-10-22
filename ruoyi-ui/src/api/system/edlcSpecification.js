import request from '@/utils/request'

// 查询电极规格管理列表
export function listEdlcSpecification(query) {
  return request({
    url: '/system/edlcSpecification/list',
    method: 'get',
    params: query
  })
}

// 查询电极规格管理详细
export function getEdlcSpecification(id) {
  return request({
    url: '/system/edlcSpecification/' + id,
    method: 'get'
  })
}

// 新增电极规格管理
export function addEdlcSpecification(data) {
  return request({
    url: '/system/edlcSpecification',
    method: 'post',
    data: data
  })
}

// 修改电极规格管理
export function updateEdlcSpecification(data) {
  return request({
    url: '/system/edlcSpecification',
    method: 'put',
    data: data
  })
}

// 删除电极规格管理
export function delEdlcSpecification(id) {
  return request({
    url: '/system/edlcSpecification/' + id,
    method: 'delete'
  })
}
