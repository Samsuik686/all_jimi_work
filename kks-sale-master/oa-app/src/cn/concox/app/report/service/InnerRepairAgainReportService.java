/*
 * Created: 2016-08-19
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.app.report.service;
import java.io.IOException;
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

import cn.concox.app.report.mapper.InnerRepairAgainReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.report.InnerRepairAgainReport;
import freemarker.template.Template;

@Service("innerRepairAgainReportService")
@Scope("prototype")
public class InnerRepairAgainReportService {

	@Resource(name = "innerRepairAgainReportMapper")
	private InnerRepairAgainReportMapper<InnerRepairAgainReport> innerRepairAgainReportMapper;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	
	/**
	 * 获取维修总数
	 * @return
	 */
	public Integer getCountRepair(InnerRepairAgainReport report){
		return innerRepairAgainReportMapper.getCountRepair(report);
	}
	
	/**
	 * 内部二次返修报表
	 * @param report
	 * @return
	 */
	public List<InnerRepairAgainReport> getRepairAgainList(InnerRepairAgainReport report){ 
		List<InnerRepairAgainReport> reportList=innerRepairAgainReportMapper.getRepairAgainList(report);
		for (InnerRepairAgainReport r : reportList) {
			if(StringUtils.isBlank(r.getReturnTimesA())){
				r.setReturnTimesA("0");
			}
			if(StringUtils.isBlank(r.getReturnTimesP())){
				r.setReturnTimesP("0%");
			}
		}
		return reportList;
	}

	/**
	 * 导出二次维修数据
	 * 
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void doExportRepairADatas(InnerRepairAgainReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<InnerRepairAgainReport> reports = getRepairAgainList(report);
		Integer sumCount = getCountRepair(report);
		String[] fieldNames = new String[] {"维修员  ","返修数量 ","二次维修数量 ","返修率 "};
		Map map = new HashMap();
		map.put("size", reports.size()+3);
		map.put("sumCount",sumCount);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("内部二次返修报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("InnerRepairAgain.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	

}
