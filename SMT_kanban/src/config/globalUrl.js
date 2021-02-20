let url
if (process.env.NODE_ENV === 'production') {
  url = window.g.API_URL
} else {
  url = window.g.LOCAL_URL
}

export const checkOweMaterialDataUrl = url + '/detection/task/page'// 核料单数据
export const checkOweMaterialDetailUrl = url + '/detection/result/page'// 核料单详情数据
export const checkOweMaterialDeleteUrl = url + '/detection/task/delete'// 导出文件
export const uploadUrl = url + '/detection/task/create'// 和欠料上传文件
export const exportUrl = url + '/detection/result/export'// 导出文件

export const uploadImg = url + '/report/task-content/uploadImg' // 生产信息表 上传图片
export const uploadFile = url + '/report/task-content/uploadFile' // 生产信息表 上传文件
