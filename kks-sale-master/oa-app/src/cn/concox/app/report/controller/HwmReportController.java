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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.app.basicdata.service.TimeoutReasonService;
import cn.concox.vo.basicdata.TimeoutReason;
import cn.concox.vo.workflow.Workflow;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.controller.ZbxhmanageController;
import cn.concox.app.common.page.Page;
import cn.concox.app.report.service.HwmReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.DateReport;
import cn.concox.vo.report.HwmReport;
/**
 * 报表管理
 * @author Li.Shangzhi
 * @date 2016-08-19 14:28:14
 */
@Controller
@Scope("prototype")
public class HwmReportController extends BaseController {
	private static Logger logger = Logger.getLogger(ZbxhmanageController.class);
	
	@Resource(name="hwmReportService")
	private HwmReportService reportService;

	@Resource
	private TimeoutReasonService timeoutReasonService;
	
	/**
	 * 收件月报表
	 * @param dateReport
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getReceiveList")
	@ResponseBody
	public APIContent getReceiveList(@ModelAttribute DateReport dateReport,HttpServletRequest req){ 
		try{
			List<DateReport> reports=reportService.getReceiveList(dateReport);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取收件 分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 收件月报表-导出
	 * @param dateReport
	 * @param req
	 * @param reps
	 */
	@RequestMapping("reportCon/receiveExport")
	@ResponseBody
	public void receiveExport(@ModelAttribute DateReport dateReport,HttpServletRequest req,HttpServletResponse reps){ 
		try{
			reportService.receiveExport(dateReport,req,reps);
		}catch(Exception e){
			logger.error("获取收件 导出数据失败:"+e.toString());
		}
	}
	
	/**
	 * 收件年报表
	 * @param dateReport
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getReceiveYearList")
	@ResponseBody
	public APIContent getReceiveYearList(@ModelAttribute DateReport dateReport,HttpServletRequest req){ 
		try{
			List<DateReport> reports=reportService.getReceiveYearList(dateReport);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取收件 分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 收件年报表-导出
	 * @param dateReport
	 * @param req
	 * @param reps
	 */
	@RequestMapping("reportCon/receiveYearExport")
	@ResponseBody
	public void receiveYearExport(@ModelAttribute DateReport dateReport,HttpServletRequest req,HttpServletResponse reps){ 
		try{
			reportService.receiveYearExport(dateReport,req,reps);
		}catch(Exception e){
			logger.error("获取收件 导出数据失败:"+e.toString());
		}
	}
	
	/**
	 * 寄件月报表
	 * @param dateReport
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getDeliverList")
	@ResponseBody
	public APIContent getDeliverList(@ModelAttribute DateReport dateReport,HttpServletRequest req){ 
		try{
			List<DateReport> reports=reportService.getDeliverList(dateReport);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取寄件 分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 寄件月报表-导出
	 * @param dateReport
	 * @param req
	 * @param reps
	 */
	@RequestMapping("reportCon/deliverExport")
	@ResponseBody
	public void deliverExport(@ModelAttribute DateReport dateReport,HttpServletRequest req,HttpServletResponse reps){ 
		try{
			reportService.deliverExport(dateReport,req,reps);
		}catch(Exception e){
			logger.error("获取寄件 导出数据失败:"+e.toString());
		}
	}
	
	/**
	 * 寄件年报表
	 * @param dateReport
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getDeliverYearList")
	@ResponseBody
	public APIContent getDeliverYearList(@ModelAttribute DateReport dateReport,HttpServletRequest req){ 
		try{
			List<DateReport> reports=reportService.getDeliverYearList(dateReport);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取寄件 分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 寄件年报表-导出
	 * @param dateReport
	 * @param req
	 * @param reps
	 */
	@RequestMapping("reportCon/deliverYearExport")
	@ResponseBody
	public void deliverYearExport(@ModelAttribute DateReport dateReport,HttpServletRequest req,HttpServletResponse reps){ 
		try{
			reportService.deliverYearExport(dateReport,req,reps);
		}catch(Exception e){
			logger.error("获取寄件 导出数据失败:"+e.toString());
		}
	}

	/**
	 * 超3天机器未寄出报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getTimeoutAcceptList")
	@ResponseBody
	public APIContent getTimeoutAcceptList(@ModelAttribute HwmReport report,HttpServletRequest req,CommonParam conp){ 
		try{
			Page<HwmReport> reports=reportService.getTimeoutAcceptListPage(report,conp.getCurrentpage(),conp.getPageSize());
			req.getSession().setAttribute("totalValue", reports.getTotal());
			return super.putAPIData(reports.getResult());
		}catch(Exception e){
			logger.error("获取寄件 分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 超3天机器未寄出报表新
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getAllTimeoutAcceptList")
	@ResponseBody
	public APIContent getAllTimeoutAcceptList(@ModelAttribute HwmReport report,HttpServletRequest req,CommonParam conp){
		try{
//			//由于数据多，必须输入开始时间、结束时间或者输入treeTime。
//			if(StringUtil.isEmpty(report.getTreeDate()) && (StringUtil.isEmpty(report.getStartTime())||StringUtil.isEmpty(report.getEndTime()))){
//				return super.operaStatus(Globals.ILLEGAL_PARAMETER,Globals.ILLEGAL_PARAMETER_DESC);
//			}
			//
//			Page<HwmReport> page = new Page<>();
//			if(conp.getPageSize() != null && conp.getCurrentpage() != null){
//				page.setCurrentPage(conp.getCurrentpage());
//				page.setSize(conp.getPageSize());
//			}else{
//				page.setCurrentPage(1);
//				page.setSize(0);
//			}
			List<HwmReport> reports=reportService.getAllTimeoutList(report);
//			for(int i=0;i<reports.size();i++){
//				getAllGroupConcat(reports.get(i));
//			}
			req.getSession().setAttribute("totalValue", reports.size());
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取所有未寄出数据失败:"+e.toString());
			e.printStackTrace();
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	/**
	  根据应返回日期， 发送终检时间 ，重新填充数据,废弃，换成填充数据的时候填入数据库
	 */
	private void getAllGroupConcat(HwmReport Hw) {

		if (null != Hw && Hw.getTimeoutBackTime()!=null && Hw.getSendFicheckTime()!=null) {
			long TimeoutBackTime=Hw.getTimeoutBackTime().getTime();
			long sendFicheckTime =Hw.getSendFicheckTime().getTime();
			if (TimeoutBackTime>sendFicheckTime){
				if (Hw.getTimeoutReason()== null){
					Hw.setTimeoutReason("测试超时");
				}

			}
			else{
				if ( Hw.getTimeoutReason()== null){
					Hw.setTimeoutReason("维修超时");
				}
				if (Hw.getDutyOfficer()==null){
					Hw.setDutyOfficer(Hw.getEngineer());
				}
			}
		}
	}

	/**
	 * 超3天机器未寄出报表-导出
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/timeoutAcceptExport")
	@ResponseBody
	public void timeoutAcceptExport(@ModelAttribute HwmReport report,HttpServletRequest req,HttpServletResponse reps){ 
		try{
			reportService.timeoutAcceptExport(report,req,reps);
		}catch(Exception e){
			logger.error("获取寄件 导出数据失败:"+e.toString());
		}
	}
	
	/**
	 * 超三天报表修改
	 * @param sxdwmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("timeout/updateInfo")
	@ResponseBody
	public APIContent timeoutUpdateInfo(HttpServletRequest request) {
		try {
			String idsemp = request.getParameter("ids");
			String dutyOfficer = request.getParameter("dutyOfficer");
			String timeoutRemark = request.getParameter("timeoutRemark");
			String timeoutReason = request.getParameter("timeoutReason");

			//判断超期原因是否已经存在，如果不存在则新增到超期原因表中。
			if(timeoutReason != null && !"".equals(timeoutReason.trim())){
				if(!timeoutReasonService.hasReason(timeoutReason.trim())){
					timeoutReasonService.addTimeoutReason(new TimeoutReason(null,timeoutReason.trim(),null,null));
				}
			}

			if(StringUtil.isRealEmpty(dutyOfficer)){
				dutyOfficer = "无";
			}
			if(StringUtil.isRealEmpty(timeoutRemark)){
				timeoutRemark = "无";
			}
			if(StringUtil.isRealEmpty(timeoutReason)){
				timeoutReason = "无";
			}
			
			if(null != idsemp && !StringUtil.isEmpty(idsemp)){
				String ids[] = idsemp.split(",");
				reportService.timeoutUpdateInfo(dutyOfficer, timeoutRemark, timeoutReason, ids);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "超三天报表修改信息" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "超三天报表修改信息" + Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("修改信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "超三天报表修改信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 机型分类报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getClassifyModelList")
	@ResponseBody
	public APIContent getClassifyModelList(@ModelAttribute HwmReport report,HttpServletRequest req){ 
		try{
			List<HwmReport> reports=reportService.getClassifyModelList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取机型分类 分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 机型分类报表-导出
	 * @param report
	 * @param req
	 * @param reps
	 */
	@RequestMapping("reportCon/classifyModelExport")
	@ResponseBody
	public void classifyModelExport(@ModelAttribute HwmReport report,HttpServletRequest req,HttpServletResponse reps){ 
		try{
			reportService.classifyModelExport(report, req, reps);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取机型分类 导出数据失败:"+e.toString());
		}
	}
	
	
	/**
	 * 维修总明细报表分页
	 * @param report
	 * @param comp
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getRepairDetailListPage")
	@ResponseBody
	public APIContent getRepairDetailListPage(@ModelAttribute HwmReport report,@ModelAttribute CommonParam comp,HttpServletRequest req){ 
		try{
			String endTime = req.getParameter("endTime");
			String startTime = req.getParameter("startTime");
			String repairDetailFlag = req.getParameter("repairDetailFlag");
			report.setRepairDetailFlag(repairDetailFlag);
			Page<HwmReport> reports=reportService.getRepairDetailList(report, comp.getCurrentpage(),comp.getPageSize(), startTime, endTime);
			req.getSession().setAttribute("totalValue", reports.getTotal());
			return super.putAPIData(reports.getResult());
		}catch(Exception e){
			logger.error("获取维修总明细 分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 维修总明细报表-导出
	 * @param report
	 * @param req
	 * @param reps		
	 */
	@RequestMapping("reportCon/repairDetailExport")
	@ResponseBody
	public void repairDetailExport(@ModelAttribute HwmReport report,HttpServletRequest req,HttpServletResponse reps){ 
		try{
			String endTime = req.getParameter("endTime");
			String startTime = req.getParameter("startTime");
			String repairDetailFlag = req.getParameter("repairDetailFlag");
			report.setRepairDetailFlag(repairDetailFlag);
			reportService.repairDetailExport(report, req, reps, startTime, endTime);
		}catch(Exception e){
			logger.error("获取机型分类 导出数据失败:"+e.toString());
		}
	}

	/**
	 * 获取超期维修人员
	 */
	@RequestMapping("reportCon/timeoutEngineer")
	@ResponseBody
	public APIContent timeoutEngineer(@ModelAttribute HwmReport report){
		try{
			return super.putAPIData(reportService.timeoutEngineer(report));
		}catch(Exception e){
			logger.error("获取超期维修人员失败:"+e.toString());
			return  super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	/**
	 * 获取超期的状态
	 */
	@RequestMapping("reportCon/timeoutState")
	@ResponseBody
	public APIContent timeoutState(@ModelAttribute HwmReport report){
		try{
			return super.putAPIData(reportService.timeoutState(report));
		}catch(Exception e){
			logger.error("获取超期的状态失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
