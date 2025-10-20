import request from '@/utils/request'

// 查询检查计划列表
export function listPlan(query) {
  return request({
    url: '/system/plan/list',
    method: 'get',
    params: query
  })
}

// 查询检查计划详细
export function getPlan(id) {
  return request({
    url: '/system/plan/' + id,
    method: 'get'
  })
}

// 新增检查计划
export function addPlan(data) {
  return request({
    url: '/system/plan',
    method: 'post',
    data: data
  })
}

// 修改检查计划
export function updatePlan(data) {
  return request({
    url: '/system/plan',
    method: 'put',
    data: data
  })
}

// 删除检查计划
export function delPlan(id) {
  return request({
    url: '/system/plan/' + id,
    method: 'delete'
  })
}

// 改变提醒状态
export function changeAlarmStatus(id, alarm) {
  const data = {
    id,
    alarm
  }
  return request({
    url: '/system/plan/changeStatus',
    method: 'put',
    data: data
  })
}
