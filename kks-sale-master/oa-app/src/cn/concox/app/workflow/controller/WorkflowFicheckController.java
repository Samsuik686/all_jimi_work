/*
 * Created: 2016-08-04
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
package cn.concox.app.workflow.controller;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowFicheckService;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowFicheck;
/**
 * 终检管理
 * @author Li.Shangzhi
 * @date 2016年8月4日
 */
@Controller
@Scope("prototype")
public class WorkflowFicheckController extends BaseController {
	Logger logger=Logger.getLogger("workflowInfo");
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	@Resource(name="ficheckService")
	private WorkflowFicheckService ficheckService;


	/**
	 * 获取终检分页数据
	 * <p>暂无分页</p>
	 * @param WorkflowFicheck
	 * @param req
	 * @return
	 */
	@RequestMapping("workflowfinCheckcon/finCheckPage")
	@ResponseBody
	public APIContent getFinCheckList(@ModelAttribute WorkflowFicheck ficheck,HttpServletRequest req,
			CommonParam common){ 
		try{
			List<Workflow> comms = new ArrayList<Workflow>();
			Page<WorkflowFicheck> pageFichecks=ficheckService.getFinCheckPage(ficheck, common.getCurrentpage(), common.getPageSize()); 
			req.getSession().setAttribute("totalValue", pageFichecks.getTotal());
			 List<WorkflowFicheck> fichecks = pageFichecks.getResult();
			if(null != fichecks && fichecks.size()>0){
				for (WorkflowFicheck Ficheck : fichecks){
					Workflow comm = workflowService.getInfo(Integer.parseInt(Ficheck.getWfId().toString()), true);
					if(null !=comm){
						setProperties(comm,Ficheck);
						comm.setW_finId(Ficheck.getId());
						comm.setW_FinChecker(Ficheck.getFinChecker()); 
						comms.add(comm);
					}
				}
			}
			return super.putAPIData(comms);
		}catch(Exception e){
			logger.error("获取终检数据数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
		/**
	 * 获取终检数据
	 * <p>暂无分页</p>
	 * @param WorkflowFicheck
	 * @param req
	 * @return
	 */
	@RequestMapping("workflowfinCheckcon/finCheckList")
	@ResponseBody
	public APIContent getFinCheckList(@ModelAttribute WorkflowFicheck ficheck,HttpServletRequest req){ 
		try{
			String treeDateStr = ficheck.getTreeDate();
			if(treeDateStr != null && StringUtils.isNotBlank(treeDateStr)){
				
				int morIndex = treeDateStr.indexOf("上午");
				int aftIndex = treeDateStr.indexOf("下午");
				if(morIndex > -1){
					//查询下午数据
					ficheck.setSearchMorOrAft("mor");
					ficheck.setTreeDate(treeDateStr.substring(0,treeDateStr.indexOf("上午")));				
				}else if(aftIndex > -1){
					ficheck.setSearchMorOrAft("aft");
					ficheck.setTreeDate(treeDateStr.substring(0,treeDateStr.indexOf("下午")));
				}
			}
			// 查询放弃报价的设备数量
			Long giveupCount = ficheckService.selGiveUpPriceCount(ficheck);
			giveupCount = giveupCount >= 0 ? giveupCount : 0;
			
			List<Workflow> comms = new ArrayList<Workflow>();
			List<WorkflowFicheck> fichecks=ficheckService.getFinCheckList(ficheck); 
			if(null != fichecks && fichecks.size()>0){
				for (WorkflowFicheck Ficheck : fichecks){
					Workflow comm = workflowService.getInfo(Integer.parseInt(Ficheck.getWfId().toString()), true);
					if(null !=comm){
						setProperties(comm,Ficheck);
						comm.setGiveupPriceCount(giveupCount);
						comm.setW_finId(Ficheck.getId());
						comm.setW_FinChecker(Ficheck.getFinChecker()); 
						comms.add(comm);
					}
				}
			}
			return super.putAPIData(comms);
		}catch(Exception e){
			logger.error("获取终检数据数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	private void setProperties(Workflow comm, WorkflowFicheck ficheck) {
		comm.setW_FinDesc(ficheck.getFinDesc()); 
		comm.setW_FinispassFalg(ficheck.getState());
		comm.setW_engineer(ficheck.getFin_engineer());
		comm.setW_payTime(ficheck.getFin_payTime());
		comm.setAcceptRemark(ficheck.getF_acceptRemark());
		comm.setRemark(ficheck.getF_remark());
		comm.setRepairNumberRemark(ficheck.getF_repairNumberRemark());
		comm.setW_priceRemark(ficheck.getF_priceRemark());
		comm.setW_repairRemark(ficheck.getF_repairRemark());
		comm.setTestPerson(ficheck.getTestPerson());
		comm.setW_sendFinTime(ficheck.getCreateTime());
		if(null != ficheck.getTestResult() && !"".equals(ficheck.getTestResult())){
			StringBuffer result = new StringBuffer();
			if(ficheck.getTestPerson() != null && ficheck.getTestTime() != null){
				result.append("———"+ficheck.getTestPerson()+"    "+ficheck.getTestTime());
				comm.setTestResult(ficheck.getTestResult()+result.substring(0, result.lastIndexOf(".0")));
			}
			
		}		
		workflowService.setBackDate(comm);
	}

	
	
	/**
	 * 发送至维修 | 退回工作状态
	 * @param req
	 * @return
	 */
	@RequestMapping("workflowfinCheckcon/sendRepair")
	@ResponseBody
	public APIContent sendRepair(HttpServletRequest req){ 
		try{
			String idsemp = req.getParameter("ids");
			if(null != idsemp && !StringUtil.isEmpty(idsemp)){
				String ids[] = idsemp.split(",");
				if(ids != null && ids.length >0){
					if(ficheckService.getCountToRepairByWfids(ids) != ids.length){
						return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
					}
					ficheckService.sendRepair(ids);
					return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);   
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			logger.error("不合格发送维修失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	
	/**
	 * @Title: sendBackToRepair 
	 * @Description:待终检返回维修
	 * @param req
	 * @return 
	 * @author 20160308
	 * @date 2017年11月28日 下午4:24:17
	 */
	@RequestMapping("workflowfinCheckcon/sendBackToRepair")
	@ResponseBody
	public APIContent sendBackToRepair(HttpServletRequest req){ 
		try{
			String idsemp = req.getParameter("ids");
			if(null != idsemp && !StringUtil.isEmpty(idsemp)){
				String ids[] = idsemp.split(",");
				if(ids != null && ids.length >0){
					if(ficheckService.getCountToTestByWfids(ids) != ids.length){
						return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
					}
					ficheckService.sendBackToRepair(ids);
					return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);   
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			logger.error("待终检返回维修失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 发送装箱
	 * @param req
	 * @return
	 */
	@RequestMapping("workflowfinCheckcon/sendEnchase")
	@ResponseBody
	public APIContent sendEnchase(HttpServletRequest req){ 
		try{
			String idsemp = req.getParameter("ids");
			if(!StringUtil.isRealEmpty(idsemp)){
				String ids[] = idsemp.split(",");
				if(ids != null && ids.length >0){
					if(ficheckService.getCountToPackByWfids(ids) != ids.length){
						return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
					}
					ficheckService.sendPack(ids);
					return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);   
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			logger.error("发送装箱错误：",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	
	/**
	 * 获取终检匹配数据
	 * @param req
	 * @return
	 */
	@RequestMapping("workflowfinCheckcon/doFinCheck")
	@ResponseBody
	public APIContent doFinCheck(HttpServletRequest req){ 
		try{
			String fiid = req.getParameter("fiId");
			if(null != fiid  && !StringUtil.isEmpty(fiid)){
				WorkflowFicheck  ficheck = ficheckService.getInfo(Integer.parseInt(fiid));  
				if(ficheck !=null && null !=ficheck.getWfId()){
					Workflow  workflow = workflowService.getInfo(Integer.parseInt(ficheck.getWfId().toString()), true);
					setProperties(workflow,ficheck);
					workflow.setW_fId(ficheck.getId().toString());
					workflow.setW_FinDesc(ficheck.getFinDesc());
					workflow.setW_FinChecker(super.getSessionUserName(req));
					workflow.setW_ispass(String.valueOf(ficheck.getIspass()));
					return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, workflow);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);		
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			logger.error("获取终检匹配数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 修改终检
	 * 
	 * @param workflow;
	 * @param req
	 * @return
	 */
	@RequestMapping("workflowfinCheckcon/updateInfo")
	@ResponseBody
	public APIContent updateInfo(@ModelAttribute WorkflowFicheck ficheck, HttpServletRequest req) {
		try {
			ficheck.setFinChecker(super.getSessionUserName(req));
			ficheck.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    ficheckService.update(ficheck);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("修改终检失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 查看送修批次的设备维修状态
	 * @param req
	 * @return
	 */
	@RequestMapping("workflowfinCheckcon/imeiSateList")
	@ResponseBody
	public APIContent getimeiSateList(HttpServletRequest req) {
		try {
			String repairnNmber=req.getParameter("repairnNmber");
			String imei = req.getParameter("imei");
			String state = req.getParameter("state");

			List<Workflow> wfList = ficheckService.getStateByRepairnNmber(repairnNmber, imei, state);		
			return super.putAPIData(wfList);
		} catch (Exception e) {
			logger.error("查看详情失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
