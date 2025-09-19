import request from '@/utils/request'

// 查询易损件列表
export function listParts(query) {
  return request({
    url: '/system/parts/list',
    method: 'get',
    params: query
  })
}

// 查询易损件详细
export function getParts(id) {
  return request({
    url: '/system/parts/' + id,
    method: 'get'
  })
}

// 新增易损件
export function addParts(data) {
  return request({
    url: '/system/parts',
    method: 'post',
    data: data
  })
}

// 修改易损件
export function updateParts(data) {
  return request({
    url: '/system/parts',
    method: 'put',
    data: data
  })
}

export function changeNum(id,num) {
  const data = {
    id: id,
    num: num
  }
  return request({
    url: '/system/parts/changedNum',
    method: 'put',
    data: data
  })
}

// 删除易损件
export function delParts(id) {
  return request({
    url: '/system/parts/' + id,
    method: 'delete'
  })
}
