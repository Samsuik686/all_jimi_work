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
package cn.concox.app.report.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.report.service.ReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.DateUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.RepairAgainDetail;
import cn.concox.vo.report.Report;
/**
 * 报表管理
 * @author Li.Shangzhi
 * @date 2016-08-19 14:28:14
 */
@Controller
@Scope("prototype")
public class ReportController extends BaseController {
	private static Logger logger = Logger.getLogger(ReportController.class);
	
	@Resource(name="reportService")
	private ReportService reportService;
	
	/**
	 * TODO A1.0
	 *  配件报价报表  
	 * [/getAccqueteList]  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getAccqueteList")
	@ResponseBody
	public APIContent getAcceptList(@ModelAttribute Report report,HttpServletRequest req){ 
		try{
			List<Report> reports=reportService.getAccqueteList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取配件报价数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * TODO A1.1
	 * 配件报价报表  
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/doExportDatas")
	@ResponseBody
	public APIContent doExportDatas(@ModelAttribute Report report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			reportService.ExportDatas(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("配件报价报表:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	/**
	 * TODO A2.0
	 * 二次返修报表  
	 * [/getRepairagainList]  获取二次维修数据
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getRepairagainList")
	@ResponseBody
	public APIContent getRepairagainList(@ModelAttribute Report report,HttpServletRequest req){ 
		try{
		/*	Report report = new Report();*/
			
			String StartTime = req.getParameter("StartDate");
			String EndTime   = req.getParameter("EndDate");
			
			if(!StringUtil.isRealEmpty(StartTime)){
				report.setStratTime(StartTime);
			}
			if(!StringUtil.isRealEmpty(EndTime)){
				report.setEndTime(EndTime); 
			}
			Report rt=new Report();
			// 数据报表
			List<Report> reports=reportService.getRepairAgainList(report);
			rt.setData(reports);
			
			// 受理总数
			Integer sumCount = reportService.getCountRepair(report); 
			rt.setSumCount(sumCount);
			
			// 放弃维修总数
			Integer giveUpRepair=reportService.getGiveUpRepair(report); 
			rt.setGiveUpRepair(giveUpRepair);
			
			// 二次维修总数
            Integer twiceRepair=reportService.countTwiceRepair(report); 
            rt.setTwiceRepair(twiceRepair);
			
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC, rt);
		}catch(Exception e){
			logger.error("二次维修数据:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("reportCon/getRepairagainDetailList")
	@ResponseBody
	public APIContent getRepairagainDetailList(@ModelAttribute RepairAgainDetail report,HttpServletRequest req,CommonParam conp){ 
		try{
			String StartTime = req.getParameter("StartDate");
			String EndTime   = req.getParameter("EndDate");
			if(!StringUtil.isRealEmpty(StartTime)){
				report.setStratTime(StartTime);
			}
			if(!StringUtil.isRealEmpty(EndTime)){
				report.setEndTime(EndTime); 
			}
			Page<RepairAgainDetail> page =reportService.getRepairAgainDetailListPage(report,conp.getCurrentpage(),conp.getPageSize());
			req.getSession().setAttribute("totalValueTwo", page.getTotal());
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC, page.getResult());
		}catch(Exception e){
			logger.error("二次维修详情数据:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
		
	
	/**
	 * TODO A2.1
	 * 二次返修报表  
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/doExportRepairADatas")
	@ResponseBody
	public APIContent doExportRepairADatas(HttpServletRequest request,HttpServletResponse response){ 
		try{
			Report report = new Report();
			
			String StartTime = request.getParameter("StartDate");
			String EndTime   = request.getParameter("EndDate");
			
			if(!StringUtil.isRealEmpty(StartTime)){
				report.setStratTime(StartTime);
			}
			if(!StringUtil.isRealEmpty(EndTime)){
				report.setEndTime(EndTime); 
			}
			reportService.doExportRepairADatas(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("二次返修报表  :"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * TODO A2.1
	 * 维修员二次返修详情报表  
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/doExportRepairDetail")
	@ResponseBody
	public APIContent doExportRepairDetail(@ModelAttribute RepairAgainDetail report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			
			String StartTime = request.getParameter("StartDate");
			String EndTime   = request.getParameter("EndDate");
			
			if(!StringUtil.isRealEmpty(StartTime)){
				report.setStratTime(StartTime);
			}
			if(!StringUtil.isRealEmpty(EndTime)){
				report.setEndTime(EndTime); 
			}
			reportService.doExportRepairDetail(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("维修员二次返修详情报表  :"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	
}
