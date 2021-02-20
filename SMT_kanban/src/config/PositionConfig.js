// 查询项
export const selectOptions = [
  {
    type: 'text',
    label: '职位名称',
    key: 'name'
  }
]
// table项
export const tableTitle = [
  {
    'label': '职位名称',
    'key': 'name',
    'min-width': '80'
  },
  {
    'label': '所属部门',
    'key': 'deptName',
    'min-width': '80'
  },
  {
    'label': '创建时间',
    'key': 'gcd',
    'min-width': '70'
  },
  {
    'label': '备注',
    'key': 'remark',
    'min-width': '70'
  }
]
// 编辑/新增
export const formPanelOptions = [
  { type: 'text', label: '职位编号', key: 'code' },
  { type: 'text', label: '职位名称', key: 'name' },
  { type: 'cascade', label: '所属部门', key: 'deptId' },
  { type: 'text', label: '备注', key: 'remark' }
]
