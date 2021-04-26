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

import cn.concox.app.basicdata.service.RepairPriceTempService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.basicdata.RepairPriceTempManage;
import cn.concox.vo.commvo.CommonParam;

@Controller
@Scope("prototype")
public class RepairPriceTempController extends BaseController {	
	private static Logger log = Logger.getLogger(RepairPriceTempController.class);
	
	@Resource(name = "repairPriceTempService")
	public RepairPriceTempService repairPriceTempService;
	
	/**
	 * 分页查询
	 * 
	 * @param zbxhmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("repairPriceTemp/repairPriceList")
	@ResponseBody
	public APIContent getRepairPriceListPage(@ModelAttribute RepairPriceTempManage manage, @ModelAttribute CommonParam comp, HttpServletRequest request) {
		try {
			Page<RepairPriceTempManage> repairPriceListPage = repairPriceTempService.getManageList(manage, comp.getCurrentpage(), comp.getPageSize());
			request.getSession().setAttribute("totalValue", repairPriceListPage.getTotal());
			return super.putAPIData(repairPriceListPage.getResult());
		} catch (NumberFormatException e) {
			log.error("查询维修报价信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询维修报价信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获得单个信息
	 * 
	 * @param zbxhmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("repairPriceTemp/getRepairPriceInfo")
	@ResponseBody
	public APIContent getRepairPriceInfo(@ModelAttribute RepairPriceTempManage rp, HttpServletRequest request) {
		try {
			RepairPriceTempManage repairPrice = repairPriceTempService.getRepairPriceInfo(rp);
			return super.putAPIData(repairPrice);
		} catch (NumberFormatException e) {
			log.error("获得维修报价信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获得维修报价信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增，修改维修报价
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:22:20
	 * @param zbxhmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("repairPriceTemp/addOrUpdateRepairPrice")
	@ResponseBody
	public APIContent addOrUpdateZbxhmanage(@ModelAttribute RepairPriceTempManage manage, HttpServletRequest request) {
		try {
				//修改
//			if(repairPriceTempService.isExists(manage)==0){  先去掉这个校验，那边会出现重复数据
				if(manage.getRid() != null && manage.getRid() > 0){
					repairPriceTempService.updateRepairPrice(manage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "维修报价信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					repairPriceTempService.insertRepairPrice(manage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "维修报价信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
				}
//			}else{
//				return super.operaStatus(Globals.OPERA_FAIL_CODE, "主板型号和编码已存在,请检查" );
//			}
			
		} catch (Exception e) {
			log.error("更新维修信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新维修报价信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除维修报价信息
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:52:34
	 * @param zbxhmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("repairPriceTemp/deleteRepairPrice")
	@ResponseBody
	public APIContent deleteRepairPrice(@ModelAttribute RepairPriceTempManage manage, HttpServletRequest request) {
		try {
			if(repairPriceTempService.checkSupportDel(manage) > 0 ){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "该维修报价存在关联数据，不可删除");				
			}
			repairPriceTempService.deleteRepairPrice(manage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "维修报价信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);			
		} catch (Exception e) {
			log.error("主板型号信息删除失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "主维修报价信息删除" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导入维修报价信息
	 * @author TangYuping
	 * @version 2016年11月26日 下午6:02:32
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("repairPriceTemp/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = repairPriceTempService.ImportDatas(file,request);
		} catch (Exception e) {
			log.error("维修报价导入信息失败:",e);
			errorList.add("维修报价导入信息失败，请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
	
	@RequestMapping("repairPriceTemp/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute RepairPriceTempManage manage, HttpServletRequest request, HttpServletResponse response) {
		try {
			repairPriceTempService.ExportDatas(manage, request, response);
		} catch (Exception e) {
			log.error("导出维修报价数据失败:",e);
		}
	}
	
	/**
	 * 选择维修报价自动选择物料
	 * 
	 * @param 
	 * @param request
	 * @return
	 */
	@RequestMapping("repairPriceTemp/getPjlByWxbjId")
	@ResponseBody
	public APIContent getPjlByWxbjId(HttpServletRequest req) {
		try {
			List<Pjlmanage> pjlList = new ArrayList<Pjlmanage>();
			String idsTemp = req.getParameter("ids");
			if (null != idsTemp && !StringUtil.isEmpty(idsTemp)) {
				String[] ids = idsTemp.split(",");
				pjlList = repairPriceTempService.getPjlByWxbjId(ids);
			}
			return super.putAPIData(pjlList);
		} catch (NumberFormatException e) {
			log.error("获得维修报价信息失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获得维修报价信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
