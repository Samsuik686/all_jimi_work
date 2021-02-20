// 查询项
export const selectOptions = [
  {
    type: 'text',
    label: '角色名',
    key: 'name'
  }
]

// table项
export const tableTitle = [
  {
    'label': '角色名',
    'key': 'name',
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
    'min-width': '100'
  }
]

// 编辑/新增
export const formPanelOptions = [
  { type: 'text', label: '角色名', key: 'name' },
  { type: 'text', label: '角色编号', key: 'roleKey' },
  { type: 'text', label: '备注', key: 'remark' }
]

