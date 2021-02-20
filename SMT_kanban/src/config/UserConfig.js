// 查询项
export const selectOptions = [
  {
    type: 'text',
    label: '用户名',
    key: 'name'
  }
]
// table项
export const tableTitle = [
  {
    'label': '账号',
    'key': 'account',
    'min-width': '80'
  },
  {
    'label': '用户名',
    'key': 'name',
    'min-width': '70'
  },
  {
    'label': '手机号码',
    'key': 'phoneNumber',
    'min-width': '100'
  },
  {
    'label': '部门',
    'key': 'deptName',
    'min-width': '90'
  },
  {
    'label': '职位',
    'key': 'position',
    'min-width': '90'
  },
  {
    'label': '角色',
    'key': 'roleInfos',
    'min-width': '90'
  },
  {
    'label': '是否禁用',
    'key': 'isEnable',
    'min-width': '60'
  }
]
// 编辑/新增
export const formPanelOptions = [
  { type: 'text', label: '用户名', key: 'name' },
  { type: 'text', label: '账号', key: 'account', show: 'edit' },
  { type: 'text', label: '手机号码', key: 'phoneNumber' },
  // { type: 'deptName', label: '部门', key: 'deptName' },
  // { type: 'position', label: '职位', key: 'position' },
  { type: 'role', label: '角色', key: 'roleIds' },
  { type: 'switch', label: '是否禁用', key: 'status' }
]
