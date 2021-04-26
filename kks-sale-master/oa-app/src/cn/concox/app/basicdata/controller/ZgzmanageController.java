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
 * @file: SalezgzmanageController.java
 * @functionName : system
 * @systemAbreviation : sale
 */
package cn.concox.app.basicdata.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.concox.app.basicdata.service.ZgzmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Zgzmanage;
import cn.concox.vo.commvo.CommonParam;
/**
 * <pre>
 * 最终故障控制层
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class ZgzmanageController extends BaseController {
    
	@Resource(name="zgzmanageService")
	private ZgzmanageService zgzmanageService;
	
	
	/**
	 * 最终故障分页查询
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zgzmanagecon/manageList")
	@ResponseBody
	public APIContent getmanageListPage(@ModelAttribute Zgzmanage zgzmanage, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
//			int getCurrentPage=1;
//			if(!StringUtils.isBlank(req.getParameter("currentpage"))){
//				getCurrentPage=Integer.parseInt(req.getParameter("currentpage"));
//			}
			Page<Zgzmanage> dzlListPage=zgzmanageService.getManageListPage(zgzmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", dzlListPage.getTotal());
			return super.putAPIData(dzlListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取最终故障数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取单最终故障详情
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zgzmanagecon/getInfo")      
	@ResponseBody
	public APIContent getInfo(@ModelAttribute Zgzmanage zgzmanage,HttpServletRequest req){
		try{
			zgzmanage = zgzmanageService.getInfo(zgzmanage);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, zgzmanage);      
		}catch(Exception e){
			logger.error("获取最终故障数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 新增 / 修改最终故障信息 
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zgzmanagecon/addOrUpdateInfo")
	@ResponseBody
	public APIContent addOrUpdateDzlInfo(@ModelAttribute Zgzmanage zgzmanage,HttpServletRequest req){
		try {  
			if(zgzmanageService.isExistsGid(zgzmanage)!=null){
//				if(zgzmanageService.isExistsZBXH(zgzmanage)!=null){
					if(zgzmanageService.isExists(zgzmanage)==0){
						if(zgzmanage.getId()>0){
							zgzmanageService.updateInfo(zgzmanage);
							return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"最终故障信息修改"+Globals.OPERA_SUCCESS_CODE_DESC);
						}else{
							zgzmanageService.insertInfo(zgzmanage);
							return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"最终故障信息新增"+Globals.OPERA_SUCCESS_CODE_DESC);
						}
					}else{
						return super.operaStatus(Globals.OPERA_FAIL_CODE, "最终故障信息已存在,请检查" );
					}
//				}else{
//					return super.operaStatus(Globals.OPERA_FAIL_CODE,"型号类别不存在,请检查" );
//				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,"最终故障类别不存在,请检查" );
			}
		}catch(Exception e){
			logger.error("更新最终故障数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除最终故障信息
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zgzmanagecon/deleteInfo")
	@ResponseBody
	public APIContent deleteDzlInfo(@ModelAttribute Zgzmanage zgzmanage,HttpServletRequest req){
		try{
			if(zgzmanageService.checkSupportDel(zgzmanage)){
				zgzmanageService.deleteInfo(zgzmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"最终故障信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "最终故障信息存在关联数据，不可删除");
		}catch(Exception e){
			logger.error("删除最终故障数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
    
	
	@RequestMapping("zgzmanage/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("zgzmanage") Zgzmanage zgzmanage, HttpServletRequest request) {
		try {
			List<Zgzmanage> c = zgzmanageService.queryList(zgzmanage);
			return super.putAPIData(c);
		} catch (Exception e) {
			logger.error("获取所有最终故障信息列表失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取最终故障信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导出最终故障信息
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zgzmanagecon/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Zgzmanage zgzmanage,HttpServletRequest request,HttpServletResponse response){
		try{
			zgzmanageService.ExportDatas(zgzmanage,request,response);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出最终故障数据失败:",e);
		}
	}

	/**
	 * 导入最终故障信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("zgzmanagecon/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = zgzmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			logger.error("导入最终故障信息失败:",e);
			errorList.add("导入最终故障信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
	
	@RequestMapping("zgzmanagecon/getZgzmanageByIds")
	@ResponseBody
	public APIContent getZgzmanageByIds(@RequestParam String ids, HttpServletRequest request, HttpServletResponse response) {
		try {
			String[] idsArr = new String[]{};
			if (StringUtils.isNotBlank(ids)) {
				idsArr = ids.split(",");
			}
			List<Zgzmanage> c = zgzmanageService.queryListByIds(idsArr);
			return super.putAPIData(c);
		} catch (Exception e) {
			logger.error("获取最终故障信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取最终故障信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
}