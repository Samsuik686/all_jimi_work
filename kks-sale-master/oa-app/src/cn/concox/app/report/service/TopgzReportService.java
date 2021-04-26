package cn.concox.app.report.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.report.mapper.TopgzReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.report.FgzReport;
import freemarker.template.Template;

@Service("topgzReportService")
@Scope("prototype")
public class TopgzReportService {
	

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="topgzReportMapper")
	private  TopgzReportMapper<FgzReport> topgzReportMapper;

	/**
	 * 各机型故障报表
	 * 
	 * @param 	ZgzReport 			报表对象
	 * @return  List<ZgzReport>  	数据集合
	 */
	public List<FgzReport> everyBreakdownList(FgzReport report) {
	    // 已发货
	    if(report.getIsSend() == Boolean.TRUE) {
	        return topgzReportMapper.everyBreakdownList(report);
	    }
	    // 未发货
	    return topgzReportMapper.notSendEveryBreakdownList(report);
	}
	
	/**
	 * 导出数据
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(List<FgzReport> reports, HttpServletRequest request,HttpServletResponse response, FgzReport report) throws IOException { 		
		String isWarry = report.getIsWarranty();
		String strWarry = "";
		if(isWarry.equals("0")){
			strWarry = "保内";
		}else if(isWarry.equals("1")){
			strWarry = "保外";
		}
		String startTime = report.getStartTime(),
				endTime = report.getEndTime();
		StringBuilder dispaly = new StringBuilder();
		boolean stNull = (startTime == null || "".equals(startTime)),etNull = (endTime == null || "".equals(endTime));
		if(!stNull && !etNull){
			dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		if(stNull && !etNull){
			 dispaly.append("(--").append(endTime).append(")");
		}
		if(!stNull && etNull){
			 Date date = new Date();
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");       
		     endTime = sdf.format(date).toString();
		     dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		String[] fieldNames = new String[] {"主板型号 ","故障分类 ","故障次数","百分比 ","原因分析 ","改善措施 ","完成时间 ","责任单位 ","负责人 ","跟进人 ","状态 ","备注 "};
		Map map = new HashMap();
		map.put("dispalyDate", dispaly);
		map.put("isWarry", strWarry);
		map.put("size", reports.size()+2);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder(strWarry+"各机型故障分类报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("Topgz.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	/**
	 * 导出各机型前5项故障报表数据
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void top5OfModelsExportDatas(List<FgzReport> reports, HttpServletRequest request,HttpServletResponse response, FgzReport report) throws IOException { 
		String[] fieldNames = new String[] {"主板型号 ","故障分类 ","故障次数","百分比 ","原因分析 ","改善措施 ","完成时间 ","责任单位 ","负责人 ","跟进人 ","状态 ","备注 "};
		String isWarry = report.getIsWarranty();
		String strWarry = "";
		if(isWarry.equals("0")){
			strWarry = "保内";
		}else if(isWarry.equals("1")){
			strWarry = "保外";
		}
		Map map = new HashMap();
		map.put("isWarry", strWarry);
		map.put("size", reports.size()+2);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder(strWarry+"各机型前5项故障报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("Top5OfModelsReport.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	/**
	 * 各机型所有故障报表
	 * 
	 * @param 	ZgzReport 			报表对象
	 * @return  List<ZgzReport>  	数据集合
	 */
	public List<FgzReport> getAllOfModelsList(FgzReport report) {
		return topgzReportMapper.getAllOfModelsList(report);
	}
	
	/**
	 * 导出各机型所有故障报表数据
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void allOfModelsExportDatas(List<FgzReport> reports, HttpServletRequest request,HttpServletResponse response, FgzReport report) throws IOException { 
		String[] fieldNames = new String[] {"主板型号 ","故障分类 ","故障次数","百分比 ","原因分析 ","改善措施 ","完成时间 ","责任单位 ","负责人 ","跟进人 ","状态 ","备注 "};
		String isWarry = report.getIsWarranty();
		String strWarry = "";
		if(isWarry.equals("0")){
			strWarry = "保内";
		}else if(isWarry.equals("1")){
			strWarry = "保外";
		}
		Map map = new HashMap();
		map.put("isWarry", strWarry);
		map.put("size", reports.size()+2);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder(strWarry+"各机型所有故障报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("AllOfModelsReport.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}


}
