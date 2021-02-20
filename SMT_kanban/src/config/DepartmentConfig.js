// table项
export const tableTitle = [
  {
    'label': '部门名称',
    'key': 'name',
    'min-width': '80'
  },
  {
    'label': '创建时间',
    'key': 'gcd',
    'min-width': '70'
  }
]
// 编辑/新增
export const formPanelOptions = [
  { type: 'text', label: '部门名称', key: 'name' },
  { type: 'cascade', label: '所属部门', key: 'parentId' }
]
