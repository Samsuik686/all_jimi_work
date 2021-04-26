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
 * @file: Salebasegroupcontroller.java
 * @functionName : system
 * @systemAbreviation : sale
 */
package cn.concox.app.basicdata.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.BasegroupService;
import cn.concox.app.basicdata.service.CjgzmanageService;
import cn.concox.app.basicdata.service.DzlmanageService;
import cn.concox.app.basicdata.service.RepairPriceService;
import cn.concox.app.basicdata.service.SjfjmanageService;
import cn.concox.app.basicdata.service.YcfkBaseService;
import cn.concox.app.basicdata.service.ZbxhmanageService;
import cn.concox.app.basicdata.service.ZgzmanageService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Basegroup;
@Controller
@Scope("prototype")
public class BasegroupController extends BaseController {

	Logger logger=Logger.getLogger("privilege");

	@Resource(name="basegroupService")
	private BasegroupService basegroupService;

	@Resource(name="zgzmanageService")
	private ZgzmanageService zgzmanageService;
	
	@Resource(name="zbxhmanageService")
	private ZbxhmanageService zbxhmanageService;
	
	@Resource(name="sjfjmanageService")
	private SjfjmanageService sjfjmanageService;
	
	@Resource(name="cjgzmanageService")
	private CjgzmanageService cjgzmanageService;
	
	@Resource(name="ycfkBaseService")
	private YcfkBaseService ycfkBaseService;
	
	@Resource(name="repairPriceService")
	private RepairPriceService repairPriceService;
	
	@Resource(name="dzlmanageService")
	private DzlmanageService dzlmanageService;
	
	/**
	 * 获取单分组详情
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("basegroupcon/getInfo")      
	@ResponseBody
	public APIContent getInfo(@ModelAttribute Basegroup basegroup,HttpServletRequest req){
		try{
			basegroup = basegroupService.getInfo(basegroup);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, basegroup);      
		}catch(Exception e){
			logger.error("获取分组数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 新增 /修改分组信息 ，前端会传过来组ID
	 * @param Zgzmanage
	 * @param req
	 * @return updateByGid
	 */
	@RequestMapping("basegroupcon/addOrUpdateInfo")
	@ResponseBody
	public APIContent addOrUpdateDzlInfo(@ModelAttribute Basegroup basegroup,HttpServletRequest req){
		try{
			if(basegroupService.isExists(basegroup)==0){
				if(basegroup.getGId()>0){
					basegroupService.updateInfo(basegroup);
					if(zgzmanageService.getCountByGid(basegroup.getGId())>0){
						zgzmanageService.updateByGid(basegroup.getGId());
					}else if("BASE_ZBXH".equals(basegroup.getEnumSn())&&zbxhmanageService.getCountByGid(basegroup.getGId())>0){
						zbxhmanageService.updateByGid(basegroup.getGId());
					}else if(sjfjmanageService.getCountByGid(basegroup.getGId())>0){
						sjfjmanageService.updateByGid(basegroup.getGId());
					}else if(cjgzmanageService.getCountByGid(basegroup.getGId())>0){
						cjgzmanageService.updateByGid(basegroup.getGId());
					}else if(repairPriceService.getCountByGid(basegroup.getGId())>0){
						repairPriceService.updateByGid(basegroup.getGId());
					}else if(ycfkBaseService.getCountByGid(basegroup.getGId())>0){
						ycfkBaseService.updateByGid(basegroup.getGId());
					}else if(dzlmanageService.getCountByGid(basegroup.getGId())>0){
						dzlmanageService.updateByGid(basegroup.getGId());
					}
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"分组信息修改"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					basegroup.setCreateTime(new Timestamp(System.currentTimeMillis()));
					basegroupService.insertInfo(basegroup);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"分组信息新增"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "分组信息已存在,请检查" );
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新分组数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 删除分组信息
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("basegroupcon/deleteInfo")
	@ResponseBody
	public APIContent deleteDzlInfo(@ModelAttribute Basegroup basegroup,HttpServletRequest req){
		try{
			if(zgzmanageService.getCountByGid(basegroup.getGId())==0 && zbxhmanageService.getCountByGid(basegroup.getGId())==0 &&
				sjfjmanageService.getCountByGid(basegroup.getGId())==0 && cjgzmanageService.getCountByGid(basegroup.getGId())==0 && 
				ycfkBaseService.getCountByGid(basegroup.getGId())==0 && repairPriceService.getCountByGid(basegroup.getGId()) == 0 && 
				dzlmanageService.getCountByGid(basegroup.getGId())==0 && zbxhmanageService.getCountByCreateTypeId(basegroup.getGId())==0){
				basegroupService.deleteInfo(basegroup);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"分组信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);	
			}else{
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE_DESC,"请先删除该组下数据后再删除组");
			}
		}catch(Exception e){
			logger.error("删除分组数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 获取分组列表信息
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping("basegroupcon/queryList")
	@ResponseBody
	public APIContent  queryList(@ModelAttribute Basegroup basegroup,HttpServletRequest req ){
		logger.info("开始获取分组列表信息");
		try{
			List<Basegroup> listBasegroup = basegroupService.queryList(basegroup);
			return super.putAPIData(listBasegroup);
		}catch(Exception e){
			logger.info("获取获取分组列表信息失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		}
	}
}