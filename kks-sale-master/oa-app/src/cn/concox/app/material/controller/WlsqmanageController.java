package cn.concox.app.material.controller;

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
import cn.concox.app.material.service.WlsqmanageService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.material.Wlsqmanage;

@Controller
@Scope("prototype")
public class WlsqmanageController extends BaseController {
	private static Logger log = Logger.getLogger(WlsqmanageController.class);

	@Resource(name = "wlsqmanageService")
	public WlsqmanageService wlsqmanageService;

	/**
	 * 根据日期查询条件显示数据
	 * @param xspjcostsReport
	 * @param request
	 */
	public void queryAbout(Wlsqmanage wlsqmanage, HttpServletRequest request){
		String startTime=request.getParameter("startDate");
		if(!StringUtils.isBlank(startTime)){
			wlsqmanage.setStartTime(startTime);
		}
		
		String endTime=request.getParameter("endDate");
		if(!StringUtils.isBlank(endTime)){
			wlsqmanage.setEndTime(endTime);
		}
	}
	/**
	 * 分页查询
	 * 
	 * @param wlsqmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("wlsqmanage/wlsqmanageList")
	@ResponseBody
	public APIContent getWlsqmanageListPage(@ModelAttribute Wlsqmanage wlsqmanage, @ModelAttribute CommonParam comp,HttpServletRequest request) {
		try {
			queryAbout(wlsqmanage, request);
			Page<Wlsqmanage> wlsqmanageListPage = wlsqmanageService.getWlsqmanageListPage(wlsqmanage, comp.getCurrentpage(),comp.getPageSize());
			request.getSession().setAttribute("totalValue",wlsqmanageListPage.getTotal());
			return super.putAPIData(wlsqmanageListPage.getResult());
		} catch (NumberFormatException e) {
			log.error("查询物料申请信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询物料申请信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("wlsqmanage/insertWlsqmanage")
	@ResponseBody
	public APIContent insertWlsqmanage(@ModelAttribute("wlsqmanage") Wlsqmanage wlsqmanage, HttpServletRequest request) {
		try {
			String s=request.getParameter("appDate");
			if(!StringUtils.isBlank(s)){
				wlsqmanage.setAppTime(Timestamp.valueOf(s));
			}
			wlsqmanage.setApplicater(super.getSessionUserName(request));
			wlsqmanageService.insertWlsqmanage(wlsqmanage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物料申请信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			log.error("物料申请信息增加失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "物料申请信息增加" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("wlsqmanage/updateWlsqmanage")
	@ResponseBody
	public APIContent updatewlsqmanage(@ModelAttribute("wlsqmanage") Wlsqmanage wlsqmanage, HttpServletRequest request) {
		try {
			wlsqmanageService.updateWlsqmanage(wlsqmanage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物料申请信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			log.info("物料申请信息修改失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "物料申请信息修改" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("wlsqmanage/deleteWlsqmanage")
	@ResponseBody
	public APIContent deleteWlsqmanage(@ModelAttribute("wlsqmanage") Wlsqmanage wlsqmanage, HttpServletRequest request) {
		try {
			wlsqmanageService.deleteWlsqmanage(wlsqmanage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物料申请信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			log.error("物料申请信息删除失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "物料申请信息删除" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("wlsqmanage/getWlsqmanage")
	@ResponseBody
	public APIContent getWlsqmanage(@ModelAttribute("wlsqmanage") Wlsqmanage wlsqmanage, HttpServletRequest request) {
		try {
			Wlsqmanage c = wlsqmanageService.getWlsqmanage(wlsqmanage);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取物料申请信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取物料申请信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("wlsqmanage/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("wlsqmanage") Wlsqmanage wlsqmanage, HttpServletRequest request) {
		try {
			List<Wlsqmanage> c = wlsqmanageService.queryList(wlsqmanage);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取物料申请信息列表失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取物料申请信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 导出物料申请数据
	 * 
	 * @param wlsqmanage
	 * @param request
	 * @param response
	 */
	@RequestMapping("wlsqmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Wlsqmanage wlsqmanage, HttpServletRequest request, HttpServletResponse response) {
		try {
			queryAbout(wlsqmanage, request);
			wlsqmanageService.ExportDatas(wlsqmanage, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出物料申请数据失败:" + e.toString());
		}
	}

	/**
	 * 导入物料申请
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("wlsqmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = wlsqmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入物料申请信息失败:" + e.toString());
			errorList.add("导入物料申请信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}
