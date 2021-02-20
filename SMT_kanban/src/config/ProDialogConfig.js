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
// 新增
export const formPanelOptions = [
  // 订单信息/号段管理/BOM-硬件信息/软件-物联网卡信息/操作指引/首单评估：
  {
    moduleId: 1,
    module: 'order_info',
    name: '订单信息',
    moduleType: 'normal',
    remark: '',
    taskName: '',
    textInfos: [
      {
        'label': '下单日期',
        'type': 'date',
        'searchable': true,
        'position': 1,
        'remark': '',
        'text': '',
        'content': '' // 图片或文件的路径
      },
      {
        'label': '订单编号',
        'type': 'text',
        'searchable': true,
        'position': 2,
        'remark': '',
        'text': '',
        'content': ''
      },
      {
        'label': '产品型号',
        'type': 'middle-text',
        'searchable': true,
        'position': 3,
        'remark': '',
        'text': '',
        'content': ''
      },
      {
        'label': '客户名称',
        'type': 'text',
        'searchable': true,
        'position': 4,
        'remark': '',
        'text': '',
        'content': ''
      },
      {
        'label': '订单数量',
        'type': 'text',
        'searchable': true,
        'position': 5,
        'remark': '',
        'text': '',
        'content': ''
      },
      {
        'label': '交货地点',
        'type': 'text',
        'searchable': true,
        'position': 6,
        'remark': '',
        'text': '',
        'content': ''
      },
      {
        'label': '交货日期',
        'type': 'date',
        'searchable': true,
        'position': 7,
        'remark': '',
        'text': '',
        'content': ''
      }
    ]
  }
]
