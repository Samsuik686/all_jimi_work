package cn.concox.app.basicdata.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cn.concox.app.basicdata.service.ZbxhmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Zbxhmanage;
import cn.concox.vo.commvo.CommonParam;

@Controller
@Scope("prototype")
public class ZbxhmanageController extends BaseController {
	private static Logger log = Logger.getLogger(ZbxhmanageController.class);

	@Resource(name = "zbxhmanageService")
	public ZbxhmanageService zbxhmanageService;

	/**
	 * 分页查询
	 * 
	 * @param zbxhmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("zbxhmanage/zbxhmanageList")
	@ResponseBody
	public APIContent getZbxhmanageListPage(@ModelAttribute Zbxhmanage zbxhmanage, @ModelAttribute CommonParam comp, HttpServletRequest request) {
		try {
//			int getCurrentPage = 1;
//			if (!StringUtils.isBlank(request.getParameter("currentpage"))) {
//				getCurrentPage = Integer.parseInt(request.getParameter("currentpage"));
//			}
			//受理时ams系统匹配到市场型号，根据市场型号精确匹配多个主板型号
			String marketModelAccept= request.getParameter("marketModelAccept");
			if (!StringUtil.isRealEmpty(marketModelAccept)){
				zbxhmanage.setMarketModelAccept(marketModelAccept);
			}
			Page<Zbxhmanage> zbxhmanageListPage = zbxhmanageService.getZbxhmanageListPage(zbxhmanage, comp.getCurrentpage(), comp.getPageSize());
			request.getSession().setAttribute("totalValue", zbxhmanageListPage.getTotal());
			return super.putAPIData(zbxhmanageListPage.getResult());
		} catch (NumberFormatException e) {
			log.error("查询主板型号信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询主板型号信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("zbxhmanage/addOrUpdateZbxhmanage")
	@ResponseBody
	public APIContent addOrUpdateZbxhmanage(@ModelAttribute("zbxhmanage") Zbxhmanage zbxhmanage, HttpServletRequest request) {
		try {
			zbxhmanageService.transferData(zbxhmanage);
			//验证数据，型号类别需要在basegroup中存在。
			if(zbxhmanageService.isExistsGid(zbxhmanage)!=null ){
				//判断修改后或者新增的数据是否已经存在，如果已经存在则拒绝新增或修改
				if(zbxhmanageService.isExists(zbxhmanage)==0){
					if(zbxhmanage.getMId() > 0){
						zbxhmanageService.updateZbxhmanage(zbxhmanage);
						return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "主板型号信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
					}else{
						zbxhmanageService.insertZbxhmanage(zbxhmanage);
						return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "主板型号信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
					}
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号管理信息已存在,请检查");
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号类别不存在,请检查" );
			}
		} catch (Exception e) {
			log.error("更新主板型号信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新主板型号信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("zbxhmanage/insertZbxhmanage")
	@ResponseBody
	public APIContent insertZbxhmanage(@ModelAttribute("zbxhmanage") Zbxhmanage zbxhmanage, HttpServletRequest request) {
		try {
			zbxhmanageService.transferData(zbxhmanage);
			if(zbxhmanageService.isExistsGid(zbxhmanage)!=null){
				if(zbxhmanageService.isExists(zbxhmanage)==0){
					zbxhmanageService.insertZbxhmanage(zbxhmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "主板型号信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号管理信息已存在,请检查" );
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号类别不存在,请检查" );
			}
		} catch (Exception e) {
			log.error("主板型号信息增加失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号信息增加" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 添加主板型号，用于受理处
	 * @param zbxhmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("zbxhmanage/insertZbxhmanageByAccept")
	@ResponseBody
	public APIContent insertZbxhmanageByAccept(@ModelAttribute("zbxhmanage") Zbxhmanage zbxhmanage, HttpServletRequest request) {
		try {
			zbxhmanageService.transferData(zbxhmanage);
			if(zbxhmanageService.isExistsGid(zbxhmanage)!=null){
				if(zbxhmanageService.isExists(zbxhmanage)==0){
					if(zbxhmanage.getMId() !=0){
						zbxhmanageService.updateZbxhmanage(zbxhmanage);
					}else{
						zbxhmanageService.insertZbxhmanage(zbxhmanage);
					}
//					zbxhmanage = zbxhmanageService.getZbTypeBySctype(zbxhmanage.getMarketModel());
					List<Zbxhmanage> zbxh=zbxhmanageService.getZbTypeBySctype(zbxhmanage.getMarketModel());
					if(zbxh !=null && zbxh.size() > 0){
						if(zbxh.size()==1){
							zbxhmanage = zbxh.get(0);
						}
					}
					return new APIContent(Globals.OPERA_SUCCESS_CODE, "主板型号信息增加" + Globals.OPERA_SUCCESS_CODE_DESC,zbxhmanage);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号管理信息已存在,请检查" );
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号类别不存在,请检查" );
			}
		} catch (Exception e) {
			log.error("主板型号信息增加失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号信息增加" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("zbxhmanage/updateZbxhmanage")
	@ResponseBody
	public APIContent updateZbxhmanage(@ModelAttribute("zbxhmanage") Zbxhmanage zbxhmanage, HttpServletRequest request) {
		try {
			zbxhmanageService.transferData(zbxhmanage);
			if(zbxhmanageService.isExistsGid(zbxhmanage)!=null){
				if(zbxhmanageService.isExists(zbxhmanage)==0){
					zbxhmanageService.updateZbxhmanage(zbxhmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "主板型号信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号管理信息已存在,请检查" );
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号类别不存在,请检查" );
			}
		} catch (Exception e) {
			log.error("主板型号信息修改失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号信息修改" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("zbxhmanage/deleteZbxhmanage")
	@ResponseBody
	public APIContent deleteZbxhmanage(@ModelAttribute("zbxhmanage") Zbxhmanage zbxhmanage, HttpServletRequest request) {
		try {
			if(zbxhmanageService.checkSupportDel(zbxhmanage)){
				zbxhmanageService.deleteZbxhmanage(zbxhmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "主板型号信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
			}
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "该主板型号存在关联数据，不可删除");
		} catch (Exception e) {
			log.error("主板型号信息删除失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号信息删除" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("zbxhmanage/getZbxhmanage")
	@ResponseBody
	public APIContent getZbxhmanage(@ModelAttribute("zbxhmanage") Zbxhmanage zbxhmanage, HttpServletRequest request) {
		try {
			Zbxhmanage m = zbxhmanageService.getZbxhmanage(zbxhmanage);
			return super.putAPIData(m);
		} catch (Exception e) {
			log.error("获取主板型号信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取主板型号信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("zbxhmanage/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("zbxhmanage") Zbxhmanage zbxhmanage, HttpServletRequest request) {
		try {
			List<Zbxhmanage> c = zbxhmanageService.queryList(zbxhmanage);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取所有主板型号信息列表失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取主板型号信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 导出主板型号数据
	 * 
	 * @param zbxhmanage
	 * @param request
	 * @param response
	 */
	@RequestMapping("zbxhmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Zbxhmanage zbxhmanage, HttpServletRequest request, HttpServletResponse response) {
		try {
			zbxhmanageService.ExportDatas(zbxhmanage, request, response);
		} catch (Exception e) {
			log.error("导出主板型号数据失败:",e);
		}
	}

	/**
	 * 导入主板型号
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("zbxhmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = zbxhmanageService.ImportDatas(file,request);
		} catch (Exception e) {
			log.error("导入主板型号信息失败:",e);
			errorList.add("导入主板型号信息失败，请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}

	@RequestMapping("zbxhmanage/batchModify")
	@ResponseBody
	public APIContent batchModify(@RequestParam(value = "mids[]",required=false)List<Integer> mids,
								  HttpServletRequest request,HttpServletResponse response){
		try {
			int num = 0;
			Integer newGid = Integer.valueOf(request.getParameter("newGid"));
			Integer newCreateType = Integer.valueOf(request.getParameter("newCreateType"));
			if(newGid == null || newCreateType == null || newGid<=0 || newCreateType<=0){
				log.error("批量修改主板类型、创建类型错误");
				return super.operaStatus(Globals.PARAMERROR,"主板类型、创建类型参数错误");
			}

			if (mids == null || mids.size() == 0) {
				Zbxhmanage zbxhmanage = new Zbxhmanage();
				zbxhmanage.setMarketModel(request.getParameter("marketModel"));
				zbxhmanage.setModel( request.getParameter("model"));

				String createType = request.getParameter("createType").trim();
				zbxhmanage.setCreateType("".equals(createType) ? null:Integer.valueOf(createType));

				String gId = request.getParameter("gId").trim();
				zbxhmanage.setGId("".equals(gId) ? null:Integer.valueOf(gId));

				String enabledFlag = request.getParameter("enabledFlag").trim();
				zbxhmanage.setEnabledFlag("".equals(enabledFlag) ? null:Integer.valueOf(enabledFlag));

				List<Zbxhmanage> list = zbxhmanageService.queryList(zbxhmanage);
				num = zbxhmanageService.batchModifyTypeByMids(list,newGid,newCreateType);
				log.info("根据查询条件修改");
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,num+"主板数据批量修改成功");
			}
			if( mids != null && mids.size()>0){
				List<Zbxhmanage> list = zbxhmanageService.getZbxhmanagesByMids(mids);
				if(list!= null && list.size()>0)
					num = zbxhmanageService.batchModifyTypeByMids(list,newGid,newCreateType);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,num+"条主板数据批量修改成功");
			}
			log.error("主板类型数据无修改");
			return super.operaStatus(Globals.OPERA_FAIL_CODE,"无有效数据");
		}catch (Exception e) {
			log.error(e+"主板数据批量修改失败");
			return  super.operaStatus(Globals.OPERA_FAIL_CODE, e.getMessage()+","+Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
