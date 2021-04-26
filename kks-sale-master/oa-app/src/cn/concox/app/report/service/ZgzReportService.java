package cn.concox.app.report.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.BasegroupMapper;
import cn.concox.app.report.mapper.ZgzReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.report.ZgzReport;
import freemarker.template.Template;

@Service("zgzreporteService")
@Scope("prototype")
public class ZgzReportService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="zgzReportMapper")
	private  ZgzReportMapper<ZgzReport> zgzReportMapper;
	
	@Resource(name="basegroupMapper")
	private BasegroupMapper<Basegroup> basegroupMapper;
	
	/**
	 * [Material]
	 * 保内故障总分类报表数据源
	 * 
	 * @param 	ZgzReport 			报表对象
	 * @return  List<ZgzReport>  	数据集合
	 */
	public List<ZgzReport> getBreakdownList(ZgzReport report) {
		List<ZgzReport> reportList = null;
			List<ZgzReport> newReportList = zgzReportMapper.getBreakdownChart(report);
			if(newReportList !=null && newReportList.size() > 0){
				reportList = new ArrayList<ZgzReport>();
				ZgzReport reportCount = new ZgzReport();
				int count = 0;
				//查询无数据时，导出异常，且图表悬停显示undefinded,把值设为0可解决
				for (ZgzReport z : newReportList) {
					if(!StringUtils.isBlank(z.getNumber()) && !"0".equals(z.getNumber())){
						reportList.add(z);
						count += Integer.parseInt(z.getNumber());
					}
				}
				reportCount.setNumber(String.valueOf(count));
				reportCount.setFaultType("总计");
				reportList.add(reportCount);
			}
		
		return reportList;
	}
	
	/**
	 * 导出数据
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(ZgzReport report, HttpServletRequest request,HttpServletResponse response) throws IOException { 
		List<ZgzReport> reports = getBreakdownList(report);
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
		String[] fieldNames = new String[] {"故障分类 ","故障次数","百分比 "};
		Map map = new HashMap();
		map.put("dispalyDate", dispaly);
		map.put("isWarry", strWarry);
		map.put("size", reports.size()+2);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder(strWarry+"故障总分类报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("ZgzQuote.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

}
