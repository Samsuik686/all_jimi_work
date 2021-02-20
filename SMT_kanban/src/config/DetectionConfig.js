/* 核欠料查询项 */
export const checkMaterialQuery = [
  {
    type: 'text',
    label: '物料编码',
    key: 'itemNo'
  },
  {
    type: 'detection',
    label: '核料单号',
    key: 'detectionNo'
  },
  {
    type: 'select',
    label: '欠料状态',
    key: 'lackFlag',
    list: [{ label: '是', key: 'true' }, { label: '否', key: 'false' }, { label: '全部', key: '' }]
  }

]
/* 核欠料数据项 */
export const checkMaterialTable = [
  {
    'label': '核料单号',
    'key': 'detectionNo',
    'min-width': '100'
  },
  {
    'label': '核欠料表',
    'key': 'lackNo',
    'min-width': '80'
  },
  {
    'label': '总需求表',
    'key': 'requirementNo',
    'min-width': '80'
  },
  {
    'label': '库存物料表',
    'key': 'storageNo',
    'min-width': '80'
  },
  {
    'label': '在途物料表',
    'key': 'purchaseNo',
    'min-width': '100'
  },
  {
    'label': '暂收物料表',
    'key': 'temporaryNo',
    'min-width': '80'
  }
  // {
  //   'label': '状态',
  //   'key': 'status',
  //   'min-width': '80',
  // },

]

/* 订单详情*/
export const checkMaterialOrderDetail = [
  {
    'label': '制单号',
    'key': 'productionOrderNo',
    'min-width': '120'
  },
  {
    'label': '产品编号',
    'key': 'productionNo',
    'min-width': '100'
  },
  {
    'label': '物料编码',
    'key': 'itemNo',
    'min-width': '140'
  },
  {
    'label': '物料名称',
    'key': 'materialName',
    'min-width': '200'
  },
  {
    'label': '替代料',
    'key': 'substitutable',
    'min-width': '60'
  },
  {
    'label': '被替代料',
    'key': 'itemSubstitutes',
    'min-width': '90'
  },
  {
    'label': '用量',
    'key': 'usageNumber',
    'min-width': '60'
  },
  {
    'label': '需求量',
    'key': 'demandNumber',
    'min-width': '70'
  },
  {
    'label': '总需求',
    'key': 'aggregateDemandNumber',
    'min-width': '70'
  },
  {
    'label': '现有库存数',
    'key': 'storageNumber',
    'min-width': '70'
  },
  {
    'label': '已发料',
    'key': 'deliveryNumber',
    'min-width': '70'
  },
  {
    'label': '欠料',
    'key': 'lackNumber',
    'min-width': '80'
  },
  {
    'label': '在途',
    'key': 'purchaseNumber',
    'min-width': '70'
  },
  {
    'label': '暂收待检',
    'key': 'temporaryNumber',
    'min-width': '70'
  }
]

/* 上传文件 表单 */
export const uploadForm = [
  {
    type: 'text',
    label: '核料单号',
    key: 'detectionNo'
  },
  {
    type: 'upload',
    label: '总需求表：',
    key: 'requirementNo'
  },
  {
    type: 'upload',
    label: '库存物料表：',
    key: 'storageNo'
  },
  {
    type: 'upload',
    label: '在途物料表：',
    key: 'purchaseNo'
  },
  {
    type: 'upload',
    label: '暂收物料表：',
    key: 'temporaryNo'
  },
  {
    type: 'upload',
    label: '核欠料表：',
    key: 'lackNo'
  }
]
