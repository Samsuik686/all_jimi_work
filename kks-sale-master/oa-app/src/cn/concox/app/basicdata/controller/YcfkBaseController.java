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

import cn.concox.app.basicdata.service.YcfkBaseService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.YcfkBase;
import cn.concox.vo.commvo.CommonParam;

/**
 * <pre>
 * YcfkBaseController 故障管理
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class YcfkBaseController extends BaseController{
	
	Logger log=Logger.getLogger(YcfkBaseController.class);
	@Resource(name="ycfkBaseService")
	private YcfkBaseService ycfkBaseService;
	
	/*
	 * 获取异常反馈基础列表，分页
	 */
	@RequestMapping("ycfkBase/ycfkBaseList")
	@ResponseBody
	public APIContent getYcfkBaseListPage(@ModelAttribute YcfkBase ycfkBase, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			Page<YcfkBase> ycfkBaseListPage=ycfkBaseService.getYcfkBaseListPage(ycfkBase, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", ycfkBaseListPage.getTotal());
			return super.putAPIData(ycfkBaseListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			log.info("获取异常反馈基础列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 增加或修改方法
	 */
	@RequestMapping("ycfkBase/addOrUpdateYcfkBase")
	@ResponseBody
	public APIContent addOrUpdateYcfkBase(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute YcfkBase ycfkBase){
		try {  
			if(ycfkBaseService.isExistsGid(ycfkBase)!=null){
				if(ycfkBaseService.isExists(ycfkBase)==0){
					if(ycfkBase.getYid()>0){
						log.info("更新异常反馈基础信息开始");
						ycfkBaseService.update(ycfkBase);
						return new APIContent(Globals.OPERA_SUCCESS_CODE,"更新异常反馈基础信息"+Globals.OPERA_SUCCESS_CODE_DESC);
					}else{
				    	  log.info("新增异常反馈基础信息开始");
			    		  ycfkBaseService.insert(ycfkBase);
			    		  return new APIContent(Globals.OPERA_SUCCESS_CODE,"新增异常反馈基础"+Globals.OPERA_SUCCESS_CODE_DESC); 
				    }
				}else{
		    		  return super.operaStatus(Globals.OPERA_FAIL_CODE,"异常反馈基础信息已存在,请检查" );
		    	}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,"异常反馈基础类别不存在,请检查" );
			}
		} catch (Exception e) {
			log.info("更新或新增异常反馈基础信息失败！");
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	/*
	 * 查询异常反馈基础
	 */
	@RequestMapping("ycfkBase/selectYcfkBase")
	@ResponseBody
	public APIContent selectYcfkBase(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute YcfkBase ycfkBase){
		log.info("异常反馈基础查询开始");
		try {
			if(ycfkBase.getYid()!=null){
				YcfkBase ycfkBase1=ycfkBaseService.select(ycfkBase);
				return super.putAPIData(new APIContent(Globals.OPERA_SUCCESS_CODE, "查询成功!", ycfkBase1));
			}else{
				log.info("异常反馈基础查询失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		
		} catch (Exception e) {
			log.info("异常反馈基础查询失败！");
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	
	/*
	 * 删除异常反馈基础
	 */
	@RequestMapping("ycfkBase/deleteYcfkBase")
	@ResponseBody
	public APIContent deleteYcfkBase(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute YcfkBase ycfkBase){
		log.info("删除异常反馈基础开始");
		try {
			if(ycfkBaseService.checkSupportDel(ycfkBase)){
				ycfkBaseService.delete(ycfkBase);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"异常反馈基础信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "异常反馈基础信息存在关联数据，不可删除");
		} catch (Exception e) {
			log.info("删除异常反馈基础失败！");
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * 导出异常反馈基础信息列表
	 * @param Zgzmanage
	 * @param req
	 * @return
	 */
	@RequestMapping("ycfkBase/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute YcfkBase ycfkBase,HttpServletRequest request,HttpServletResponse response){
		try{
			ycfkBaseService.ExportDatas(ycfkBase,request,response);
		}catch(Exception e){
			e.printStackTrace();
			log.error("导出异常反馈基础数据失败:"+e.toString());
		}
	}
	
	/**
	 * 列表查询不分页
	 */
	@RequestMapping("ycfkBase/queryList")
	@ResponseBody
	public  APIContent queryList(@ModelAttribute YcfkBase ycfkBase){
		log.info("列表查询开始");
		 try{
			List<YcfkBase> listYcfkBase=ycfkBaseService.queryList(ycfkBase);
			return super.putAPIData(listYcfkBase);
		 }catch(Exception e){
			    log.info("获取异常反馈基础列表失败:"+e.toString());
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		 }
	
	}
	
	/**
	 * 根据gid查询问题描述list
	 */
	@RequestMapping("ycfkBase/getProDetailsByGid")
	@ResponseBody
	public  APIContent getProDetailsByGid(@ModelAttribute YcfkBase ycfkBase){
		log.info("列表查询开始");
		 try{
			List<YcfkBase> listYcfkBase=ycfkBaseService.getProDetailsByGid(ycfkBase.getGId());
			return super.putAPIData(listYcfkBase);
		 }catch(Exception e){
			    log.info("获取异常反馈基础列表失败:"+e.toString());
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		 }
	
	}
	
	/**
	 * 导入异常反馈基础
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("ycfkBase/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = ycfkBaseService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("导入异常反馈基础信息失败:" + e.toString());
			errorList.add("导入异常反馈基础信息失败:,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}
