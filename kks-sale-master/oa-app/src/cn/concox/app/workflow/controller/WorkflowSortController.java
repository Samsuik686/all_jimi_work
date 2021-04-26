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
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.app.workflow.service.WorkflowSortService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.workflow.Workflow;
/**
 * 分拣管理
 * @author Li.Shangzhi
 * @date 2016年8月4日
 */
@Controller
@Scope("prototype")
public class WorkflowSortController extends BaseController {
	Logger logger=Logger.getLogger("workflowInfo");
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	@Resource(name="workflowSortService")
	private WorkflowSortService workflowSortService;
	/**
	 * 获取分拣数据
	 * <p>暂无分页</p>
	 * @param Workflow
	 * @param req
	 * @return
	 */
	@RequestMapping("sortcon/sortPage")
	@ResponseBody
	public APIContent getSortList(@ModelAttribute Workflow workflow,HttpServletRequest req, 
			@ModelAttribute CommonParam comp){ 
		try{
			String sortState=req.getParameter("sortState");
			if(!StringUtil.isRealEmpty(sortState)){
				workflow.setSortState(Integer.valueOf(sortState));
			}
			workflow.setState(1);     //TODO 分拣状态  | 即 受理完,已发送状态
			Page<Workflow> wflListPage=workflowService.getSortPage(workflow, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", wflListPage.getTotal());
			List<Workflow> wl=wflListPage.getResult();
			workflowService.setList(wl);			
			return super.putAPIData(wl);
		}catch(Exception e){
			logger.error("获取分拣分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取分拣数据
	 * <p>暂无分页</p>
	 * @param Workflow
	 * @param req
	 * @return
	 */
	@RequestMapping("sortcon/sortList")
	@ResponseBody
	public APIContent getSortList(@ModelAttribute Workflow workflow,HttpServletRequest req){ 
		try{
			String sortState=req.getParameter("sortState");
			if(!StringUtil.isRealEmpty(sortState)){
				workflow.setSortState(Integer.valueOf(sortState));
			}
			workflow.setState(1);     //TODO 分拣状态  | 即 受理完,已发送状态
			List<Workflow> wflListPage=workflowService.getSortList(workflow);
			return super.putAPIData(wflListPage);
		}catch(Exception e){
			logger.error("获取分拣分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 发送维修
	 * @param Workflow
	 * @param req
	 * @return
	 */
	@RequestMapping("sortcon/sendRepair")
	@ResponseBody
	public APIContent sendRepair(HttpServletRequest req){ 
		try{
			String idsemp = req.getParameter("ids");
			if(null != idsemp && !StringUtil.isEmpty(idsemp)){
				String ids[] = idsemp.split(",");
				
				if(ids != null && ids.length >0){
					if(workflowSortService.getCountSortByIds(ids) != ids.length){
						return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
					}
					workflowSortService.sendRepair(super.getSessionUserName(req), ids);
					return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);   
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			logger.error("发送维修失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 分拣工站返回数据到受理工站
	 * @author TangYuping
	 * @version 2017年1月12日 下午2:08:59
	 * @param req
	 * @return
	 */
	@RequestMapping("sortcon/sendDataToAccept")
	@ResponseBody
	public APIContent sendDataToAccept(HttpServletRequest req){
		try{
			String idsemp = req.getParameter("ids");
			if(null != idsemp && !StringUtil.isEmpty(idsemp)){
				String ids[] = idsemp.split(",");
				
				if(ids != null && ids.length >0){
					if(workflowSortService.getCountSortByIds(ids) != ids.length){
						return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
					}
					workflowSortService.backToAccept(0,ids);  //状态改成已受理，分拣时间置空
					return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);   
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("数据返回受理工站失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
