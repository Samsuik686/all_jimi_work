package cn.concox.app.basicdata.controller;

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

import cn.concox.app.basicdata.service.ZjppmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Zjppmanage;
import cn.concox.vo.commvo.CommonParam;

/**
 * <pre>
 * 终检型号匹配控制层
 * </pre>
 */
@Controller
@Scope("prototype")
public class ZjppmanageController extends BaseController {
	private static Logger log = Logger.getLogger(ZjppmanageController.class);

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "zjppmanageService")
	private ZjppmanageService zjppmanageService;

	/**
	 * 终检型号匹配分页查询
	 * 
	 * @param zjppmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zjppmanage/zjppmanageList")
	@ResponseBody
	public APIContent getZjppmanageListPage(@ModelAttribute Zjppmanage zjppmanage, @ModelAttribute CommonParam comp, HttpServletRequest req) {
		try {
//			int getCurrentPage = 1;
//			if (!StringUtils.isBlank(req.getParameter("currentpage"))) {
//				getCurrentPage = Integer.parseInt(req.getParameter("currentpage"));
//			}
			Page<Zjppmanage> dzlListPage = zjppmanageService.getZjppmanageListPage(zjppmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", dzlListPage.getTotal());
			return super.putAPIData(dzlListPage.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取终检型号匹配数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 更新 终检型号匹配信息
	 * 
	 * @param zjppmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zjppmanage/addOrUpdateZjppmanage")
	@ResponseBody
	public APIContent addOrUpdateZjppmanage(@ModelAttribute Zjppmanage zjppmanage,HttpServletRequest req) {
		try {
			if(zjppmanageService.isExists(zjppmanage)==0){
				if(zjppmanage.getMatchId() > 0){
					zjppmanageService.updateZjppmanage(zjppmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "终检型号匹配信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					zjppmanageService.insertZjppmanage(zjppmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "终检型号匹配信息新增"+ Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "终检型号匹配信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新终检型号匹配数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,"更新终检型号匹配数据"+Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 新增 终检型号匹配信息
	 * 
	 * @param zjppmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zjppmanage/insertZjppmanage")
	@ResponseBody
	public APIContent insertZjppmanage(@ModelAttribute Zjppmanage zjppmanage,HttpServletRequest req) {
		try {
			if(zjppmanageService.isExists(zjppmanage)==0){
				zjppmanageService.insertZjppmanage(zjppmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "终检型号匹配信息新增"+ Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "终检型号匹配信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新终检型号匹配数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 修改终检型号匹配信息
	 * 
	 * @param zjppmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zjppmanage/updateZjppmanage")
	@ResponseBody
	public APIContent updateZjppmanage(@ModelAttribute Zjppmanage zjppmanage,HttpServletRequest req) {
		try {
			if(zjppmanageService.isExists(zjppmanage)==0){
				zjppmanageService.updateZjppmanage(zjppmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "终检型号匹配信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "终检型号匹配信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新终检型号匹配数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除终检型号匹配信息
	 * 
	 * @param zjppmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zjppmanage/deleteZjppmanage")
	@ResponseBody
	public APIContent deleteZjppmanage(@ModelAttribute Zjppmanage zjppmanage,HttpServletRequest req) {
		try {
			zjppmanageService.deleteZjppmanage(zjppmanage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "终检型号匹配信息删除"+ Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			log.error("删除终检型号匹配数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 获取终检型号匹配详情
	 * 
	 * @param zjppmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zjppmanage/getZjppmanage")
	@ResponseBody
	public APIContent getZjppmanage(@ModelAttribute Zjppmanage zjppmanage,HttpServletRequest req) {
		try {
			Zjppmanage	z = zjppmanageService.getZjppmanage(zjppmanage);
			return super.putAPIData(z);
		} catch (Exception e) {
			log.error("获取终检型号匹配数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取终检型号匹配详情
	 * 
	 * @param zjppmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("zjppmanage/getZjppListByModel")
	@ResponseBody
	public APIContent getZjppListByModel(@ModelAttribute Zjppmanage zjppmanage,HttpServletRequest req) {
		try {
			List<Zjppmanage> zList = zjppmanageService.getZjppListByModel(zjppmanage);
			return super.putAPIData(zList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("按型号获取终检型号匹配数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("zjppmanage/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("zjppmanage") Zjppmanage zjppmanage,HttpServletRequest request) {
		try {
			List<Zjppmanage> c = zjppmanageService.queryList(zjppmanage);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取所有终检型号匹配信息列表失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取终检型号匹配信息列表"
					+ Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导出终检型号匹配数据
	 * 
	 * @param zjppmanage
	 * @param request
	 * @param response
	 */
	@RequestMapping("zjppmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Zjppmanage zjppmanage,HttpServletRequest request, HttpServletResponse response) {
		try {
			zjppmanageService.ExportDatas(zjppmanage, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出终检型号匹配数据失败:",e);
		}
	}

	/**
	 * 导入终检型号匹配
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("zjppmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = zjppmanageService.ImportDatas(file,request);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入终检型号匹配信息失败:" + e.toString());
			errorList.add("导入终检型号匹配信息失败，请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}