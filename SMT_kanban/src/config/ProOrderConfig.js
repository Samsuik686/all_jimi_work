// 查询项
export const selectOptions = [
  {
    type: 'date',
    label: '下单日期',
    position: '1',
    necessary: true,
    content: ''
    // key: 'name'
  },
  {
    type: 'text',
    label: '订单编号',
    position: '2',
    necessary: true,
    content: ''
    // key: 'name'
  },
  {
    type: 'text',
    label: '产品型号',
    position: '3',
    necessary: true,
    content: ''
    // key: 'name'
  },
  {
    type: 'text',
    label: '客户名称',
    position: '4',
    necessary: true,
    content: ''
    // key: 'name'
  }
]
// table项
export const tableTitle = [
  {
    'label': '版本',
    'key': 'version',
    'min-width': '50'
  },
  {
    'label': '状态',
    'key': 'statusName',
    'min-width': '60'
  },
  {
    'label': '订单编号',
    'key': 'orderNo',
    'min-width': '70'
  },
  {
    'label': '产品型号',
    'key': 'proModel',
    'min-width': '70'
  },
  {
    'label': '客户名称',
    'key': 'customerName',
    'min-width': '70'
  },
  {
    'label': '订单数量',
    'key': 'orderQuantity',
    'min-width': '65'
  },
  {
    'label': '下单日期',
    'key': 'orderDate',
    'min-width': '105'
  },
  {
    'label': '交货地点',
    'key': 'deliveryPlace',
    'min-width': '70'
  },
  {
    'label': '交货日期',
    'key': 'deliveryDate',
    'min-width': '105'
  },
  {
    'label': '备注',
    'key': 'remark',
    'min-width': '60'
  }

]
// 编辑/新增
export const formPanelOptions = [
  { type: 'text', label: '职位名称', key: 'name' },
  { type: 'cascade', label: '所属部门', key: 'deptId' },
  { type: 'text', label: '备注', key: 'remark' }
]
