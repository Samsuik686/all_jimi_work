/*
 * Created: 2016-7-15
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
 * @functionName : system
 * @systemAbreviation : sale
 */
package cn.concox.app.basicdata.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.PricePjService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.PricePj;
import cn.concox.vo.commvo.CommonParam;

/**
 * <pre>
 * 配件料控制层
 * </pre>
 * 
 * @author Li.ShangZhi
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class PricePjController extends BaseController {

	@Resource(name = "pricePjService")
	private PricePjService pricePjService;

	/**
	 * 配件料分页查询
	 * 
	 * @param PricePj
	 * @param req
	 * @return
	 */
	@RequestMapping("pricePj/manageList")
	@ResponseBody
	public APIContent getmanageListPage(@ModelAttribute PricePj pricePj, @ModelAttribute CommonParam comp, HttpServletRequest req) {
		try {
			Page<PricePj> dzlListPage = pricePjService.getManageListPage(pricePj, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", dzlListPage.getTotal());
			return super.putAPIData(dzlListPage.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 获取单配件料详情
	 * 
	 * @param PricePj
	 * @param req
	 * @return
	 */
	@RequestMapping("pricePj/getInfo")
	@ResponseBody
	public APIContent getInfo(@ModelAttribute PricePj pricePj, HttpServletRequest req) {
		try {
			pricePj = pricePjService.getInfo(pricePj);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, pricePj);
		} catch (Exception e) {
			logger.error("获取配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 新增 配件料信息
	 * 
	 * @param PricePj
	 * @param req
	 * @return
	 */
	@RequestMapping("pricePj/addInfo")
	@ResponseBody
	public APIContent addInfo(@ModelAttribute PricePj pricePj, HttpServletRequest req) {
		try {
			String ids = req.getParameter("ids");
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(",");
				for (String s : id) {
					pricePj.setMid(Integer.valueOf(s));
					pricePjService.insertInfo(pricePj);
				}
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "配件料信息新增" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("新增配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 修改配件料信息
	 * 
	 * @param PricePj
	 * @param req
	 * @return
	 */
	@RequestMapping("pricePj/updateInfo")
	@ResponseBody
	public APIContent updateInfo(@ModelAttribute PricePj pricePj, HttpServletRequest req) {
		try {
			pricePjService.updateInfo(pricePj);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "配件料信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("修改配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 删除配件料信息
	 * 
	 * @param pricePj
	 * @param req
	 * @return
	 */
	@RequestMapping("pricePj/deleteInfo")
	@ResponseBody
	public APIContent deleteInfo(HttpServletRequest req) {
		try {
			String ids =req.getParameter("ids");
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(","); 
				pricePjService.deleteInfo(id);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "配件料信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("删除配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("pricePj/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute PricePj pricePj, HttpServletRequest request) {
		try {
			List<PricePj> c = pricePjService.queryList(pricePj);
			return super.putAPIData(c);
		} catch (Exception e) {
			logger.error("获取所有配件料信息列表失败", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取配件料信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 新增时查看是否重复
	 * @param pricePj
	 * @param request
	 * @return
	 */
	@RequestMapping("pricePj/checkRepeat")
	@ResponseBody
	public APIContent checkRepeat (HttpServletRequest req) {
		try {
			String ids=req.getParameter("ids");
			String repairNumber=req.getParameter("repairNumber");
			int ret = 0;
			if(null != ids && !StringUtil.isEmpty(ids)&&null != repairNumber && !StringUtil.isEmpty(repairNumber)){
				String id[] = ids.split(",");
				for (String s : id) {
					ret+=pricePjService.findCountForPricePj(repairNumber,s);
				}
			}
			return super.putAPIData(ret);
		} catch (Exception e) {
			logger.error("新增时查看是否重复失败", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 批次获得配件费
	 * @param pricePj
	 * @param request
	 * @return
	 */
	@RequestMapping("pricePj/getPricePjCosts")
	@ResponseBody
	public APIContent getPricePjCosts(@RequestParam(value="repairNumber",required=true) String repairNumber, HttpServletRequest request) {
		try {
			BigDecimal c = pricePjService.getPricePjCosts(repairNumber);
			return super.putAPIData(c);
		} catch (Exception e) {
			logger.error("获取所有配件料信息列表失败", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取配件料信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}