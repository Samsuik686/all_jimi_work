package cn.concox.app.report.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.report.service.FgzReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Zbxhmanage;
import cn.concox.vo.report.FgzReport;


/**
 * 保内各机型故障分类报表
 * @author Aikuangyong
 *
 */
@Controller
@Scope("prototype")
public class FgzReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(FgzReportController.class);
	
	@Resource(name="fgzreporteService")
	private FgzReportService fgzreporteService;
	
	
	/**
	 * 根据查询条件显示数据
	 * @param request
	 */
	public void queryAbout(FgzReport report, HttpServletRequest request){
		String startTime=request.getParameter("startDate");
		if(!StringUtils.isBlank(startTime)){
			report.setStartTime(startTime);
		}
		
		String endTime=request.getParameter("endDate");
		if(!StringUtils.isBlank(endTime)){
			report.setEndTime(endTime);
		}
	}
	
	/**
	 * [Material]
	 *  保内各机型故障分类报表 
	 * [/fenBreakdownList]  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/fenBreakdownList")
	@ResponseBody
	public APIContent fenBreakdownList(@ModelAttribute FgzReport report,HttpServletRequest req){ 
		try{
			queryAbout(report,req);
			List<FgzReport> reports=fgzreporteService.getBreakdownList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取各机型故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * [Material]
	 *  保内各机型故障分类报表
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/fgzExportDatas")
	@ResponseBody
	public APIContent fgzExportDatas(@ModelAttribute FgzReport report,HttpServletRequest req,HttpServletResponse response){ 
		try{
			queryAbout(report,req);
			fgzreporteService.ExportDatas(report,req,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取各机型故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 查出所有机型
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getMarketModelList")
	@ResponseBody
	public APIContent getMarketModelList(HttpServletRequest req){ 
		try{
			List<Zbxhmanage> zbx=fgzreporteService.getMarketModelList();
			return super.putAPIData(zbx);
		}catch(Exception e){
			logger.error("获取机型数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

}
