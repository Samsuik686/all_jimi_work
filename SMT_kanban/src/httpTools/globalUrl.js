let url;
if (process.env.NODE_ENV === 'production') {
  url = window.g.API_URL
} else {
  url = window.g.LOCAL_URL
}
const mockUrl = '';

/*用户*/
export const loginUrl = url + '/user/login';
/*工单配置*/
export const getworderorderUrl = url + '/config/selectZhidan';//获取工单数据
export const  addworkderorderUrl = url + '/config/addZhidan';//新增工单
export const  changeworkderorderUrl = url + '/config/updateZhidan';//新增工单
export const  delectworkderorderUrl = url + '/config/deleteZhidan';//删除工单config/deleteZhidan
export const addallbadrecordUrl = url + '/badcollect/addAllbadrecord'//批量增加不良数采集
export const  stopalarmUrl = url + '/config/setAlarm';//停止报警
/*不良登记*/
export const  getbedlinnerUrl = url + '/badcollect/getAOIMessage';//获取不良列表
export const  adderrorUrl = url + '/badcollect/addBadrecord';//增加不良列数
export const  reduceerrorUrl = url + '/badcollect/reduceBadrecord';//减少不良数
/*产线管理*/
export const  getlinemanage = url + '/linemanage/selectLine';//获取产线内容
export const  change_add_linemanager = url +'/linemanage/AddorUpdateline';//增加修改产线内容
export const  delect_linemanager = url +'/linemanage/deleteLineuser';//删除改产线内容
export const  getAllName = url +'/linemanage/getAllName';//linemanage/getAllName
/*人员管理*/
export const  getpeoplelest = url + '/usermanage/selectUser';//获取人员内容
export const  add_people = url +'/usermanage/addUser';//增加人员内容
export const  change_people = url +'/usermanage/updateUser';//修改人员内容
export const  delect_people = url +'/usermanage/deleteUser';//删除改人员内容
/*日志*/
export const  getLogmessage = url +'/getlog/getLogmessage';//获取日志
/*面板*/
export const  getboard = url +'/board/getboradMessageOntime';//获取面板
