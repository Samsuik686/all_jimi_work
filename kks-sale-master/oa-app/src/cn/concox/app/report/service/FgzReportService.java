package cn.concox.app.report.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.ZbxhmanageMapper;
import cn.concox.app.report.mapper.FgzReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.basicdata.Zbxhmanage;
import cn.concox.vo.report.FgzReport;
import freemarker.template.Template;


@Service("fgzreporteService")
@Scope("prototype")
public class FgzReportService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="fgzReportMapper")
	private  FgzReportMapper<FgzReport> fgzReportMapper;
	
	@Resource(name = "zbxhmanageMapper")
	private ZbxhmanageMapper<Zbxhmanage> zbxhmanageMapper;
	
	/**
	 * 查出所有机型
	 * @return
	 */
	public List<Zbxhmanage> getMarketModelList() {
		return zbxhmanageMapper.queryModelList();
	}
	
	/**
	 * [Material]
	 * 保内各机型故障分类报表数据源
	 * 
	 * @param 	ZgzReport 			报表对象
	 * @return  List<ZgzReport>  	数据集合
	 */
	public List<FgzReport> getBreakdownList(FgzReport report) {
		return fgzReportMapper.fenBreakdownList(report);
	}
	
	/**
	 * 导出数据
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(FgzReport report, HttpServletRequest request,HttpServletResponse response) throws IOException { 
		List<FgzReport> reports = fgzReportMapper.fenBreakdownList(report);
		
		String[] fieldNames = new String[] {"主板型号","故障分类 ","数量 ","百分比 "};
		Map map = new HashMap();
		map.put("size", reports.size()+2);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("保内各机型故障分类报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("FgzQuote.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	

}
