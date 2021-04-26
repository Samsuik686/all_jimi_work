package cn.concox.app.report.service;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.report.mapper.BadrateReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.report.BadRateReport;
import freemarker.template.Template;

@Service("badRateReportService")
@Scope("prototype")
public class BadRateReportService {
	

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="badrateReportMapper")
	private  BadrateReportMapper<BadRateReport> badrateReportMapper;
	
	

	/**
	 * [Material]
	 * 机器不良率报表
	 * 
	 * @param 	ZgzReport 			报表对象
	 * @return  List<ZgzReport>  	数据集合
	 */
	public List<BadRateReport> badrateList(BadRateReport report) {
		List<BadRateReport> reports = badrateReportMapper.badrateList(report);
		BadRateReport reports2 = new BadRateReport();
		Integer sumRepairs = 0;
		Long sumGoods = 0L;
		String sumPercent = "";
		if(null != reports && reports.size()>0){
			for (BadRateReport br:reports) {					
				if (null != br.getRepairs() && br.getRepairs() > 0) {
					sumRepairs += br.getRepairs();
				} else {
					sumRepairs += 0;
				}
				if (null != br.getGoods() && br.getGoods() > 0) {
					sumGoods += br.getGoods();
				} else {
					sumGoods += 0;
				}
			}	
			if (sumRepairs > 0 && sumGoods >0) {
				NumberFormat df = NumberFormat.getInstance();
				// 设置精确到小数点后2位 			  
				df.setMaximumFractionDigits(2); 
				sumPercent  = df.format((float)sumRepairs / (float)sumGoods * 100)+"%"; 					
			}				
			reports2.setModel("合计");
			reports2.setRepairs(sumRepairs);
			reports2.setGoods(sumGoods);
			reports2.setPercent(sumPercent);
			reports.add(reports2);
		}
		
		return reports;
	}
	
	
	/**
	 * 导出数据
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(List<BadRateReport> reports, HttpServletRequest request,HttpServletResponse response,BadRateReport report) throws IOException { 
		
		String isWarry = report.getIsWarranty();
		String strWarry = "";
		if(isWarry.equals("0")){
			strWarry = "保内";
		}else if(isWarry.equals("1")){
			strWarry = "保外";
		}
		String[] fieldNames = new String[] {"主板型号 ","返修机数量 ","出货总数 ","总返修比率 "};
		Map map = new HashMap();
		map.put("isWarry", strWarry);
		map.put("size", reports.size()+2);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder(strWarry+"机器不良率报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("Badrate.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}


}
