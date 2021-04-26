package cn.concox.app.report.controller;

import java.util.ArrayList;
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
import cn.concox.app.report.service.TopgzReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Zbxhmanage;
import cn.concox.vo.report.FgzReport;


/**
 * 各机型故障分类报表
 * @author Aikuangyong
 *
 */
@Controller
@Scope("prototype")
public class TopgzReportController extends BaseController {
	
	
	private static Logger logger = Logger.getLogger(TopgzReportController.class);
	
	@Resource(name="topgzReportService")
	private TopgzReportService topgzReportService;
	
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
	 *  各机型故障分类报表
	 * [/fenBreakdownList]  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/everyBreakdownList")
	@ResponseBody
	public APIContent everyBreakdownList(@ModelAttribute FgzReport report,HttpServletRequest req){ 
		List<FgzReport> reports2 = new ArrayList<FgzReport>();//所有机型
		try{
			queryAbout(report,req);
			reports2=topgzReportService.everyBreakdownList(report);	
			FgzReport fgzr=new FgzReport();
			int count=0;
			for (FgzReport fgzReport : reports2) {
				count += Integer.parseInt(fgzReport.getNumber());
			}
			fgzr.setNumber(String.valueOf(count));
			fgzr.setBoardModel("总计");
			reports2.add(fgzr);
			return super.putAPIData(reports2);
		}catch(Exception e){
			logger.error("获取各机型故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * [Material]
	 *  各机型故障分类报表
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/everygzExportDatas")
	@ResponseBody
	public APIContent everygzExportDatas(@ModelAttribute FgzReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			List<FgzReport> reports2 = new ArrayList<FgzReport>();//所有机型
			report.setBoardModel(new String(report.getBoardModel().getBytes("iso8859-1"),"utf-8"));
			queryAbout(report,request);			
			reports2=topgzReportService.everyBreakdownList(report);								
			FgzReport fgzr=new FgzReport();
			int count=0;
			for (FgzReport fgzReport : reports2) {
				count += Integer.parseInt(fgzReport.getNumber());
			}
			fgzr.setNumber(String.valueOf(count));
			fgzr.setBoardModel("总计");
			reports2.add(fgzr);
			topgzReportService.ExportDatas(reports2,request,response,report);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取各机型故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 *  各机型所有故障 
	 * [/fenBreakdownList]  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/allOfModelsList")
	@ResponseBody
	public APIContent getAllOfModelsList(@ModelAttribute FgzReport report,HttpServletRequest req){ 
		List<FgzReport> reports1= new ArrayList<FgzReport>();//一个机型
		List<FgzReport> reports2 = new ArrayList<FgzReport>();//所有机型
		try{
			queryAbout(report,req);
			if(report.getBoardModel()!=null&&!"".equals(report.getBoardModel())){
				reports1=topgzReportService.getAllOfModelsList(report);		
				reports2.addAll(reports1);//一个机型的故障分类	
			}else{
				List<Zbxhmanage> zbxList=fgzreporteService.getMarketModelList();
				for (Zbxhmanage zbxhmanage : zbxList) {
					report.setBoardModel(zbxhmanage.getModel());//主板型号
					reports1=topgzReportService.getAllOfModelsList(report);			
					reports2.addAll(reports1);//所有机型的故障分类
				}
			}	
			FgzReport fgzr=new FgzReport();
			int count=0;
			for (FgzReport fgzReport : reports2) {
				count += Integer.parseInt(fgzReport.getNumber());
			}
			fgzr.setNumber(String.valueOf(count));
			fgzr.setBoardModel("总计");
			reports2.add(fgzr);
			return super.putAPIData(reports2);
		}catch(Exception e){
			logger.error("各机型所有故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 *  各机型所有故障
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/allOfModelsExportDatas")
	@ResponseBody
	public APIContent allOfModelsExportDatas(@ModelAttribute FgzReport report,HttpServletRequest req,HttpServletResponse response){ 
		try{
			List<FgzReport> reports1= new ArrayList<FgzReport>();//一个机型
			List<FgzReport> reports2 = new ArrayList<FgzReport>();//所有机型
			queryAbout(report,req);
			if(report.getBoardModel()!=null&&!"".equals(report.getBoardModel())){
				reports1=topgzReportService.getAllOfModelsList(report);	
				reports2.addAll(reports1);//一个机型的故障分类	
			}else{
				List<Zbxhmanage> zbxList=fgzreporteService.getMarketModelList();
				for (Zbxhmanage zbxhmanage : zbxList) {
					report.setBoardModel(zbxhmanage.getModel());
					reports1=topgzReportService.getAllOfModelsList(report);				
					reports2.addAll(reports1);//所有机型的故障分类
				}
			}	
			FgzReport fgzr=new FgzReport();
			int count=0;
			for (FgzReport fgzReport : reports2) {
				count += Integer.parseInt(fgzReport.getNumber());
			}
			fgzr.setNumber(String.valueOf(count));
			fgzr.setBoardModel("总计");
			reports2.add(fgzr);
			topgzReportService.allOfModelsExportDatas(reports2,req,response,report);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("各机型所有故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
