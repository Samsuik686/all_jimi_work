package cn.concox.app.report.controller;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.concox.app.common.page.Page;
import cn.concox.app.material.service.MaterialLogService;
import cn.concox.app.report.service.XspjcostsReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.XspjcostsReport;

@Controller
@Scope("prototype")
public class XspjcostsReportController extends BaseController {
	private static Logger logger = Logger.getLogger(XspjcostsReportController.class);

	@Resource(name = "xspjcostsReportService")
	public XspjcostsReportService xspjcostsReportService;
	
	@Resource(name = "materialLogService")
	private MaterialLogService materialLogService;
	
	/**
	 * 根据查询条件显示数据
	 * @param xspjcostsReport
	 * @param request
	 */
	public void queryAbout(@ModelAttribute XspjcostsReport xspjcostsReport, HttpServletRequest request){
		String startTime=request.getParameter("startDate");
		if(!StringUtils.isBlank(startTime)){
			xspjcostsReport.setStartTime(startTime);
		}
		
		String endTime=request.getParameter("endDate");
		if(!StringUtils.isBlank(endTime)){
			xspjcostsReport.setEndTime(endTime);
		}
	}
	/**
	 * 分页查询
	 * 
	 * @param xspjcosts
	 * @param request
	 * @return
	 */
	@RequestMapping("xspjcosts/xspjcostsList")
	@ResponseBody
	public APIContent getXspjcostsListPage(@ModelAttribute XspjcostsReport xspjcostsReport, @ModelAttribute CommonParam comp, HttpServletRequest request) {
		try {
			queryAbout(xspjcostsReport,request);
			Page<XspjcostsReport> xspjcostsListPage = xspjcostsReportService.getXspjcostsListPage(xspjcostsReport, comp.getCurrentpage(), comp.getPageSize());
			request.getSession().setAttribute("totalValue",xspjcostsListPage.getTotal());
			return super.putAPIData(xspjcostsListPage.getResult());
		} catch (NumberFormatException e) {
			logger.error("查询销售配件收费信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询销售配件收费信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("xspjcosts/addOrUpdateXspjcosts")
	@ResponseBody
	public APIContent addOrUpdateXspjcosts(@ModelAttribute("xspjcosts") XspjcostsReport xspjcosts,HttpServletRequest request) {
		try {
			String saleTime=request.getParameter("saleTime");
			if(!StringUtils.isBlank(saleTime)){
				xspjcosts.setSaleDate(Timestamp.valueOf(saleTime));
			}
			String payTime=request.getParameter("payTime");
			if(!StringUtils.isBlank(payTime)){
				xspjcosts.setPayDate(Timestamp.valueOf(payTime));
			}
			if(xspjcostsReportService.isExists(xspjcosts)==0){
				if(xspjcosts.getSaleId() > 0){
					xspjcostsReportService.updateXspjcosts(xspjcosts);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "销售配件收费信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					xspjcosts.setCreateBy(super.getSessionUserName(request));
					xspjcostsReportService.insertXspjcosts(xspjcosts);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "销售配件收费信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "销售配件收费信息已存在,请检查" );
			}
		} catch (Exception e) {
			logger.error("更新销售配件收费信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新销售配件收费信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("xspjcosts/deleteXspjcosts")
	@ResponseBody
	public APIContent deleteXspjcosts(@ModelAttribute("xspjcosts") XspjcostsReport xspjcosts,HttpServletRequest request) {
		try {
			xspjcostsReportService.deleteXspjcosts(xspjcosts);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "销售配件收费信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("销售配件收费信息删除失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "销售配件收费信息删除" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("xspjcosts/getXspjcosts")
	@ResponseBody
	public APIContent getXspjcosts(@ModelAttribute("xspjcosts") XspjcostsReport xspjcosts,HttpServletRequest request) {
		try {
			XspjcostsReport c = xspjcostsReportService.getXspjcosts(xspjcosts);
			return super.putAPIData(c);
		} catch (Exception e) {
			logger.error("获取销售配件收费信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取销售配件收费信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("xspjcosts/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("xspjcosts") XspjcostsReport xspjcosts,HttpServletRequest request) {
		try {
			List<XspjcostsReport> c = xspjcostsReportService.queryList(xspjcosts);
			return super.putAPIData(c);
		} catch (Exception e) {
			logger.error("获取销售配件收费信息列表失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取销售配件收费信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导出销售配件收费数据
	 * 
	 * @param recharge
	 * @param request
	 * @param response
	 */
	@RequestMapping("xspjcosts/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute XspjcostsReport xspjcosts,HttpServletRequest request, HttpServletResponse response) {
		try {
			queryAbout(xspjcosts,request);
			xspjcostsReportService.ExportDatas(xspjcosts, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出销售配件收费数据失败:" + e.toString());
		}
	}
	
	/**
	 * 导入销售配件收费
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("xspjcosts/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = xspjcostsReportService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入销售配件收费信息失败:" + e.toString());
			errorList.add("导入销售配件收费信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}
