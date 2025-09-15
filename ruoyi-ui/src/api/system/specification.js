import request from '@/utils/request'

// 查询模组规格列表
export function listSpecification(query) {
  return request({
    url: '/system/specification/list',
    method: 'get',
    params: query
  })
}

// 查询模组规格详细
export function getSpecification(id) {
  return request({
    url: '/system/specification/' + id,
    method: 'get'
  })
}

// 新增模组规格
export function addSpecification(data) {
  return request({
    url: '/system/specification',
    method: 'post',
    data: data
  })
}

// 修改模组规格
export function updateSpecification(data) {
  return request({
    url: '/system/specification',
    method: 'put',
    data: data
  })
}

// 删除模组规格
export function delSpecification(id) {
  return request({
    url: '/system/specification/' + id,
    method: 'delete'
  })
}
