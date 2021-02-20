// 查询项
export const selectOptions = [
  {
    type: 'select',
    label: '线号',
    key: 'line',
    list: [
      { key: '301', label: '301' },
      { key: '302', label: '302' },
      { key: '303', label: '303' },
      { key: '304', label: '304' },
      { key: '305', label: '305' },
      { key: '306', label: '306' },
      { key: '307', label: '307' },
      { key: '308', label: '308' },
      { key: '309', label: '309' },
      { key: '310', label: '310' },
    ]
  },
  {
    type: 'text',
    label: '工单',
    key: 'zhidan'
  },
  {
    type: 'text',
    label: '客户',
    key: 'client'
  },
  {
    type: 'select',
    label: '状态',
    key: 'state',
    list: [{ key: '进行中', label: '进行中' }, { key: '未开始', label: '未开始' }, { key: '已完成', label: '已完成' }]
  },
]

// table项
export const tableTitle = [
  {
    'label': '线号',
    'key': 'line',
    'min-width': '45'
  },
  {
    'label': '工单',
    'key': 'zhidan',
    'min-width': '70'
  },
  {
    'label': '机型',
    'key': 'machine_name',
    'min-width': '80'
  },
  {
    'label': '套料总数',
    'key': 'nest_quantity',
    'min-width': '80'
  },
  {
    'label': '客户',
    'key': 'client',
    'min-width': '80'
  },
  {
    'label': '已生产',
    'key': 'already_product',
    'min-width': '60'
  },
  {
    'label': '标产',
    'key': 'standard_product',
    'min-width': '60'
  },
  {
    'label': '不良数',
    'key': 'defects_number',
    'min-width': '60'
  },
  {
    'label': '不良率',
    'key': 'defects_rate',
    'min-width': '60'
  }, {
    'label': '连板数',
    'key': 'consecutive_number',
    'min-width': '60'
  },

  {
    'label': '创建时间',
    'key': 'create_time',
    'min-width': '80'
  },
  {
    'label': '开始时间',
    'key': 'esti_startTime',
    'min-width': '80'
  }, {
    'label': '结束时间',
    'key': 'esti_endTime',
    'min-width': '80'
  },

  {
    'label': '状态',
    'key': 'state',
    'min-width': '80',
    formatter(row, column, cellValue, index) {
      switch (cellValue) {

        case "进行中":
          return '进行中'
        case "已完成":
          return '已完成'
        case "未开始":
          return '未开始'
      }
    }
  },

]