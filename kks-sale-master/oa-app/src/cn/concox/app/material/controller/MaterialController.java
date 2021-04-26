/*
 * Created: 2016-8-10
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
package cn.concox.app.material.controller;

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
import cn.concox.app.material.service.MaterialService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.material.Material;

@Controller
@Scope("prototype")
public class MaterialController extends BaseController {

	Logger logger = Logger.getLogger(MaterialController.class);

	@Resource(name = "materialService")
	private MaterialService materialService;

	/**
	 * 获取物料统计数据
	 * <p>
	 * 暂无分页
	 * </p>
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("materiacon/queryList")
	@ResponseBody
	public APIContent queryList(@ModelAttribute Material material, HttpServletRequest req) {
		try {
			List<Material> materials = materialService.queryList(material);
			return super.putAPIData(materials);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取获取物料统计数据失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 获取物料统计数据
	 * <p>
	 * 分页
	 * </p>
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("materiacon/queryListPage")
	@ResponseBody
	public APIContent queryListPage(@ModelAttribute Material material, @ModelAttribute CommonParam comp, HttpServletRequest req) {
		try {
			Page<Material> materialListPage = materialService.queryListPage(material, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", materialListPage.getTotal());
			return super.putAPIData(materialListPage.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取获取物料统计数据失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 修改物料信息
	 * @param material
	 * @param request
	 * @return
	 */
	@RequestMapping("materiacon/updateInfo")
	@ResponseBody
	public APIContent updateInfo(@ModelAttribute Material material, HttpServletRequest request) {
		try {
			materialService.update(material);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物料信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("更新物料信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新物料信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 生成物料需求单
	 * <p>
	 * 暂无分页
	 * </p>
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("materiacon/getOrderList")
	@ResponseBody
	public APIContent getOrderList(@ModelAttribute Material material, @ModelAttribute CommonParam comp, HttpServletRequest req) {
		try {
			Page<Material> orders = materialService.getOrderListPage(material, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValueTwo", orders.getTotal());
			return super.putAPIData(orders.getResult());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取生成物料需求单数据失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 导出物料统计报表
	 * 
	 * @param Material
	 * @param req
	 * @return
	 */
	@RequestMapping("materiacon/ExportMaterialDatas")
	@ResponseBody
	public void ExportMaterialDatas(@ModelAttribute Material material, HttpServletRequest request, HttpServletResponse response) {
		try {
			materialService.ExportMaterailStatisticsDatas(material, request, response);
		} catch (Exception e) {
			logger.error("导出物料需求单数据失败:" + e.toString());
		}
	}

	/**
	 * 导出物料需求单数据
	 * 
	 * @param Material
	 * @param req
	 * @return
	 */
	@RequestMapping("materiacon/ExportOrderDatas")
	@ResponseBody
	public void ExportOrderDatas(@ModelAttribute Material material, HttpServletRequest request, HttpServletResponse response) {
		try {
			materialService.ExportMaterialOrderDatas(material, request, response);
		} catch (Exception e) {
			logger.error("导出物料需求单数据失败:" + e.toString());
		}
	}

}
