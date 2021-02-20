export const panelFormItem = [
  {
    moduleId: 1,
    module: 'order_info',
    name: '订单信息',
    infos: [
      {
        label: '订单信息',
        type: 'text', // text、text-area、from-to、date、switch、select、upload
        position: '1',
        searchable: true,
        remark: '',
        text: '',
        content: '' // 图片或文件的路径
      },
      {
        label: '订单编号',
        type: 'text',
        position: '2',
        searchable: true,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '产品型号',
        type: 'long-text',
        position: '3',
        searchable: true,
        remark: '',
        text: '',
        content: ''
      }
    ]
  },
  {
    moduleId: 2,
    module: 'IMEI_manage',
    name: 'IMEI号段管理',
    infos: [
      {
        label: 'IMEI-起始',
        type: 'text',
        position: '1',
        searchable: true,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: 'IMEI-结束',
        type: 'text',
        position: '2',
        searchable: true,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '对应关系',
        type: 'switch',
        position: '3',
        searchable: false,
        remark: '',
        text: '',
        content: 'false'
      }
    ]
  },
  {
    moduleId: 3,
    module: 'hardware_info',
    name: 'BOM/硬件信息',
    infos: [
      {
        label: '组包装BOM版本号',
        type: 'long-text',
        position: '1',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '组包装BOM版本号生效日期',
        type: 'date',
        position: '2',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '电子BOM表版本号',
        type: 'long-text',
        position: '3',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '电子BOM表版本号生效日期',
        type: 'date',
        position: '4',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '硬件版本',
        type: 'text',
        position: '5',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '基带芯片/芯片类型',
        type: 'middle-text',
        position: '6',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      }
    ]
  },
  {
    moduleId: 4,
    module: 'accessory_description',
    name: '配件描述',
    infos: [
      {
        label: '电源线',
        type: 'image',
        position: '1',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '说明书',
        type: 'image',
        position: '2',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      // {
      //   label: '2V继电器+4PIN继电器线',
      //   type: 'image',
      //   position: '3',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      // },
      // {
      //   label: 'SOS线+SOS薄膜开关',
      //   type: 'image',
      //   position: '4',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      // },
      // {
      //   label: '音频线',
      //   type: 'image',
      //   position: '5',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      // },
      // {
      //   label: '麦克风+开关机笔',
      //   type: 'image',
      //   position: '6',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      // },
      // {
      //   label: '数据转接线',
      //   type: 'image',
      //   position: '7',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      // },
      // {
      //   label: '螺丝+螺丝刀+SIM卡盖',
      //   type: 'image',
      //   position: '8',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      // },
      // {
      //   label: '开盖拨片',
      //   type: 'image',
      //   position: '9',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      // },
      // {
      //   label: '配件',
      //   type: 'image',
      //   position: '10',
      //   searchable: false,
      //   remark: '',
      //   text: '',
      //   content: ''
      //
      // }
    ]
  },
  {
    moduleId: 5,
    module: 'apparatus_info',
    name: '机身/包装/卡通箱信息',
    infos: [
      {
        name: '机身信息',
        content: [
          {
            label: '机身外观样式',
            type: 'image',
            searchable: false,
            position: '1',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '机身镭雕',
            type: 'image',
            searchable: false,
            position: '2',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '机身贴纸',
            type: 'image',
            searchable: false,
            position: '3',
            remark: '',
            text: '',
            content: ''
          }
        ]
      },
      {
        name: '包装信息',
        content: [
          {
            label: '包装盒样式',
            type: 'image',
            searchable: false,
            position: '4',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '包装IMEI贴纸',
            type: 'image',
            searchable: false,
            position: '5',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '封套样式',
            type: 'image',
            searchable: false,
            position: '6',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '封套IEMI贴纸',
            type: 'image',
            searchable: false,
            position: '7',
            remark: '',
            text: '',
            content: ''
          }
        ]
      },
      {
        name: '卡通箱信息',
        content: [
          {
            label: '卡通箱贴纸',
            type: 'image',
            searchable: false,
            position: '1',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '消毒贴纸',
            type: 'image',
            searchable: false,
            position: '2',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '贴纸位置参考图',
            type: 'image',
            searchable: false,
            position: '3',
            remark: '',
            text: '',
            content: ''
          },
          {
            label: '撕毁无效贴纸',
            type: 'image',
            searchable: false,
            position: '4',
            remark: '',
            text: '',
            content: ''
          }
        ]
      }
    ]
  },
  {
    moduleId: 6,
    module: 'software_info',
    name: '软件物联网卡信息',
    infos: [
      {
        label: '软件发送时间',
        type: 'date',
        position: '1',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '组装软件版本',
        type: 'middle-text',
        position: '2',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '组装软件版本实际读数',
        type: 'middle-text',
        position: '3',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '单片机',
        type: 'middle-text',
        position: '4',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '单片机实际读数',
        type: 'middle-text',
        position: '5',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '域名',
        type: 'middle-text',
        position: '6',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '端口',
        type: 'middle-text',
        position: '7',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: 'SMT软件版本',
        type: 'middle-text',
        position: '8',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '升级方式',
        type: 'middle-text',
        position: '9',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '物联网卡',
        type: 'network-card',
        position: '10',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      }
    ]
  },
  {
    moduleId: 7,
    module: 'operating_instructions',
    name: '操作指引',
    infos: [
      {
        label: '附件上传',
        type: 'file',
        position: '1',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      }
    ]
  },
  // {
  //   moduleId: 8,
  //   module: 'special_requirements',
  //   name: '特殊要求',
  //   infos: [
  //     {
  //       label: '备注',
  //       type: 'text-area',
  //       position: '1',
  //       searchable: false,
  //       remark: '',
  //       text: '',
  //       content: ''
  //     }
  //   ]
  // },
  {
    moduleId: 8,
    module: 'acssess_table',
    name: '首单评估检验表',
    infos: [
      {
        label: '壳变更有或无',
        type: 'check-box',
        position: '1',
        searchable: false,
        remark: '',
        text: '',
        content: true
      },
      {
        label: '测试线变更',
        type: 'check-box',
        position: '2',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '线序变',
        type: 'check-box',
        position: '3',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '功能变化',
        type: 'check-box',
        position: '4',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '测试夹具',
        type: 'check-box',
        position: '5',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '热熔夹具',
        type: 'check-box',
        position: '6',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '老化线',
        type: 'check-box',
        position: '7',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '测试线',
        type: 'check-box',
        position: '8',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      },
      {
        label: '备注',
        type: 'middle-text',
        position: '9',
        searchable: false,
        remark: '',
        text: '',
        content: ''
      }
    ]
  }
]
// const copyData = [
//   {
//   "reportTaskId":1,
//   "moduleId":1,
//   "infos":[
//     {
//       "label":"订单信息",
//       "type":"text",
//       "searchable":true,
//       "remark":"111",
//       "text":"1",
//       "content":"xxx - xxx",
//       "position":""
//     },
//     {
//       "label":"订单信息",
//       "type":"text",
//       "searchable":true,
//       "remark":"111",
//       "text":"1",
//       "content":"xxx - xxx",
//       "position":""
//     }]
// },
// {
//   "reportTaskId":1,
//   "moduleId":1,
//   "infos":[
//   {
//     "name":"机身信息",
//     "content":[
//       {
//         "label":"订单信息",
//         "type":"text",
//         "searchable":true,
//         "remark":"111",
//         "text":"1",
//         "content":"xxx - xxx",
//         "position":""
//       },
//       {
//         "label":"订单信息",
//         "type":"text",
//         "searchable":true,
//         "remark":"111",
//         "text":"1",
//         "content":"xxx - xxx",
//         "position":""
//       }
//     ]
//   },
//   {
//     "name":"包装信息",
//     "content":[{
//       "label":"订单信息",
//       "type":"text",
//       "searchable":true,
//       "remark":"111",
//       "text":"1",
//       "content":"xxx - xxx",
//       "position":""
//     },{
//       "label":"订单信息",
//       "type":"text",
//       "searchable":true,
//       "remark":"111",
//       "text":"1",
//       "content":"xxx - xxx",
//       "position":""
//     }]
//   }
// ]
// }]
