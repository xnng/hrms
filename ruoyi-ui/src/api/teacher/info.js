import request from '@/utils/request'

// 查询teacher列表
export async function listInfo(query) {
  const result = await request({
    url: '/teacher/list',
    method: 'get',
    params: query
  })
  result.rows = result.data.rows
  result.total = result.data.total
  delete result.data
  // result.rows = result.rows.map(row => {
  //   const obj = {}
  //   row.children.forEach(child => {
  //     obj[child.name] = child.value
  //   })
  //   return {
  //     ...row,
  //     ...obj
  //   }
  // })
  console.log(result)
  return result;
}

export function getQrCodeId() {
  return request({
    url: '/teacher/getAnonymousId',
    method: 'get'
  })
}

// 查询teacher详细
export function getInfo(teacherId) {
  return request({
    url: '/teacher/' + teacherId,
    method: 'get'
  })
}

// 新增teacher
export function addInfo(data) {
  return request({
    url: '/teacher',
    method: 'post',
    data: data
  })
}

// 修改teacher
export function updateInfo(data) {
  return request({
    url: '/teacher',
    method: 'put',
    data: data
  })
}

// 删除teacher
export function delInfo(teacherId) {
  return request({
    url: '/teacher/' + teacherId,
    method: 'delete'
  })
}
