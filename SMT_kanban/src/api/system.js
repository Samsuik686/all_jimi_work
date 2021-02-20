import { get, post, put, del, axiosPost } from '@/utils/request'

export const login = {
  login: data => axiosPost('/user/login', data),
  userInfo: data => put('/sys/user/get', data)
  // logout: () => post('/logout')
}

export const preinstall = {
  // 模板列表操作
  getStaffingList: data => get('/report/staffing-template/list', data),
  createStaffingList: data => post('/report/staffing-template/create', data),
  editStaffingList: data => post('/report/staffing-template/edit', data),
  // 模板内容操作
  getStaffingContent: data => get('/report/staffing-template-content/list', data),
  editStaffingContent: data => post('/report/staffing-template-content/edit', data)
}

export const production = {
  getPage: data => post('/report/task/page', data),
  creatData: data => post('/report/task/add', data),
  copyData: data => post('/report/task/copy', data),
  invalidData: data => get('/report/task/invalid', data),
  upgradeData: data => post('/report/task/upgrade', data),
  // 人员配置内容
  getStaffing: data => get('/report/task-staffing/list', data),
  addStaffing: data => post('/report/task-staffing/add', data),
  // 报表
  getContent: data => get('/report/task-content/getContent', data),
  // 保存模块内容
  keepNormalModule: data => post('/report/task-content/editText', data),
  keepComplexModule: data => post('/report/task-content/editComplex', data),
  // 操作人提交审核
  applyCheck: data => post('/report/task-content/applyCheck', data),
  // 审核人审核
  checkModule: data => post('/report/task-content/check', data),
  // 撤回申请
  withdrawCheck: data => post('/report/task-content/withdrawCheck', data),
  // 品质、工程确认
  confirmTask: data => post('/report/task-content/confirm', data)
}

export const detection = {
  getList: data => get('/detection/task/page', data),
  detailList: data => get('/detection/result/page', data),
  exportFile: data => get('/detection/result/export', data),
  uploadFile: data => post('/detection/task/create', data),
  delData: data => del(`/detection/task/delete?detectionTaskId=${data}`)
}

export const department = {
  getList: data => get('/sys/dept/list', data),
  editData: data => put('/sys/dept/edit', data),
  addData: data => post('/sys/dept/add', data),
  deleteData: data => del('/sys/dept/del', data)
}

export const position = {
  getList: data => get('/sys/post/list', data),
  getPage: data => get('/sys/post/page', data),
  editData: data => put('/sys/post/edit', data),
  addData: data => post('/sys/post/add', data),
  deleteData: data => del('/sys/post/del', data)
}

export const role = {
  getList: data => get('/sys/role/list', data),
  getPage: data => get('/sys/role/page', data),
  addData: data => post('/sys/role/add', data),
  editData: data => put('/sys/role/edit', data),
  deleteData: data => del('/sys/role/del', data),
  empowerPerm: data => post('/sys/role/empowerPerm', data) // 提交角色配置权限
}

export const user = {
  getPage: data => get('/sys/user/page', data),
  getList: data => get('/sys/user/list', data),
  editData: data => put('/sys/user/edit', data),
  logout: data => put('/logout', data),
  addData: data => post('/sys/user/add', data),
  resetPsd: data => put('/sys/user/password/reset', data),
  deleteData: data => del('/sys/user/del', data),
  modifyPsd: data => put('/sys/user/password/modify', data) // 修改密码
}

export const permission = {
  getList: data => get('/sys/perm/list', data),
  getListByRole: data => get('/sys/perm/listByRole', data),
  getListByUser: data => get('/sys/perm/listByUser', data)
}

export const operationLog = {
  getPage: data => get('/security/sys-oper-log/page', data)
}

