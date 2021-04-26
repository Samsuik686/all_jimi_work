package cn.concox.app.cumstomFlow.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.concox.app.common.page.Page;
import cn.concox.app.cumstomFlow.CustomFlowCache;
import cn.concox.app.cumstomFlow.mapper.CustomFlowMapper;
import cn.concox.app.cumstomFlow.mapper.CustomTaskMapper;
import cn.concox.app.rolePrivilege.mapper.UserRoleMgtMapper;
import cn.concox.comm.Globals;
import cn.concox.comm.util.FileUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.customProcess.CustomField;
import cn.concox.vo.customProcess.CustomFlow;
import cn.concox.vo.customProcess.CustomModel;
import cn.concox.vo.customProcess.CustomTask;
import cn.concox.vo.customProcess.CustomTaskFile;
import cn.concox.vo.rolePrivilege.UserRoleMgt;
import sso.comm.model.UserInfo;

/**
 * <pre>
 * 异常管理业务类
 * </pre>
 */
@Service("customTaskService")
@Scope("prototype")
public class CustomTaskService {
	
	Logger logger = Logger.getLogger(CustomTaskService.class);
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="customTaskMapper")
	private  CustomTaskMapper<CustomTask> customTaskMapper;
	
	@Resource(name = "userRoleMgtMapper")
	private UserRoleMgtMapper<UserRoleMgt> userRoleMgtMapper;
	
	@Autowired
	private CustomFlowCache customFlowCache;
	
	@Autowired
	private CustomFlowMapper<CustomFlow> customFlowMapper;
	
		
	@Value("${custom_file_upload_url}")
	private String CUSTOM_FILE_UPLOAD_URL;
	
	
	public CustomTask select(CustomTask customTask){
		List<CustomTaskFile> fileList = this.getFileByYid(customTask.getId());
		CustomTask ct = new CustomTask();
		ct = customTaskMapper.select(customTask);
		if (null != fileList && fileList.size() > 0) {
			 ct.setAttachFileList(fileList);
		}
		return ct;
	}
	
	public void update(CustomTask ycfkTwomanage, MultipartFile[] files, String userName){
		customTaskMapper.update(ycfkTwomanage);
		try {
			this.uploadFile(files, userName, ycfkTwomanage);
		} catch (Exception e) {
			logger.error("上传附件失败",e);
		}
	}
	
	// 更新任务状态为完成
	public void updateStateFinish(String... ids){
		customTaskMapper.updateStateFinish(ids);
	}
	
	
	public void detele(String[] ids){
		List<String> urls = customTaskMapper.selectFileUrlByYids(ids);
		for(String url : urls){
			File file = new File(url);
			if(file.exists()){
				file.delete();
			}
		}
		customTaskMapper.deleteFileByYid(ids); // 删除异常反馈时连带删除附件信息
		customTaskMapper.deleteBySerials(ids);
	}
	
	public void insert(CustomTask customTask, MultipartFile[] files, String userName){		
		customTaskMapper.insert(customTask);	
		try {
			this.uploadFile(files, userName, customTask);
		} catch (Exception e) {
			logger.error("上传附件失败",e);
		}
	}
	
	/**
	 * 文件上传
	 * @author TangYuping
	 * @version 2017年4月14日 下午3:24:57
	 * @param files
	 * @param userName
	 * @param customTask
	 * @throws Exception
	 */
	public void uploadFile(MultipartFile[] files, String userName,CustomTask customTask) throws Exception {
		if (null != files && files.length > 0 ) {			
			for (MultipartFile file:files) {
				String fileName=file.getOriginalFilename();//上传的文件名	
				if (file.getSize() > 0 && null != fileName && !"".equals(fileName)) {
					String linuxFileName = "CT_"+UUID.randomUUID().toString();//服务器上面的文件名
					String suffix = fileName.substring(fileName.lastIndexOf("."));
					//线上路径
					String uploadFilePath = CUSTOM_FILE_UPLOAD_URL+"ct/"+linuxFileName+suffix;
					
					InputStream ins = file.getInputStream();	 //读取文件的输入流
					boolean b = FileUtil.readInputStreamToFile(ins, uploadFilePath);
					if (b) {
						this.ctUploadFile(customTask, userName, fileName, uploadFilePath);
					} else {
						throw new FileNotFoundException("文件上传失败！");
					}
				}
			}
		}
	}
	
	/**
	 * 自定义流程文件添加
	 * @author TangYuping
	 * @version 2017年4月14日 上午10:48:26
	 * @param ycfkManage
	 * @param fileName
	 * @param filePath
	 */
	public void ctUploadFile(CustomTask customTask, String userName, String fileName, String filePath){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		CustomTaskFile ctFile = new CustomTaskFile(customTask.getId(), fileName, filePath, 
					userName, now, customTask.getModelId());
		customTaskMapper.insertYcfkFile(ctFile);
	}
	
	/**
	 * 根据异常反馈ID获取对应附件信息
	 * @author TangYuping
	 * @version 2017年4月14日 下午1:47:58
	 * @param yid
	 * @return
	 */
	public List<CustomTaskFile> getFileByYid(Integer yid){
		if (null != yid && !"".equals(yid)) {
			return customTaskMapper.getCustomTaskFile(yid);
		}
		return new ArrayList<CustomTaskFile>();
	}
	
	
	public int isExists(CustomTask ycfkTwomanage){
		return customTaskMapper.isExists(ycfkTwomanage);
	}
	
	public Page<CustomTask> getCustomTaskListPage(CustomTask cumstomTask,int currentPage, 
			int pageSize, String userId){
		Page<CustomTask> customTaskPage = new Page<CustomTask>();
		List<String> roleIds = userRoleMgtMapper.getUserRoleIdListByUId(userId);
		customTaskPage.setCurrentPage(currentPage);
		customTaskPage.setSize(pageSize);
		
		if(roleIds != null && roleIds.size()>0 && null != cumstomTask.getModelId()){
			if(roleIds.indexOf("25") > -1 || roleIds.indexOf("176") > -1){
				//管理员可以查看所有数据
				cumstomTask.setCreater(null);
				cumstomTask.setFollower(null);
			} else {
				// 只能查看自己和跟进的数据	
				cumstomTask.setCreater(userId);
				cumstomTask.setFollower(userId);
			}
		}
		customTaskPage =customTaskMapper.queryCustomTaskListPage(customTaskPage,cumstomTask);
		// 赋予流程名称
		List<CustomTask> customTask = customTaskPage.getResult();
		for(CustomTask ct : customTask){
			ct.setModelName(customFlowCache.getModelNameMap().get(ct.getModelId()));
		}
		return customTaskPage;
	} 
	

	@SuppressWarnings("unchecked")
	public void exportDatas(CustomTask customTask, HttpServletRequest request,HttpServletResponse response, String userId) throws IOException {
		int startIndex = 0;
		int pageSize = 50000;
		// 总览界面查询所有
		if("0".equals(customTask.getModelId())){
			customTask.setModelId(null);
		}
		Page<CustomTask> customTaskListPage = getCustomTaskListPage(customTask, startIndex, pageSize, userId);
		int total = customTaskListPage.getTotal();
		if(total <= 0){
			nullRecords(response,"查询数据为空");
			return;
		}
		List<CustomTask> customTaskResutl = customTaskListPage.getResult();
		
		List<CustomField> customField = customFlowCache.getCustomFieldMap().get(customTask.getFlowName());
		
	    String[] fieldNames = new String[] {"流水号","上一工站","所属流程","所在工站","反馈人","跟进人","更新人","反馈日期","更新时间","备注","状态"};
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    OutputStream ouputStream = response.getOutputStream();
	    // 循环创建sheet，一页50000条记录
	    try {
	    	HSSFWorkbook wb = new HSSFWorkbook();
			do{
				if(startIndex != 0){
					Page<CustomTask> resultPage = getCustomTaskListPage(customTask, startIndex, pageSize, userId);
					customTaskResutl = resultPage.getResult();
				}
				
				HSSFSheet sheet = wb.createSheet(customTask.getFlowName() + "->" + (customTask.getModelId() == null?"开始":customFlowCache.getModelNameMap().get(customTask.getModelId())) + "统计_" + startIndex + "-" + (startIndex+pageSize));

				HSSFRow row = sheet.createRow((int) 0);

				HSSFCellStyle style = wb.createCellStyle();
				Font font = wb.createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
				style.setFont(font);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中

				HSSFCellStyle cellStyle = wb.createCellStyle();
				HSSFDataFormat format = wb.createDataFormat();
				cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
				

				row.setHeight((short) (15.625 * 20));
				int count = 0;
				for (; count < fieldNames.length; count++) {
					HSSFCell cell = row.createCell(count);
					cell.setCellValue(fieldNames[count]);
					cell.setCellStyle(style);
					sheet.setColumnWidth(count, 5000);
				}
				
				// 自定义字段
				if(customField != null && customField.size() > 0 ){
					for(CustomField cf : customField){
						HSSFCell cell = row.createCell(count);
						cell.setCellValue(cf.getName());
						cell.setCellStyle(style);
						sheet.setColumnWidth(count, 5000);
						count++;						
					}
				}
				
				ObjectMapper om = new ObjectMapper();
				
				for (int i = 0; i < customTaskResutl.size(); i++) {
					row = sheet.createRow(i + 1);
					CustomTask change = customTaskResutl.get(i);
					// "流水号","上一工站","所属流程","所在工站","反馈人","跟进人","更新人","反馈日期","更新时间","备注","状态"
					row.createCell(0).setCellValue(change.getSerial());
					row.createCell(1).setCellValue(change.getFromModel());
					row.createCell(2).setCellValue(change.getFlowName());
					row.createCell(3).setCellValue(change.getModelName());
					row.createCell(4).setCellValue(change.getCreater());
					row.createCell(5).setCellValue(change.getFollower());
					row.createCell(6).setCellValue(change.getUpdater());
					
					row.createCell(7).setCellValue(change.getCreateDate() == null ? "" : sdf.format(change.getCreateDate()));
					row.createCell(8).setCellValue(change.getUpdateDate() == null ? "" : sdf.format(change.getUpdateDate()));
					row.createCell(9).setCellValue(change.getRemark());
					row.createCell(10).setCellValue(change.getState() == 0 ? "处理中" : "已完成");
					
					Map<String,String> params = om.readValue(change.getParams(), Map.class);
					if(customField != null && customField.size() > 0 ){
						int filedCount = 11;
						for(CustomField cf : customField){
							row.createCell(filedCount).setCellValue(params.get(cf.getName()));
							filedCount++;
						}
					}
				}
				
				sheet.autoSizeColumn((short) 2);
				startIndex = startIndex + pageSize;
			}while(startIndex < total);
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=imeiXpidExport_" + sdf.format(new Date()) + ".xls");
			wb.write(ouputStream);
			ouputStream.flush();
			
		} catch (Exception e) {
			nullRecords(response,"导出失败，位置异常!");
			logger.error("自定义任务导出失败",e);
		} finally{
			ouputStream.close();			
		}
	    
	}

	
	/**
	 * 跳转错误说明
	 * 
	 * @author TangYuping
	 * @version 2017年4月12日 上午11:51:01
	 * @param response
	 */
	public void nullRecords(HttpServletResponse response, String mess) {

		try {
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.println("<script language='javascript'>");
			out.println("alert('" + mess + "');");
			out.println("history.back();");
			out.print("</script>");
			out.close();
		} catch (IOException e) {
			logger.error("跳转失败:" , e);
		}
	}
	
	public List<CustomTask> queryList(CustomTask ycfkTwomanage){
		return customTaskMapper.queryList(ycfkTwomanage);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
//	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
//		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
//		List<CustomTask> successList = new ArrayList<CustomTask>();// 保存可以插入成功的表的数据
//		List<String> repeatList = new ArrayList<String>();// 重复数据
//		Object[] result;
//		try {
//			result = ReadExcel.readXls(file,null);
//			for (int i = 0; i < result.length; i++) {
//				Object[] m = (Object[]) result[i];
//				if (m != null && m.length > 0) {
//					for (int j = 2; j < m.length; j++) { // TODO 行
//						CustomTask s = new CustomTask();
//
//						// YcfkTwomanage对象的属性值{model[0]、imei[1]、warranty[2]、description[3]、number[4]、feedBackPerson[5]、feedBackDate[6]、recipient[7]、responsibilityUnit[8]、
//						//measures[9]、completionState[10]、completionDate[11]、followupPeople[12]、customerName[13]、phone[14]、remak[15]}
//						Object[] obj = new Object[16];
//
//						Object[] n = (Object[]) m[j];
//						if (n != null && n.length > 0) {
//							for (int k = 0; k < n.length; k++) { // TODO 列
//								obj[k] = n[k];
//							}
//							try {
//								// 判断非空字段，如有为空，直接抛出异常
//								if (StringUtils.isBlank((String)obj[3]) || StringUtils.isBlank((String)obj[10])) {
//									throw new RuntimeException();
//								} else {
//									s.setModel((String)obj[0]);
//									s.setImei((String) obj[1]);
//									if(!StringUtils.isBlank((String)obj[2])){
//										if(((String)obj[2]).equals("保内")){
//											s.setWarranty(0);
//										}else if(((String)obj[2]).equals("保外")){
//											s.setWarranty(1);
//										}
//									}
//									s.setDescription((String)obj[3]);
//									if(!StringUtils.isBlank((String)obj[4])){
//										s.setNumber(Integer.valueOf((String)obj[4]));
//									}
//									s.setFeedBackPerson((String)obj[5]);
//									if(StringUtils.isBlank((String)obj[6])){
//										s.setFeedBackDate(new Timestamp(System.currentTimeMillis()));
//									}else{
//										s.setFeedBackDate(DateUtil.getTimestampByStr((String)obj[6]));
//									}
//									s.setRecipient((String)obj[7]);
//									s.setResponsibilityUnit((String)obj[8]);
////									处理措施列表暂不使用									
//									s.setMeasures(generateMeasuresTxt(request, (String)obj[9], ""));
//									
//									if("已完成".equals(((String)obj[10]).trim())){
//										s.setCompletionState(1);
//										if(!StringUtils.isBlank((String)obj[11])){
//											s.setCompletionDate(DateUtil.getTimestampByStr((String)obj[11]));
//										}else{
//											s.setCompletionDate(new Timestamp(System.currentTimeMillis()));
//										}
//									}else{
//										s.setCompletionState(0);
//										s.setCompletionDate(null);
//									}
//									s.setFollowupPeople((String)obj[12]);
//									s.setCustomerName((String)obj[13]);
//									s.setPhone((String)obj[14]);
//									s.setRemak((String)obj[15]);
//									if(isExists(s)==0){
//										try {
//											customTaskMapper.insert(s);
//											successList.add(s);
//										} catch (Exception e) {
//											e.printStackTrace();
//										}
//									}else{
//										repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行数据已存在,未导入,请检查......");
//									}
//								}
//							} catch (Exception e) {
//								if (errorList.size() < 1000) {// 最多保存1000条错误数据
//									errorList.add((errorList.size() + 1) + ":第" + (j + 1) + "行导入错误,有必填数据未填写......");
//								}
//							}
//						}
//					}
//					if (errorList.size() < 1) {
//
//						if (successList.size() > 0) {
//							errorList.add("导入成功！！！");
//						}else{
//							errorList.add("可导入数据已全部存在！！！");
//						}
//						if(repeatList.size()>0){
//							errorList.addAll(repeatList);
//						}
//					} else {
//						errorList.add(0, "导入失败.......");
//					}
//				}
//			}
//		} catch (Exception e) {
//			throw new RuntimeException();
//		}
//		return errorList;
//	}

	
	/**
	 * 异常反馈下载附件
	 * @author TangYuping
	 * @version 2017年4月14日 下午2:18:52
	 * @param file
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	public void downLoadFile (CustomTaskFile file, HttpServletRequest req,
			HttpServletResponse response) throws Exception {		
		String fileUrl = URLDecoder.decode(file.getFileUrl(),"UTF-8");
		String fileName = file.getFileName();
		boolean b = FileUtil.downLoadFile(req, response, fileUrl, fileName);
		if (!b) {
			throw new IOException();
		}
	}
	
	/**
	 * 删除附件
	 * @author TangYuping
	 * @version 2017年4月14日 下午2:23:36
	 * @param file
	 * @throws Exception
	 */
	public void deleteFileByFid(CustomTaskFile file) throws Exception{
		if (null != file.getFid() && file.getFid() > 0) {
			if (null != file.getFid() && file.getFid() > 0) {
				String url = customTaskMapper.selectFileUrlByFid(file.getFid());
				if(!StringUtil.isEmpty(url)){
					File attache = new File(url);
					if(attache.exists()){
						attache.delete();
					}
				}
				customTaskMapper.deleteFileByFid(file.getFid());
			}			
		} else {
			throw new Exception("无法获取ID");
		}
	}

	@Transactional
	public String sendData(CustomTask customTask, String idsemp, HttpServletRequest req, CustomModel customModel) throws ParseException { 
		UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
		// 非结束模块，获取id相关信息
		List<CustomTask> selectByIds = customTaskMapper.selectByIds(idsemp.split(","));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 将每个任务插入下一流程页面
		for(CustomTask ct : selectByIds){
			// 校验设备当前所属模块，是否能发往下一个模块
			String modelId = ct.getModelId();
			CustomModel query = new CustomModel();
			query.setBelong(customTask.getFlowName());
			// 0代表总览节点
			if ("0".equals(modelId)) {
				query.setType(0);
			} else {
				query.setId(modelId);
			}
			List<CustomModel> list = customFlowMapper.queryCustomModel(query);
			if (list == null || list.size() != 1) {
				return "获取模块信息失败";
			}
			CustomModel cm = list.get(0);
			String backNodes = cm.getBackNodes();
			List<String> asList = Arrays.asList(backNodes.split(","));
			if(!asList.contains(customTask.getModelId())){
				return "请检查数据，部分数据无法流转到下一工站！";
			}
			
			Integer id = ct.getId();
			ct.setId(null);
			ct.setFromModel(customFlowCache.getModelNameMap().get(modelId));
			ct.setFollower(customTask.getFollower());
			ct.setCreater(user.getuId());
			ct.setUpdater(user.getuId());
			ct.setCreateDate(new Date());
			ct.setUpdateDate(new Date());
			ct.setState(0);
			ct.setModelId(customTask.getModelId());
			if(!StringUtil.isEmpty(customTask.getExpireDateStr())){
				ct.setExpireDate(sdf.parse(customTask.getExpireDateStr()));				
			}
			
			customTaskMapper.insert(ct);
			
			List<CustomTaskFile> fileByYid = this.getFileByYid(id);
			// 附件信息同步插入
			for(CustomTaskFile file : fileByYid){
				file.setFid(null);
				file.setYid(ct.getId());
				customTaskMapper.insertYcfkFile(file);
			}
		}
		
		// 更新状态为已完成
		customTaskMapper.updateStateFinish(idsemp.split(","));
		
		return "发送数据到下一工站成功";
	}
	
	
	public void updateExpireFixed(){
		customTaskMapper.updateExpireFixed();
	}
}
