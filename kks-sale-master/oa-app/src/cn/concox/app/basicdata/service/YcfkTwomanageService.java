package cn.concox.app.basicdata.service;

import java.io.*;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.app.basicdata.controller.YcfkTwomanageController;
import cn.concox.app.common.util.LogoutUtils;
import cn.concox.app.user.controller.service.UserService;
import cn.concox.comm.mail.CVMailUtil;
import cn.concox.comm.util.CVConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.mapper.YcfkTwomanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.report.service.HwmReportService;
import cn.concox.app.rolePrivilege.mapper.UserRoleMgtMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.FileUtil;
import cn.concox.vo.basicdata.YcfkManageFile;
import cn.concox.vo.basicdata.YcfkTwomanage;
import cn.concox.vo.rolePrivilege.UserRoleMgt;
import cn.concox.wechat.uti.pay.DateUtil;
import freemarker.template.Template;
import sso.comm.util.SSOUtils;

/**
 * <pre>
 * 异常管理业务类
 * </pre>
 */
@Service("ycfkTwomanageService")
@Scope("prototype")
public class YcfkTwomanageService {
	Logger log=Logger.getLogger(YcfkTwomanageController.class);
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="ycfkTwomanageMapper")
	private  YcfkTwomanageMapper<YcfkTwomanage> ycfkTwomanageMapper;
	
	@Resource(name = "userRoleMgtMapper")
	private UserRoleMgtMapper<UserRoleMgt> userRoleMgtMapper;

	@Resource(name = "userService")
	private UserService userService;
	
	@Value("${file_upload_url}")
	private String FILE_UPLOAD_URL;
	
	public YcfkTwomanage select(YcfkTwomanage ycfkTwomanage){
		List<YcfkManageFile> fileList = this.getFileByYid(ycfkTwomanage.getBackId());
		YcfkTwomanage ycfk = new YcfkTwomanage();
		 ycfk = ycfkTwomanageMapper.select(ycfkTwomanage);
		if (null != fileList && fileList.size() > 0) {
			 ycfk.setYcfkFileList(fileList);
		}
		return  ycfk;
	}
	
	public void update(YcfkTwomanage ycfkTwomanage, MultipartFile[] files, String userName){
		ycfkTwomanageMapper.update(ycfkTwomanage);
		try {
			this.uploadFile(files, userName, ycfkTwomanage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void detele(String[] ids){
		ycfkTwomanageMapper.deleteFileByYid(ids); // 删除异常反馈时连带删除附件信息
		ycfkTwomanageMapper.deleteByIds(ids);
	}
	
	public void insert(YcfkTwomanage ycfkTwomanage, MultipartFile[] files, String userName){		
		ycfkTwomanageMapper.insert(ycfkTwomanage);	
		try {
			this.uploadFile(files, userName, ycfkTwomanage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件上传
	 * @author TangYuping
	 * @version 2017年4月14日 下午3:24:57
	 * @param files
	 * @param userName
	 * @param ycfkTwomanage
	 * @throws Exception
	 */
	public void uploadFile (MultipartFile[] files, String userName,YcfkTwomanage ycfkTwomanage) throws Exception {
		if (null != files && files.length > 0 ) {			
			for (MultipartFile file:files) {
				String fileName=file.getOriginalFilename();//上传的文件名	
				if (file.getSize() > 0 && null != fileName && !"".equals(fileName)) {
					String linuxFileName = "YCFK_"+UUID.randomUUID().toString();//服务器上面的文件名
					String suffix = fileName.substring(fileName.lastIndexOf("."));
					//本地测试路径，
//					String rootPath=getClass().getResource("/").getFile().toString();  
//					String uploadFilePath=rootPath.substring(0,rootPath.lastIndexOf("WEB-INF"))+"uploadfile/"+linuxFileName+suffix;
//					uploadFilePath = new File(uploadFilePath).getPath().replaceAll("%20", "");
					//线上路径
					String uploadFilePath = FILE_UPLOAD_URL+"ycfk/"+linuxFileName+suffix;
					
					InputStream ins = file.getInputStream();	 //读取文件的输入流
					boolean b = FileUtil.readInputStreamToFile(ins, uploadFilePath);
					if (b) {
						this.ycfkUploadFile(ycfkTwomanage, userName, fileName, uploadFilePath);
					} else {
						throw new FileNotFoundException("文件上传失败！");
					}
				}
				
			}
			
			
		}
	}
	
	/**
	 * 异常反馈添加文件
	 * @author TangYuping
	 * @version 2017年4月14日 上午10:48:26
	 * @param ycfkManage
	 * @param fileName
	 * @param filePath
	 */
	public void ycfkUploadFile(YcfkTwomanage ycfkManage, String userName, String fileName, String filePath){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		YcfkManageFile ycfkFile = new YcfkManageFile(ycfkManage.getBackId(), fileName, filePath, 
					userName, now, ycfkManage.getCurrentSite());
		ycfkTwomanageMapper.insertYcfkFile(ycfkFile);
	}
	
	/**
	 * 根据异常反馈ID获取对应附件信息
	 * @author TangYuping
	 * @version 2017年4月14日 下午1:47:58
	 * @param yid
	 * @return
	 */
	public List<YcfkManageFile> getFileByYid(Integer yid){
		if (null != yid && !"".equals(yid)) {
			return ycfkTwomanageMapper.getYcfkManageFile(yid);
		}
		return new ArrayList<YcfkManageFile>();
	}
	
	
	public int isExists(YcfkTwomanage ycfkTwomanage){
		return ycfkTwomanageMapper.isExists(ycfkTwomanage);
	}
	
	@SuppressWarnings("unchecked")
	public Page<YcfkTwomanage> getYcfkTwomanageListPage(YcfkTwomanage ycfkTwomanage,int currentPage, 
			int pageSize, String userId){
		Page<YcfkTwomanage> ycfkTwomanagePage = new Page<YcfkTwomanage>();
		List<String> roleIds = userRoleMgtMapper.getUserRoleIdListByUId(userId);
		ycfkTwomanagePage.setCurrentPage(currentPage);
		ycfkTwomanagePage.setSize(pageSize);

		if(roleIds != null && roleIds.size()>0 && null != ycfkTwomanage.getCurrentSite()){
			if(roleIds.indexOf("25") > -1 || roleIds.indexOf("96") > -1){
				//管理员可以查看所有数据
				ycfkTwomanage.setFollowupPeople(ycfkTwomanage.getFollowupPeople());
			} else {
				// 只能查看自己跟进的数据	
				ycfkTwomanage.setFollowupPeople(ycfkTwomanage.getCurrLoginer());
			}
		}
		ycfkTwomanagePage =ycfkTwomanageMapper.queryYcfkTwomanageListPage(ycfkTwomanagePage,ycfkTwomanage);
		List<YcfkTwomanage> ycfkTwomanage_list = ycfkTwomanagePage.getResult();
		if(null != ycfkTwomanage_list && ycfkTwomanage_list.size() > 0){
			for (YcfkTwomanage y : ycfkTwomanage_list) {
				Timestamp feedBackDate = y.getFeedBackDate();
				Timestamp completionDate = y.getCompletionDate();
				if(null != feedBackDate){
					if(null == completionDate){
						completionDate = new Timestamp(System.currentTimeMillis());
					}
					Double outDate = HwmReportService.countTimeoutDate(feedBackDate, completionDate);
					y.setTimeoutDate((outDate >= 1)? (outDate -1) : 0);
				}
			}
		}
		
		return ycfkTwomanagePage;
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(YcfkTwomanage ycfkTwomanage, HttpServletRequest request,HttpServletResponse response, String userId) throws IOException {
		List<String> roleIds = userRoleMgtMapper.getUserRoleIdListByUId(userId);
		if(roleIds != null && roleIds.size()>0 && null != ycfkTwomanage.getCurrentSite()){
			if(roleIds.indexOf("25") > -1 || roleIds.indexOf("96") > -1){
				//管理员可以查看所有数据
				ycfkTwomanage.setFollowupPeople(ycfkTwomanage.getFollowupPeople());
			} else {
				// 只能查看自己跟进的数据	
				ycfkTwomanage.setFollowupPeople(ycfkTwomanage.getCurrLoginer());
			}
		}
		
		List<YcfkTwomanage> ycfkTwomanage_list = ycfkTwomanageMapper.queryYcfkTwomanageListPage(ycfkTwomanage);
		//导出的措施列表不显示发送到XX工站
		if(null != ycfkTwomanage_list && ycfkTwomanage_list.size() > 0){
			for (int i = 0; i < ycfkTwomanage_list.size(); i++) {
				StringBuilder sb = new StringBuilder();
				String measures = ycfkTwomanage_list.get(i).getMeasures();
				if(null != measures){
					String[] m = measures.split("<br><br>");
					for (String s : m) {
						if(s.indexOf("发送数据到（") < 0){
							StringBuilder sb1 = new StringBuilder();
							sb1.append("<span>")
							.append(s.substring(s.indexOf("【"),s.lastIndexOf("|")-1))
							.append(s.substring(s.indexOf("|") +2, s.lastIndexOf("【")))
							.append(" | ")
							.append(s.substring(s.indexOf("<span>") + 6,s.indexOf("|")+1))
							.append(s.substring(s.lastIndexOf("|") +1, s.lastIndexOf("|") +18));
							sb.append(sb1).append("<br><br>");
						}
					}
					ycfkTwomanage_list.get(i).setMeasures(sb.toString());
				}
				
				Timestamp feedBackDate = ycfkTwomanage_list.get(i).getFeedBackDate();
				Timestamp completionDate = ycfkTwomanage_list.get(i).getCompletionDate();
				if(null != feedBackDate){
					if(null == completionDate){
						completionDate = new Timestamp(System.currentTimeMillis());
					}
					Double outDate = HwmReportService.countTimeoutDate(feedBackDate, completionDate);
					ycfkTwomanage_list.get(i).setTimeoutDate((outDate >= 1)? (outDate -1) : 0);
				}
				ycfkTwomanage_list.get(i).setSortId(i+1);
			}
		}
		//导出时间
		 Date date = new Date();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");       
	     String exportDate = sdf.format(date).toString();
	     String[] fieldNames = new String[] {"序号","日期","跟进人","所在工站","紧急度","问题严重程度","状态","机型","IMEI","客户名称","联系电话","保内","数量","反馈人","问题描述","原因分析","责任单位","责任人","处理措施","库存品处理措施","验收结果","验收人","抄送人","未完成时间(天)","完成日期","备注"};
	     if(null !=ycfkTwomanage.getYcfk_completionState()){
	    	 if(ycfkTwomanage.getYcfk_completionState() == 1){
	    		 fieldNames = new String[] {"序号","日期","跟进人","所在工站","紧急度","问题严重程度","状态","机型","IMEI","客户名称","联系电话","保内","数量","反馈人","问题描述","原因分析","责任单位","责任人","处理措施","库存品处理措施","验收结果","验收人","抄送人","完成时间(天)","完成日期","备注"};
	    	 }
	     }
		Map map = new HashMap();
		map.put("size", ycfkTwomanage_list.size()+2);
		map.put("peList",ycfkTwomanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length); 
		map.put("exportDate", exportDate);
		String fileName     = new StringBuilder("客诉问题反馈跟进表--")
									.append(exportDate)
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
									.append("YcfkTwomanage.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	public List<YcfkTwomanage> queryList(YcfkTwomanage ycfkTwomanage){
		return ycfkTwomanageMapper.queryList(ycfkTwomanage);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<YcfkTwomanage> successList = new ArrayList<YcfkTwomanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						YcfkTwomanage s = new YcfkTwomanage();

						// YcfkTwomanage对象的属性值{model[0]、imei[1]、warranty[2]、description[3]、number[4]、feedBackPerson[5]、feedBackDate[6]、recipient[7]、responsibilityUnit[8]、
						//measures[9]、completionState[10]、completionDate[11]、followupPeople[12]、customerName[13]、phone[14]、remak[15]}
						Object[] obj = new Object[16];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[3]) || StringUtils.isBlank((String)obj[10])) {
									throw new RuntimeException();
								} else {
									s.setModel((String)obj[0]);
									s.setImei((String) obj[1]);
									if(!StringUtils.isBlank((String)obj[2])){
										if(((String)obj[2]).equals("保内")){
											s.setWarranty(0);
										}else if(((String)obj[2]).equals("保外")){
											s.setWarranty(1);
										}
									}
									s.setDescription((String)obj[3]);
									if(!StringUtils.isBlank((String)obj[4])){
										s.setNumber(Integer.valueOf((String)obj[4]));
									}
									s.setFeedBackPerson((String)obj[5]);
									if(StringUtils.isBlank((String)obj[6])){
										s.setFeedBackDate(new Timestamp(System.currentTimeMillis()));
									}else{
										s.setFeedBackDate(DateUtil.getTimestampByStr((String)obj[6]));
									}
									s.setRecipient((String)obj[7]);
									s.setResponsibilityUnit((String)obj[8]);
//									处理措施列表暂不使用									
									s.setMeasures(generateMeasuresTxt(request, (String)obj[9], ""));
									
									if("已完成".equals(((String)obj[10]).trim())){
										s.setCompletionState(1);
										if(!StringUtils.isBlank((String)obj[11])){
											s.setCompletionDate(DateUtil.getTimestampByStr((String)obj[11]));
										}else{
											s.setCompletionDate(new Timestamp(System.currentTimeMillis()));
										}
									}else{
										s.setCompletionState(0);
										s.setCompletionDate(null);
									}
									s.setFollowupPeople((String)obj[12]);
									s.setCustomerName((String)obj[13]);
									s.setPhone((String)obj[14]);
									s.setRemak((String)obj[15]);
									if(isExists(s)==0){
										try {
											ycfkTwomanageMapper.insert(s);
											successList.add(s);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else{
										repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行数据已存在,未导入,请检查......");
									}
								}
							} catch (Exception e) {
								if (errorList.size() < 1000) {// 最多保存1000条错误数据
									errorList.add((errorList.size() + 1) + ":第" + (j + 1) + "行导入错误,有必填数据未填写......");
								}
							}
						}
					}
					if (errorList.size() < 1) {

						if (successList.size() > 0) {
							errorList.add("导入成功！！！");
						}else{
							errorList.add("可导入数据已全部存在！！！");
						}
						if(repeatList.size()>0){
							errorList.addAll(repeatList);
						}
					} else {
						errorList.add(0, "导入失败.......");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return errorList;
	}
	
	//*****************************异常反馈管理 START*************************************************//
	
	/**
	 * 异常反馈管理  发送数据到下一工站
	 * @author TangYuping
	 * @version 2017年3月9日 下午2:05:01
	 * @param ycfk
	 * @param ids
	 * @param currentSite
	 * @return
	 */
	public boolean sendData (YcfkTwomanage ycfk, String idsemp, HttpServletRequest req) {
		String[] ids = idsemp.split(",");
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());		
		if (null != ids && ids.length > 0) {
			// 发送时要拼接 处理措施，多条数据发送，循环修改
			for (String id : ids ) {								
				 ycfk.setBackId(Integer.valueOf(id));
				 YcfkTwomanage old = this.select(ycfk);
				 String currentSite = old.getCurrentSite();
				String nextPerson = ycfk.getFollowupPeople();
				//如果是发送到同工站则不改变状态
				if(!ycfk.getCurrentSite().equals(ycfk.getNextSite()))
					this.flushCurrentSite(ycfk);
				//可以发送到同一工作站
				if ("customer".equals(ycfk.getNextSite())) {
					ycfk.setCurrentSite("客服");
					ycfk.setCustomerStatus(0);
					ycfk.setCustomerPerson(nextPerson);
					ycfk.setCustomerCreateTime(currentTime);
				}else if ("terminal".equals(ycfk.getNextSite())) {
					//终端技术   <-- 客服/平台/维修
					ycfk.setCurrentSite("终端技术");
					ycfk.setTermailStatus(0);
					ycfk.setTermailPerson(nextPerson);
					ycfk.setTermailCreateTime(currentTime);
				} else if ("platform".equals(ycfk.getNextSite())) {
					//平台技术  <-- 客服/终端
					ycfk.setCurrentSite("平台技术");
					ycfk.setPlatformStatus(0);
					ycfk.setPlatformPerson(nextPerson);
					ycfk.setPlatformCreateTime(currentTime);
				} else if ("quality".equals(ycfk.getNextSite())) {
					//品质部  <-- 客服/终端/平台
					ycfk.setCurrentSite("品质部");
					ycfk.setQualityStatus(0);
					ycfk.setQualityPerson(nextPerson);
					ycfk.setQualityCreateTime(currentTime);
				} else if ("product".equals(ycfk.getNextSite())) {
					//产品规划部   <-- 客服/终端/平台/品质				
					ycfk.setCurrentSite("产品规划部");
					ycfk.setProductStatus(0);
					ycfk.setProductPerson(nextPerson);
					ycfk.setProductCreateTime(currentTime);
				} else if ("repair".equals(ycfk.getNextSite())) {
					//维修  <-- 客服
					ycfk.setCurrentSite("维修");
					ycfk.setRepairStatus(0);
					ycfk.setRepairPerson(nextPerson);
					ycfk.setRepairCreateTime(currentTime);
				} else if ("project".equals(ycfk.getNextSite())) {
					//项目 <-- 客服/终端/平台/品质
					ycfk.setCurrentSite("项目部");
					ycfk.setProjectStatus(0);
					ycfk.setProjectPerson(nextPerson);
					ycfk.setProjectCreateTime(currentTime);
				} else if ("jimi".equals(ycfk.getNextSite())) {
					//几米 <-- 客服/终端/平台/品质
					ycfk.setCurrentSite("几米");
					ycfk.setJimiStatus(0);
					ycfk.setJimiPerson(nextPerson);
					ycfk.setJimiCreateTime(currentTime);
				} else if ("market".equals(ycfk.getNextSite())) {
					//市场 <-- 客服/终端/平台/品质
					ycfk.setCurrentSite("市场");
					ycfk.setMarketStatus(0);
					ycfk.setMarketPerson(nextPerson);
					ycfk.setMarketCreateTime(currentTime);
				} else if ("research".equals(ycfk.getNextSite())) {
					//研发 <-- 客服/终端/平台/品质
					ycfk.setCurrentSite("研发");
					ycfk.setResearchStatus(0);
					ycfk.setResearchPerson(nextPerson);
					ycfk.setResearchCreateTime(currentTime);
				} else if ("test".equals(ycfk.getNextSite())) {
					//测试 <-- 客服/终端/平台/品质
					ycfk.setCurrentSite("测试");
					ycfk.setTestStatus(0);
					ycfk.setTestPerson(nextPerson);
					ycfk.setTestCreateTime(currentTime);
				} else if ("iot".equals(ycfk.getNextSite())) {
					//物联网卡 <-- 客服/终端/平台/品质
					ycfk.setCurrentSite("物联网卡");
					ycfk.setIotStatus(0);
					ycfk.setIotPerson(nextPerson);
					ycfk.setIotCreateTime(currentTime);
				} else if ("sim".equals(ycfk.getNextSite())) {
					//SIM卡平台 <-- 客服/终端/平台/品质
					ycfk.setCurrentSite("SIM卡平台");
					ycfk.setSimStatus(0);
					ycfk.setSimPerson(nextPerson);
					ycfk.setSimCreateTime(currentTime);
				} 
				String measures = old.getMeasures() + this.generateMeasuresTxt(req, "发送数据到（"+ycfk.getCurrentSite()+"）", currentSite);
				ycfk.setMeasures(measures);
				ycfkTwomanageMapper.sendDataToNextSite(ycfk, id);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据当前发送工站设置设备在此工站中的状态为已完成
	 * @author TangYuping
	 * @version 2017年3月9日 下午2:46:34
	 * @param ycfk
	 */
	public void flushCurrentSite(YcfkTwomanage ycfk) {
		if ("customer".equals(ycfk.getCurrentSite())) {
			ycfk.setCustomerStatus(1);
		} else if ("termail".equals(ycfk.getCurrentSite())) {
			ycfk.setTermailStatus(1);
		}else if ("platform".equals(ycfk.getCurrentSite())) {
			ycfk.setPlatformStatus(1);
		} else if ("quality".equals(ycfk.getCurrentSite())) {
			ycfk.setQualityStatus(1);
		} else if ("product".equals(ycfk.getCurrentSite())) {
			ycfk.setProductStatus(1);
		} else if ("repair".equals(ycfk.getCurrentSite())) {
			ycfk.setRepairStatus(1);
		} else if ("project".equals(ycfk.getCurrentSite())) {
			ycfk.setProjectStatus(1);
		} else if ("jimi".equals(ycfk.getCurrentSite())) {
			ycfk.setJimiStatus(1);
		} else if ("market".equals(ycfk.getCurrentSite())) {
			ycfk.setMarketStatus(1);
		} else if ("research".equals(ycfk.getCurrentSite())) {
			ycfk.setResearchStatus(1);
		} else if ("test".equals(ycfk.getCurrentSite())) {
			ycfk.setTestStatus(1);
		} else if ("iot".equals(ycfk.getCurrentSite())) {
			ycfk.setIotStatus(1);
		} else if ("sim".equals(ycfk.getCurrentSite())) {
			ycfk.setSimStatus(1);
		}  
	}
	
	/**
	 * 添加，修改时 根据前台访问工站设置设备当前所在工站
	 * @author TangYuping
	 * @version 2017年3月10日 上午11:42:29
	 * @param ycfkTwomanage
	 */
	public void setCurrentSite (YcfkTwomanage ycfkTwomanage) {
		String currentFeedbackPerson = ycfkTwomanage.getFeedBackPerson();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if ("customer".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("客服");
			ycfkTwomanage.setCustomerPerson(currentFeedbackPerson);
			ycfkTwomanage.setCustomerCreateTime(currentTime);
		} else if ("termail".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("终端技术");
			ycfkTwomanage.setTermailPerson(currentFeedbackPerson);
			ycfkTwomanage.setTermailCreateTime(currentTime);
		} else if ("platform".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("平台技术");
			ycfkTwomanage.setPlatformPerson(currentFeedbackPerson);
			ycfkTwomanage.setPlatformCreateTime(currentTime);
		} else if ("repair".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("维修");
			ycfkTwomanage.setRepairPerson(currentFeedbackPerson);
			ycfkTwomanage.setRepairCreateTime(currentTime);
		} else if ("project".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("项目部");
		} else if ("quality".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("品质部");
		} else if ("product".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("产品规划部");
		} else if ("jimi".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("几米");
		} else if ("market".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("市场");
		} else if ("research".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("研发");
		} else if ("test".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("测试");
		} else if ("iot".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("物联网卡");
		} else if ("sim".equals(ycfkTwomanage.getCurrentSite())) {
			ycfkTwomanage.setCurrentSite("SIM卡平台");
		}
		
	}
	//*****************************异常反馈管理 END*************************************************//
	
//	处理措施列表	
	public String generateMeasuresTxt(HttpServletRequest req, String measures, String currentSite){
			StringBuffer sb = new StringBuffer("<span>");
			sb.append(measures);
			UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
			sb.append(" | " + user.getuName() + "【"+currentSite+"】");
			sb.append(" | " + DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
			sb.append("</span><br><br>");
			return sb.toString();
	}
	
	/**
	 * 异常反馈下载附件
	 * @author TangYuping
	 * @version 2017年4月14日 下午2:18:52
	 * @param file
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	public void downLoadFile (YcfkManageFile file, HttpServletRequest req,
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
	public void deleteFileByFid(YcfkManageFile file) throws Exception{
		if (null != file.getFid() && file.getFid() > 0) {
			if (null != file.getFid() && file.getFid() > 0) {
				ycfkTwomanageMapper.deleteFileByFid(file.getFid());
			}			
		} else {
			throw new Exception("无法获取ID");
		}
	}

	/**
	 * 发送下一工站后发送邮件
	 * 经过发送下一站的转换后，currentSite存的就是发送后的工站，
	 * @param ycfkTwomanage 包括下一
	 */
	public boolean sendMail(YcfkTwomanage ycfkTwomanage) {
		try {
			//获取跟进人员
			List<String> follows = ycfkTwomanage.getFollows();
			//获取跟进人员邮箱
			for (String uName : follows) {
				StringBuilder builder = new StringBuilder();
				//获取邮箱
				String mail = userService.getMailByUname(uName);
				//获取待处理数,查询条件是工站和姓名，以及完成状态
				YcfkTwomanage tmp  = new YcfkTwomanage();
				tmp.setFollowupPeople(uName);
				tmp.setCompletionState(0);
				tmp.setCurrentSite(transferSite(ycfkTwomanage.getCurrentSite()));
				List<YcfkTwomanage> notComplete = ycfkTwomanageMapper.selectNotCompleteByUname(tmp);

				builder.append("<html><head><title>通知邮件</title><meta charset=\"utf-8\"/></head>")
						.append("<body> <div> Dear ").append(uName).append(": 您在售后系统有")
						.append("<span style=\"color:red\">").append(notComplete.size()).append("</span>")
						.append("项工作需要处理");
				//获取今日超期工作数据量
				int outDateNum = 0;
				for (YcfkTwomanage ycfk : notComplete) {
					Double outDate = HwmReportService.countTimeoutDate(ycfk.getFeedBackDate(), new Date());
					//一般问题3天，重要问题2天，严重问题1天，重大问题1天
					Integer severityFlag = ycfk.getSeverityFlag();
					int outDateTody = (outDate >= 1) ? (int) (outDate - 1) : 0;
					if(severityFlag != null ) {
						if (severityFlag == 0 && outDateTody == 2) {
							outDateNum++;
						} else if (severityFlag == 1 && outDateTody == 1) {
							outDateNum++;
						} else if (severityFlag == 2 && outDateTody == 0) {
							outDateNum++;
						} else if (severityFlag == 3 && outDateTody == 0) {
							outDateNum++;
						}
					}
				}
				if(outDateNum >0)
					builder.append(",其中")
							.append("<span style=\"color:red\">").append(outDateNum).append("</span>")
							.append("项今天超期");
				//最终的邮件内容
				builder.append("。");
				builder.append("<a href=\"http://www.concox400.com/sale-web\">前去处理</a>")
						.append("</div></body></html>");
				System.out.println("邮件内容:"+builder.toString());
				//发送邮件
				CVMailUtil.sendMailText(CVConfigUtil.get("business-send-email"), mail, "售后系统客诉问题提醒", builder.toString());
				log.info("发送邮件给"+uName+"成功");
			}
			return true;
		}catch (MessagingException | UnsupportedEncodingException e){
			log.error("发送邮件失败",e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 发送邮件给抄送人
	 * @param ids
	 * @return
	 */
	public boolean sendMailCopy(String  ids) {
		try {
			//不要重复发送，太多了
			Set<String> hasSend = new HashSet<>();
			String[] split = ids.split(",");
			for(int i=0;i<split.length;i++) {
				YcfkTwomanage temp = new YcfkTwomanage();
				temp.setBackId(Integer.parseInt(split[i]));
				YcfkTwomanage ycfkTwomanage = this.select(temp);
				if(ycfkTwomanage.getCopyPerson()==null || "".equals(ycfkTwomanage.getCopyPerson())){
					continue;
				}
				ycfkTwomanage.setCopyPersonArray(Arrays.asList(ycfkTwomanage.getCopyPerson().split(",")));
				//获取抄送人员
				List<String> copyPersonArray = ycfkTwomanage.getCopyPersonArray();
				//获取抄送人员邮箱
				for (String uName : copyPersonArray) {
					if(hasSend.contains(uName)){
						continue;
					}
					hasSend.add(uName);
					StringBuilder builder = new StringBuilder();
					//获取邮箱
					String mail = userService.getMailByUname(uName);
					//获取待处理数,查询条件是姓名，以及完成状态
					YcfkTwomanage tmp = new YcfkTwomanage();
					tmp.setCopyPerson(uName);
					tmp.setCompletionState(0);

					List<YcfkTwomanage> notComplete = ycfkTwomanageMapper.selectNotCompleteByUnameCopy(tmp);

					builder.append("<html><head><title>通知邮件</title><meta charset=\"utf-8\"/></head>")
							.append("<body> <div> Dear ").append(uName).append(": 在售后系统有")
							.append("<span style=\"color:red\">").append(notComplete.size()).append("</span>")
							.append("项工作抄送给您");
					//获取今日超期工作数据量
					int outDateNum = 0;
					for (YcfkTwomanage ycfk : notComplete) {
						Double outDate = HwmReportService.countTimeoutDate(ycfk.getFeedBackDate(), new Date());
						//一般问题3天，重要问题2天，严重问题1天，重大问题1天
						Integer severityFlag = ycfk.getSeverityFlag();
						int outDateTody = (outDate >= 1) ? (int) (outDate - 1) : 0;
						if (severityFlag != null) {
							if (severityFlag == 0 && outDateTody == 2) {
								outDateNum++;
							} else if (severityFlag == 1 && outDateTody == 1) {
								outDateNum++;
							} else if (severityFlag == 2 && outDateTody == 0) {
								outDateNum++;
							} else if (severityFlag == 3 && outDateTody == 0) {
								outDateNum++;
							}
						}
					}
					if (outDateNum > 0)
						builder.append(",其中")
								.append("<span style=\"color:red\">").append(outDateNum).append("</span>")
								.append("项今天超期");
					//最终的邮件内容
					builder.append("。");
					builder.append("<a href=\"http://www.concox400.com/sale-web\">前去查看</a>")
							.append("</div></body></html>");
					System.out.println("邮件内容:" + builder.toString());
					//发送邮件
					CVMailUtil.sendMailText(CVConfigUtil.get("business-send-email"), mail, "售后系统客诉问题提醒", builder.toString());
					log.info("发送邮件给" + uName + "成功");
				}
			}
			return true;
		}catch (MessagingException | UnsupportedEncodingException e){
			log.error("发送邮件失败",e);
			e.printStackTrace();
			return false;
		}
	}

	public String transferSite(String site){
		//可以发送到同一工作站
		if ("客服".equals(site)) {
			return "customer";
		}else if ("终端技术".equals(site)) {
			return "terminal";
		} else if ("平台技术".equals(site)) {
			return "platform";
		} else if ("品质部".equals(site) ){
			return "quality";
		} else if ("产品规划部".equals(site) ){
			//产品规划部   <-- 客服/终端/平台/品质
			return "product";
		} else if ("维修".equals(site)) {
			return "repair";
		} else if ("项目部".equals(site)) {
			return "project";
		} else if ("几米".equals(site)) {
			return "jimi";
		} else if ("市场".equals(site)) {
			return "market";
		} else if ("研发".equals(site)) {
			return "research";
		} else if ("测试".equals(site)) {
			return "test";
		} else if ("物联网卡".equals(site)) {
			return "iot";
		} else if ("SIM卡平台".equals(site)) {
			return "sim";
		}
		return null;
	}
}
