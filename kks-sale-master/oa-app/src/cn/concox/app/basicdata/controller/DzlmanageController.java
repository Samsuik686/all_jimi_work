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

import cn.concox.app.basicdata.service.DzlmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Dzlmanage;
import cn.concox.vo.commvo.CommonParam;
/**
 * 电子料管理控制 
 */
@Controller
@Scope("prototype")
public class DzlmanageController extends BaseController {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="dzlmanageService")
	private DzlmanageService dzlmanageService;
	
	/**
	 * 电子料分页查询
	 * @param dzlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("dzlmanage/dzlmanageList")
	@ResponseBody
	public APIContent getDzlmanageList(@ModelAttribute Dzlmanage dzlmanage, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			Page<Dzlmanage> dzlListPage=dzlmanageService.getDzlmanageList(dzlmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", dzlListPage.getTotal());
			return super.putAPIData(dzlListPage.getResult());
		}catch(Exception e){
			logger.error("获取电子料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取单个电子料详情
	 * @param dzlmanage 
	 * @param req
	 * @return
	 */
	@RequestMapping("dzlmanage/getDzlInfo")
	@ResponseBody
	public APIContent getDzlInfo(@ModelAttribute Dzlmanage dzlmanage,HttpServletRequest req){
		try{
			dzlmanage = dzlmanageService.getDzlInfo(dzlmanage);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, dzlmanage);      
		}catch(Exception e){
			logger.error("新增电子料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增 / 修改电子料信息 
	 * @param dzlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("dzlmanage/addOrUpdateDzlInfo")
	@ResponseBody
	public APIContent addOrUpdateDzlInfo(@ModelAttribute Dzlmanage dzlmanage,HttpServletRequest req){
		try{
			if(dzlmanageService.isExistsGid(dzlmanage)!=null){
				if(dzlmanageService.isExists(dzlmanage) == 0){
					if(dzlmanage.getDzlId()>0){
						dzlmanageService.updateDzlInfo(dzlmanage);
						return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改电子料信息"+Globals.OPERA_SUCCESS_CODE_DESC);
					}else{
						dzlmanageService.insertDzlInfo(dzlmanage);
						return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"新增电子料信息"+Globals.OPERA_SUCCESS_CODE_DESC);
					}
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "电子料信息已存在,请检查" );
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "电子料类别不存在,请检查" );
			}
		}catch(Exception e){
			logger.error("更新电子料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除电子料信息
	 * @param dzlmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("dzlmanage/deleteDzlInfo")
	@ResponseBody
	public APIContent deleteDzlInfo(@ModelAttribute Dzlmanage dzlmanage,HttpServletRequest req){
		try{
			if(dzlmanageService.checkSupportDel(dzlmanage) > 0){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "该电子料存在关联数据，不可删除");
			}
			dzlmanageService.deleteDzlInfo(dzlmanage);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"电子料信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			
		}catch(Exception e){
			logger.error("删除电子料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * 导入电子料
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("dzlmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = dzlmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入电子料信息失败:" + e.toString());
			errorList.add("导入电子料信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
	
	/**
	 * 导出电子料数据
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("dzlmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Dzlmanage dzlmanage,HttpServletRequest request,HttpServletResponse response){
		try{
			dzlmanageService.ExportDatas(dzlmanage,request,response);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出电子料数据失败:"+e.toString());
		}
	}
}
