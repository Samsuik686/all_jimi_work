package cn.concox.app.basicdata.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jimi.trackersoa.core.util.StringUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.concox.app.basicdata.service.YcfkmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Ycfkmanage;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.wechat.uti.pay.DateUtil;

/**
 * <pre>
 * YcfkmanageController 400电话记录管理
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class YcfkmanageController extends BaseController {
	Logger log=Logger.getLogger(YcfkmanageController.class);
	
	@Resource(name="ycfkmanageService")
	private YcfkmanageService ycfkmanageService;
	
	/**
	 * 查询条件(日期)
	 * @param ycfkmanage
	 * @param req
	 */
	public void searchBy(Ycfkmanage ycfkmanage,HttpServletRequest req){
		String startTime=req.getParameter("startTime");
		String endTime=req.getParameter("endTime");
		if(!StringUtils.isBlank(startTime)){
			ycfkmanage.setStartTime(startTime);
		}
		if(!StringUtils.isBlank(endTime)){
			ycfkmanage.setEndTime(endTime);
		}
	}
	
	/*
	 * 获取400电话记录列表，分页
	 */
	@RequestMapping("ycfkmanage/ycfkmanageList")
	@ResponseBody
	public APIContent getYcfkmanageListPage(@ModelAttribute Ycfkmanage ycfkmanage, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			searchBy(ycfkmanage, req);
			Page<Ycfkmanage> ycfkmanageListPage=ycfkmanageService.getYcfkmanageListPage(ycfkmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", ycfkmanageListPage.getTotal());
			return super.putAPIData(ycfkmanageListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取400电话记录列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 查询400电话记录
	 */
	@RequestMapping("ycfkmanage/selectYcfkmanage")
	@ResponseBody
	public APIContent selectYcfkmanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Ycfkmanage ycfkmanage){
		log.info("400电话记录查询开始");
		try {
			if(ycfkmanage.getBackId()!=null){
				Ycfkmanage ycfkmanage1=ycfkmanageService.select(ycfkmanage);
				return super.putAPIData(new APIContent(Globals.OPERA_SUCCESS_CODE, "查询成功!", ycfkmanage1));
			}else{
				log.error("400电话记录查询失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			log.error("400电话记录查询失败！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 更新和新增400电话记录
	 */
	@RequestMapping("ycfkmanage/addOrUpdateYcfkmanage")
	@ResponseBody
	public APIContent addOrUpdateYcfkmanage(HttpServletRequest req,@ModelAttribute Ycfkmanage ycfkmanage){
		log.info("更新400电话记录信息开始");
		try {
			String feedBackTime=req.getParameter("feedBackTime");
			String completionTime=req.getParameter("completionTime");
			if(!StringUtils.isBlank(feedBackTime)){
				ycfkmanage.setFeedBackDate(Timestamp.valueOf(feedBackTime));
			}
			if(!StringUtils.isBlank(completionTime)){
				ycfkmanage.setCompletionDate(Timestamp.valueOf(completionTime));
			}
			
			if(ycfkmanage.getBackId()>0){
				if(ycfkmanageService.isExists(ycfkmanage)==0){
				
				/*	处理措施列表暂不使用
				 * Ycfkmanage old = ycfkmanageService.select(ycfkmanage);
					
					if(!StringUtil.isBlank(ycfkmanage.getMeasures())){
						ycfkmanage.setMeasures(old.getMeasures() + generateMeasuresTxt(req, ycfkmanage.getMeasures()));
					}else{
						ycfkmanage.setMeasures(old.getMeasures());
					}*/
					ycfkmanageService.update(ycfkmanage);
					return new APIContent(Globals.OPERA_SUCCESS_CODE,"更新400电话记录信息成功"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "400电话记录信息已存在,请检查" );
				}
			}else{
				if(ycfkmanageService.isExists(ycfkmanage)==0){
//	处理措施列表暂不使用			
//					ycfkmanage.setMeasures(generateMeasuresTxt(req, ycfkmanage.getMeasures()));
					if(ycfkmanage.getCompletionState()==null||"".equals(ycfkmanage.getCompletionState())){
						ycfkmanage.setCompletionState(1); //不选状态 默认已完成
					}
					ycfkmanageService.insert(ycfkmanage);
					return new APIContent(Globals.OPERA_SUCCESS_CODE,"新增400电话记录信息成功"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "400电话记录信息已存在,请检查" );
				}
			}
		} catch (Exception e) {
			log.error("更新400电话记录信息失败！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	//处理措施列表暂不使用
	private String generateMeasuresTxt(HttpServletRequest req,String measures){
		
		StringBuffer sb = new StringBuffer("<span>");
		sb.append(measures);
		sb.append(" | " + super.getSessionUserName(req));
		sb.append(" | " + DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
		sb.append("</span><br>");
		return sb.toString();
	}
	
	/*
	 * 删除400电话记录
	 */
	@RequestMapping("ycfkmanage/deleteYcfkmanage")
	@ResponseBody
	public APIContent deleteYcfkmanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Ycfkmanage ycfkmanage){
		log.info("删除400电话记录开始");
		try {
			if(ycfkmanage.getBackId()!=null){
				ycfkmanageService.detele(ycfkmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"故障信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				log.error("删除400电话记录失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			log.error("删除400电话记录！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导出400电话记录信息数据
	 * @param Ycfkmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("ycfkmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Ycfkmanage ycfkmanage,HttpServletRequest request,HttpServletResponse response){
		try{
			searchBy(ycfkmanage, request);
			ycfkmanageService.ExportDatas(ycfkmanage,request,response);
		}catch(Exception e){
			e.printStackTrace();
			log.error("导出400电话记录信息数据失败:"+e.toString());
		}
	}
	/**
	 * 列表查询不分页
	 */
	@RequestMapping("ycfkmanage/queryList")
	@ResponseBody
	public  APIContent queryList(@ModelAttribute Ycfkmanage ycfkmanage){
		log.info("列表查询开始");
		 try{
			List<Ycfkmanage> listYcfkmanage=ycfkmanageService.queryList(ycfkmanage);
			return super.putAPIData(listYcfkmanage);
		 }catch(Exception e){
		    log.error("获取400电话记录列表失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		 }
	}
	
	/**
	 * 导入400电话记录信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("ycfkmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = ycfkmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			log.error("导入400电话记录信息失败:",e);
			errorList.add("导入400电话记录信息失败:,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
	
	/**
	 * 400电话记录分类统计报表 
	 */
	@RequestMapping("ycfkmanage/ycfkCountList")
	@ResponseBody
	public APIContent ycfkCountList(@ModelAttribute Ycfkmanage ycfkmanage, HttpServletRequest request, HttpServletResponse response){
		log.info("400电话记录分类统计数据查询开始");
		 try{
			 searchBy(ycfkmanage, request);
			List<Ycfkmanage> listYcfkmanage=ycfkmanageService.ycfkCountList(ycfkmanage);
			return super.putAPIData(listYcfkmanage);
		 }catch(Exception e){
		    log.error("获取400电话记录分类统计数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		 }
	}
	
	/**
	 * 导出400电话记录分类统计报表
	 * @param Ycfkmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("ycfkmanage/ycfkExportDatas")
	@ResponseBody
	public void ycfkExportDatas(@ModelAttribute Ycfkmanage ycfkmanage,HttpServletRequest request,HttpServletResponse response){
		try{
			searchBy(ycfkmanage, request);
			ycfkmanageService.ycfkExportDatas(ycfkmanage,request,response);
		}catch(Exception e){
			e.printStackTrace();
			log.error("导出400电话记录信息数据失败:"+e.toString());
		}
	}
}
