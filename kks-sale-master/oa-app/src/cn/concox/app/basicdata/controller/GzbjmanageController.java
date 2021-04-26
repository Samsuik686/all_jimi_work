package cn.concox.app.basicdata.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.service.GzbjmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Gzbjmanage;
import cn.concox.vo.commvo.CommonParam;

/**
 * <pre>
 * 故障报价控制层
 * </pre>
 */
@Controller
@Scope("prototype")
public class GzbjmanageController extends BaseController {
	private static Logger log = Logger.getLogger(GzbjmanageController.class);

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "gzbjmanageService")
	private GzbjmanageService gzbjmanageService;

	/**
	 * 故障报价分页查询
	 * 
	 * @param gzbjmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("gzbjmanage/gzbjmanageList")
	@ResponseBody
	public APIContent getGzbjmanageListPage(@ModelAttribute Gzbjmanage gzbjmanage, @ModelAttribute CommonParam comp, HttpServletRequest req) {
		try {
			Page<Gzbjmanage> dzlListPage = gzbjmanageService.getGzbjmanageListPage(gzbjmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", dzlListPage.getTotal());
			return super.putAPIData(dzlListPage.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取故障报价数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 更新 故障报价信息
	 * 
	 * @param gzbjmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("gzbjmanage/addOrUpdateGzbjmanage")
	@ResponseBody
	public APIContent addOrUpdateGzbjmanage(@ModelAttribute Gzbjmanage gzbjmanage, HttpServletRequest req) {
		try {
			if(gzbjmanageService.isExists(gzbjmanage) == 0){
				if(gzbjmanage.getId() > 0){
					gzbjmanageService.updateGzbjmanage(gzbjmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "故障报价信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					gzbjmanageService.insertGzbjmanage(gzbjmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "故障报价信息新增"+ Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "故障报价信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新故障报价数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新故障报价数据" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 新增 故障报价信息
	 * 
	 * @param gzbjmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("gzbjmanage/insertGzbjmanage")
	@ResponseBody
	public APIContent insertGzbjmanage(@ModelAttribute Gzbjmanage gzbjmanage, HttpServletRequest req) {
		try {
			if(gzbjmanageService.isExists(gzbjmanage) == 0){
				gzbjmanageService.insertGzbjmanage(gzbjmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "故障报价信息新增"+ Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "故障报价信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新故障报价数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 修改故障报价信息
	 * 
	 * @param gzbjmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("gzbjmanage/updateGzbjmanage")
	@ResponseBody
	public APIContent updateGzbjmanage(@ModelAttribute Gzbjmanage gzbjmanage, HttpServletRequest req) {
		try {
			if(gzbjmanageService.isExists(gzbjmanage) == 0){
				gzbjmanageService.updateGzbjmanage(gzbjmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "故障报价信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "故障报价信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新故障报价数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除故障报价信息
	 * 
	 * @param gzbjmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("gzbjmanage/deleteGzbjmanage")
	@ResponseBody
	public APIContent deleteGzbjmanage(@ModelAttribute Gzbjmanage gzbjmanage, HttpServletRequest req) {
		try {
			if(gzbjmanageService.checkSupportDel(gzbjmanage) > 0){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "该故障报价存在关联数据，不可删除");
			}
			gzbjmanageService.deleteGzbjmanage(gzbjmanage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "故障报价信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			log.error("删除故障报价数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 获取故障报价详情
	 * 
	 * @param gzbjmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("gzbjmanage/getGzbjmanage")
	@ResponseBody
	public APIContent getGzbjmanage(@ModelAttribute Gzbjmanage gzbjmanage, HttpServletRequest req) {
		try {
			Gzbjmanage	z = gzbjmanageService.getGzbjmanage(gzbjmanage);
			return super.putAPIData(z);
		} catch (Exception e) {
			log.error("获取故障报价数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("gzbjmanage/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("gzbjmanage") Gzbjmanage gzbjmanage, HttpServletRequest request) {
		try {
			List<Gzbjmanage> c = gzbjmanageService.getGzbjmanageList(gzbjmanage);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取所有故障报价信息列表失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取故障报价信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导出故障报价数据
	 * 
	 * @param gzbjmanage
	 * @param request
	 * @param response
	 */
	@RequestMapping("gzbjmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Gzbjmanage gzbjmanage, HttpServletRequest request, HttpServletResponse response) {
		try {
			gzbjmanageService.ExportDatas(gzbjmanage, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出故障报价数据失败:",e);
		}
	}

	/**
	 * 导入故障报价
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("gzbjmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = gzbjmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入故障报价信息失败:" + e.toString());
			errorList.add("导入故障报价信息失败，请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
	
	/**
	 * 维修报价获得故障报价价格
	 * @param req
	 * @return
	 */
	@RequestMapping("gzbjmanage/getSumPriceByIds")
	@ResponseBody
	public APIContent getSumPriceByIds(HttpServletRequest req){
		try{
			BigDecimal price = BigDecimal.ZERO ;
			String idsTemp = req.getParameter("ids");
			if(!StringUtil.isRealEmpty(idsTemp)){
				String[] ids = idsTemp.split(",");
				price = gzbjmanageService.getSumPriceByIds(ids);
			}
			return super.putAPIData(price);
		}catch(Exception e){
			logger.error("获取故障报价价格失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}