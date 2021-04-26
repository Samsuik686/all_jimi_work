package cn.concox.app.basicdata.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import cn.concox.app.basicdata.service.PjlmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.commvo.CommonParam;
/**
 * 配件料管理控制 
 */
@Controller
@Scope("prototype")
public class PjlmanageController extends BaseController {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="pjlmanageService")
	private PjlmanageService pjlmanageService;
	
	/**
	 * 配件料分页查询
	 * @param pjlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/pjlmanageList")
	@ResponseBody
	public APIContent getPjlmanageList(@ModelAttribute Pjlmanage pjlmanage, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			Page<Pjlmanage> pjlListPage=pjlmanageService.getPjlmanageList(pjlmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", pjlListPage.getTotal());
			return super.putAPIData(pjlListPage.getResult());
		}catch(Exception e){
			logger.error("获取配件料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取单个配件料详情
	 * @param pjlmanage 
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/getPjlInfo")
	@ResponseBody
	public APIContent getPjlInfo(@ModelAttribute Pjlmanage pjlmanage,HttpServletRequest req){
		try{
			pjlmanage = pjlmanageService.getPjlInfo(pjlmanage);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, pjlmanage);      
		}catch(Exception e){
			logger.error("获取配件料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增 / 修改配件料信息 
	 * @param pjlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/addOrUpdatePjlInfo")
	@ResponseBody
	public APIContent addOrUpdatePjlInfo(@ModelAttribute Pjlmanage pjlmanage,HttpServletRequest req){
		try{
			if(pjlmanageService.isExists(pjlmanage) == 0){
				if(pjlmanage.getPjlId()>0){
					pjlmanageService.updatePjlInfo(pjlmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改配件料信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					pjlmanageService.insertPjlInfo(pjlmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"新增配件料信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "配件料信息已存在,请检查" );
			}
		}catch(Exception e){
			logger.error("更新配件料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除配件料信息
	 * @param pjlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/deletePjlInfo")
	@ResponseBody
	public APIContent deletePjlInfo(@ModelAttribute Pjlmanage pjlmanage,HttpServletRequest req){
		try{
			if(pjlmanageService.checkSupportDel(pjlmanage) > 0){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "该配件料存在关联数据，不可删除");
			}
			pjlmanageService.deletePjlInfo(pjlmanage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"配件料信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			
		}catch(Exception e){
			logger.error("删除配件料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * 导入配件料
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("pjlmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = pjlmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入配件料信息失败:" + e.toString());
			errorList.add("导入配件料信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
	
	/**
	 * 导出配件料数据
	 * @param pjlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Pjlmanage pjlmanage,HttpServletRequest request,HttpServletResponse response){
		try{
			pjlmanageService.ExportDatas(pjlmanage,request,response);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出配件料数据失败:"+e.toString());
		}
	}
	
	
	/**
	 * 根据id配件料
	 * @param pjlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/getListByIds")
	@ResponseBody
	public APIContent getListByIds(HttpServletRequest req){
		try{
			List<Pjlmanage> pjlList = new ArrayList<Pjlmanage>();
			String idsTemp = req.getParameter("ids");
			if(!StringUtil.isRealEmpty(idsTemp)){
				String[] ids = idsTemp.split(",");
				pjlList = pjlmanageService.getListByIds(ids);
			}
			return super.putAPIData(pjlList);
		}catch(Exception e){
			logger.error("获取配件料匹配数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 维修和报价根据主板型号查询所有备选型号及本身型号
	 * @param pjlmanage 
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/getAllOherModel")
	@ResponseBody
	public APIContent getAllOherModel(@RequestParam("model") String model,HttpServletRequest req){
		try{
			Set<String> allOherModel = pjlmanageService.getAllOherModel(model);
			return super.putAPIData(allOherModel);      
		}catch(Exception e){
			logger.error("获取备选型号数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 维修报价获得配件价格
	 * @param pjlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("pjlmanage/getSumPriceByIds")
	@ResponseBody
	public APIContent getSumPriceByIds(HttpServletRequest req){
		try{
			BigDecimal price = BigDecimal.ZERO ;
			String idsTemp = req.getParameter("ids");
			if(!StringUtil.isRealEmpty(idsTemp)){
				String[] ids = idsTemp.split(",");
				price = pjlmanageService.getSumPriceByIds(ids);
			}
			return super.putAPIData(price);
		}catch(Exception e){
			logger.error("获取配件料价格失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * @Title: getPjlByPjlIds 
	 * @Description:配件料组合根据选中的配件料ids，获取配件料信息列表
	 * @param req
	 * @return 
	 * @author 20160308
	 * @date 2017年11月16日 下午4:55:13
	 */
	@RequestMapping("pjlmanage/getPjlByPjlIds")
	@ResponseBody
	public APIContent getPjlByPjlIds(HttpServletRequest req){
		try{
			List<Pjlmanage> pjlList = new ArrayList<Pjlmanage>();
			String idsTemp = req.getParameter("ids");
			if(!StringUtil.isRealEmpty(idsTemp)){
				String[] ids = idsTemp.split(",");
				pjlList = pjlmanageService.getPjlByPjlIds(ids);
			}
			return super.putAPIData(pjlList);
		}catch(Exception e){
			logger.error("获取配件料匹配数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
}
