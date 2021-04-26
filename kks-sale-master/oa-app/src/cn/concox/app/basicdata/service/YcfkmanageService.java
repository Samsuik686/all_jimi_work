package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.mapper.YcfkmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Ycfkmanage;
import cn.concox.wechat.uti.pay.DateUtil;
import freemarker.template.Template;

/**
 * <pre>
 * 异常管理业务类
 * </pre>
 * @author Liao.bifeng 
 * @version v1.0
 */
@Service("ycfkmanageService")
@Scope("prototype")
public class YcfkmanageService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="ycfkmanageMapper")
	private  YcfkmanageMapper<Ycfkmanage> ycfkmanageMapper;
	
	public Ycfkmanage select(Ycfkmanage ycfkmanage){
		return ycfkmanageMapper.select(ycfkmanage);
	}
	
	public void update(Ycfkmanage ycfkmanage){
		ycfkmanageMapper.update(ycfkmanage);
	}
	
	public void detele(Ycfkmanage ycfkmanage){
		ycfkmanageMapper.delete(ycfkmanage);
	}
	
	public void insert(Ycfkmanage ycfkmanage){
		ycfkmanageMapper.insert(ycfkmanage);
	}
	
	public int isExists(Ycfkmanage ycfkmanage){
		return ycfkmanageMapper.isExists(ycfkmanage);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Ycfkmanage> getYcfkmanageListPage(Ycfkmanage ycfkmanage,int currentPage, int pageSize){
		Page<Ycfkmanage> ycfkmanagePage = new Page<Ycfkmanage>();
		ycfkmanagePage.setCurrentPage(currentPage);
		ycfkmanagePage.setSize(pageSize);
		ycfkmanagePage =ycfkmanageMapper.queryYcfkmanageListPage(ycfkmanagePage,ycfkmanage);
		return ycfkmanagePage;
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(Ycfkmanage ycfkmanage, HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Ycfkmanage> ycfkmanage_list = ycfkmanageMapper.queryList(ycfkmanage);
		
		String[] fieldNames = new String[] {"销售机型  ","IMEI","问题描述 ","反馈日期 ","责任人 ","责任单位 ","处理措施","状态","完成时间","客户姓名 ","联系电话 ","备注 "};
		Map map = new HashMap();
		map.put("size", ycfkmanage_list.size()+2);
		map.put("peList",ycfkmanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("400电话记录管理列表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
									.append("Ycfkmanage.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	public List<Ycfkmanage> queryList(Ycfkmanage ycfkmanage){
		return ycfkmanageMapper.queryList(ycfkmanage);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Ycfkmanage> successList = new ArrayList<Ycfkmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Ycfkmanage s = new Ycfkmanage();

						// Ycfkmanage对象的属性值{model[0]、imei[1]、description[2]、feedBackDate[3]、recipient[4]、responsibilityUnit[5]、
						//measures[6]、CompletionState[7]、completionDate[8]、customerName[9]、phone[10]、remak[11]}
						Object[] obj = new Object[12];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[2]) || StringUtils.isBlank((String)obj[7])) {
									throw new RuntimeException();
								} else {
									s.setModel((String)obj[0]);
									s.setImei((String) obj[1]);
									s.setDescription((String)obj[2]);
									if(StringUtils.isBlank((String)obj[3])){
										s.setFeedBackDate(new Timestamp(System.currentTimeMillis()));
									}else{
										s.setFeedBackDate(DateUtil.getTimestampByStr((String)obj[3]));
									}
									s.setRecipient((String)obj[4]);
									s.setResponsibilityUnit((String)obj[5]);
//									处理措施列表暂不使用									
//									s.setMeasures(generateMeasuresTxt(request,(String)obj[9]));
									s.setMeasures((String)obj[6]);
									
									if("已完成".equals(((String)obj[7]).trim())){
										s.setCompletionState(1);
										if(!StringUtils.isBlank((String)obj[8])){
											s.setCompletionDate(DateUtil.getTimestampByStr((String)obj[8]));
										}else{
											s.setCompletionDate(new Timestamp(System.currentTimeMillis()));
										}
									}else{
										s.setCompletionState(0);
										s.setCompletionDate(null);
									}
									
									s.setCustomerName((String)obj[9]);
									s.setPhone((String)obj[10]);
									s.setRemak((String)obj[11]);
									if(isExists(s)==0){
										try {
											ycfkmanageMapper.insert(s);
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
	
//	处理措施列表暂不使用	
	private String generateMeasuresTxt(HttpServletRequest req,String measures){
			StringBuffer sb = new StringBuffer("<span>");
			sb.append(measures);
			UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
			sb.append(" | " + user.getuName());
			sb.append(" | " + DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
			sb.append("</span><br>");
			return sb.toString();
	}
	
	/**
	 * 400电话记录分类统计报表 
	 * @param ycfk
	 * @return
	 */
	public List<Ycfkmanage> ycfkCountList(Ycfkmanage ycfk){
		return ycfkmanageMapper.ycfkCountList(ycfk);
	}
	
	/**
	 * 导出400电话记录分类统计报表 
	 * @param ycfk
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ycfkExportDatas(Ycfkmanage ycfkmanage, HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Ycfkmanage> ycfkmanage_list = ycfkmanageMapper.ycfkCountList(ycfkmanage);
		String[] fieldNames = new String[] {"问题描述 ","处理方法","数量","百分比","原因分析","解决措施","责任人","跟进人","预计完成时间","实际完成时间","状态(OPEN/OFF)"};
		Map map = new HashMap();
		map.put("size", ycfkmanage_list.size()+2);
		map.put("peList",ycfkmanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("400电话记录分类统计报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("YcfkCountList.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
}
