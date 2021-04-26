
package cn.concox.app.basicdata.controller;


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

import cn.concox.app.basicdata.service.SjfjmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Sjfjmanage;
import cn.concox.vo.commvo.CommonParam;

/**
 * <pre>
 * SjfjmanageController 随机附件管理
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class SjfjmanageController  extends BaseController {
	Logger log=Logger.getLogger(SjfjmanageController.class);
	@Resource(name="sjfjmanageService")
	private SjfjmanageService sjfjmanageService;
	
	/*
	 * 获取随机附件列表，分页
	 */
	@RequestMapping("sjfjmanage/sjfjmanageList")
	@ResponseBody
	public APIContent getSjfjmanageListPage(@ModelAttribute Sjfjmanage sjfjmanage, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
//			int getCurrentPage=1;
//			if(!StringUtils.isBlank(req.getParameter("currentpage"))){
//				getCurrentPage=Integer.parseInt(req.getParameter("currentpage"));
//			}
			Page<Sjfjmanage> sjfjmanageListPage=sjfjmanageService.getSjfjmanageListPage(sjfjmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", sjfjmanageListPage.getTotal());
			return super.putAPIData(sjfjmanageListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取随机附件列表失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/*
	 * 查询随机附件
	 */
	@RequestMapping("sjfjmanage/selectSjfjmanage")
	@ResponseBody
	public APIContent selectSjfjmanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Sjfjmanage sjfjmanage){
		log.info("随机附件查询开始");
		try {
			if(sjfjmanage.getEid()!=null){
				Sjfjmanage sjfjmanage1=sjfjmanageService.select(sjfjmanage);
				return super.putAPIData(new APIContent(Globals.OPERA_SUCCESS_CODE, "查询成功!", sjfjmanage1));
			}else{
				log.error("随机附件查询失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			log.error("随机附件查询失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/*
	 * 更新和新增随机附件
	 */
	@RequestMapping("sjfjmanage/addOrUpdateSjfjmanage")
	@ResponseBody  
	public APIContent addOrUpdateSjfjmanage(HttpServletRequest req,@ModelAttribute Sjfjmanage sjfjmanage){
		log.info("更新随机附件信息开始");
		try {
			if(sjfjmanageService.isExistsGid(sjfjmanage)!=null){
				if(sjfjmanageService.isExists(sjfjmanage)==0){
					if(sjfjmanage.getEid()>0){
						sjfjmanageService.update(sjfjmanage);
						return new APIContent(Globals.OPERA_SUCCESS_CODE,"更新随机附件信息"+Globals.OPERA_SUCCESS_CODE_DESC);
					}else{
						sjfjmanageService.insert(sjfjmanage);
						return new APIContent(Globals.OPERA_SUCCESS_CODE,"新增随机附件"+Globals.OPERA_SUCCESS_CODE_DESC);
				    }
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "随机附件信息已存在,请检查" );
		    	}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,"随机附件类别不存在,请检查" );
			}
		} catch (Exception e) {
			log.error("更新随机附件信息失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 删除随机附件
	 */
	@RequestMapping("sjfjmanage/deleteSjfjmanage")
	@ResponseBody
	public APIContent deleteSjfjmanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Sjfjmanage sjfjmanage){
		log.info("删除随机附件开始");
		try {
			if(sjfjmanageService.checkSupportDel(sjfjmanage)){
				sjfjmanageService.detele(sjfjmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"随机附件信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "随机附件存在关联数据，不可删除");
		} catch (Exception e) {
			log.error("删除随机附件失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * 导出随机附件数据
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("sjfjmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Sjfjmanage sjfjmanage,HttpServletRequest request,HttpServletResponse response){
		try{
			sjfjmanageService.ExportDatas(sjfjmanage,request,response);
		}catch(Exception e){
			log.error("导出随机附件数据失败:",e);
		}
	}
	
	/**
	 * 列表查询不分页
	 */
	@RequestMapping("sjfjmanage/queryList")
	@ResponseBody
	public  APIContent queryList(@ModelAttribute Sjfjmanage sjfjmanage){
		log.info("列表查询开始");
		 try{
			List<Sjfjmanage> listSjfjmanage=sjfjmanageService.queryList(sjfjmanage);
			return super.putAPIData(listSjfjmanage);
		 }catch(Exception e){
			log.error("获取随机附件列表失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		 }
	
	}
	
	/**
	 * 导入随机附件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("sjfjmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList =sjfjmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			log.error("导入随机附件信息失败:",e);
			errorList.add("导入随机附件信息失败:,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}
