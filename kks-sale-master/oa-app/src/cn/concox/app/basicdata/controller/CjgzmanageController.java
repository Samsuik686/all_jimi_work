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

import cn.concox.app.basicdata.service.CjgzmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Cjgzmanage;
import cn.concox.vo.commvo.CommonParam;

/**
 * <pre>
 * CjgzmanageController 故障管理
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class CjgzmanageController extends BaseController{
	
	Logger log=Logger.getLogger(CjgzmanageController.class);
	@Resource(name="cjgzmanageService")
	private CjgzmanageService cjgzmanageService;
	
	/*
	 * 获取初检故障列表，分页
	 */
	@RequestMapping("cjgzmanage/CjgzmanageList")
	@ResponseBody
	public APIContent getCjgzmanageListPage(@ModelAttribute Cjgzmanage cjgzmanage, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
//			int getCurrentPage=1;
//			if(!StringUtils.isBlank(req.getParameter("currentpage"))){
//				getCurrentPage=Integer.parseInt(req.getParameter("currentpage"));
//			}
			Page<Cjgzmanage> cjgzmanageListPage=cjgzmanageService.getCjgzmanageListPage(cjgzmanage, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", cjgzmanageListPage.getTotal());
			return super.putAPIData(cjgzmanageListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			log.info("获取初检故障列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 增加或修改方法
	 */
	@RequestMapping("cjgzmanage/addOrUpdateCjgzmanage")
	@ResponseBody
	public APIContent addOrUpdateCjgzmanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Cjgzmanage cjgzmanage){
		try {  
			if(cjgzmanageService.isExistsGid(cjgzmanage)!=null){
				if(cjgzmanageService.isExists(cjgzmanage)==0){
					if(cjgzmanage.getIid()>0){
						log.info("更新初检故障信息开始");
						cjgzmanageService.update(cjgzmanage);
						return new APIContent(Globals.OPERA_SUCCESS_CODE,"更新初检故障信息"+Globals.OPERA_SUCCESS_CODE_DESC);
					}else{
				    	  log.info("新增初检故障信息开始");
			    		  cjgzmanageService.insert(cjgzmanage);
			    		  return new APIContent(Globals.OPERA_SUCCESS_CODE,"新增初检故障"+Globals.OPERA_SUCCESS_CODE_DESC); 
				    }
				}else{
		    		  return super.operaStatus(Globals.OPERA_FAIL_CODE,"初检故障信息已存在,请检查" );
		    	}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,"初检故障类别不存在,请检查" );
			}
		} catch (Exception e) {
			log.info("更新或新增初检故障信息失败！");
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	/*
	 * 查询初检故障
	 */
	@RequestMapping("cjgzmanage/selectCjgzmanage")
	@ResponseBody
	public APIContent selectCjgzmanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Cjgzmanage cjgzmanage){
		log.info("初检故障查询开始");
		try {
			if(cjgzmanage.getIid()!=null){
				Cjgzmanage cjgzmanage1=cjgzmanageService.select(cjgzmanage);
				return super.putAPIData(new APIContent(Globals.OPERA_SUCCESS_CODE, "查询成功!", cjgzmanage1));
			}else{
				log.info("初检故障查询失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		
		} catch (Exception e) {
			log.info("初检故障查询失败！");
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	
	/*
	 * 删除初检故障
	 */
	@RequestMapping("cjgzmanage/deleteCjgzmanage")
	@ResponseBody
	public APIContent deleteCjgzmanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Cjgzmanage cjgzmanage){
		log.info("删除初检故障开始");
		try {
			if(cjgzmanageService.checkSupportDel(cjgzmanage)){
				cjgzmanageService.delete(cjgzmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"初检故障信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "初检故障信息存在关联数据，不可删除");
		} catch (Exception e) {
			log.info("删除初检故障失败！");
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * 导出初检故障信息列表
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("cjgzmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Cjgzmanage cjgzmanage,HttpServletRequest request,HttpServletResponse response){
		try{
			cjgzmanageService.ExportDatas(cjgzmanage,request,response);
		}catch(Exception e){
			e.printStackTrace();
			log.error("导出初检故障数据失败:"+e.toString());
		}
	}
	
	/**
	 * 列表查询不分页
	 */
	@RequestMapping("cjgzmanage/queryList")
	@ResponseBody
	public  APIContent queryList(@ModelAttribute Cjgzmanage cjgzmanage){
		log.info("列表查询开始");
		 try{
			List<Cjgzmanage> listCjgzmanage=cjgzmanageService.queryList(cjgzmanage);
			return super.putAPIData(listCjgzmanage);
		 }catch(Exception e){
			    log.info("获取初检故障列表失败:"+e.toString());
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		 }
	
	}
	
	/**
	 * 导入初检故障
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("cjgzmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = cjgzmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("导入初检故障信息失败:" + e.toString());
			errorList.add("导入初检故障信息失败:,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}
