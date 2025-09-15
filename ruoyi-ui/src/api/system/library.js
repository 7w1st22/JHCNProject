import request from '@/utils/request'

// 查询单体库列表
export function listLibrary(query) {
  return request({
    url: '/system/library/list',
    method: 'get',
    params: query
  })
}

// 查询单体库详细
export function getLibrary(id) {
  return request({
    url: '/system/library/' + id,
    method: 'get'
  })
}


export function getLibraryByDtCode(dtCode) {
  return request({
    url: '/system/library/getLibraryByDtCode/' + dtCode,
    method: 'get'
  })
}
// 新增单体库
export function addLibrary(data) {
  return request({
    url: '/system/library',
    method: 'post',
    data: data
  })
}

// 修改单体库
export function updateLibrary(data) {
  return request({
    url: '/system/library',
    method: 'put',
    data: data
  })
}

// 删除单体库
export function delLibrary(id) {
  return request({
    url: '/system/library/' + id,
    method: 'delete'
  })
}
