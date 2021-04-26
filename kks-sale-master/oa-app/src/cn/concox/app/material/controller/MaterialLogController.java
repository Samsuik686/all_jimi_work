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
import cn.concox.app.material.service.MaterialLogService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.material.MaterialLog;

/**
 * <pre>
 * MaterialLogController 出库入库管理
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class MaterialLogController extends BaseController {

	private static Logger log=Logger.getLogger(MaterialLogController.class);
	
	@Resource(name="materialLogService")
	private MaterialLogService materialLogService;
	
	/*
	 * 查询出库入库
	 */
	@RequestMapping("materialLog/selectMaterialLog")
	@ResponseBody
	public APIContent selectMaterialLog(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute MaterialLog materialLog){
		log.info("出库入库查询开始");
		try {
			if(materialLog.getId()!=null){
				MaterialLog materialLog1=materialLogService.select(materialLog);
				return super.putAPIData(new APIContent(Globals.OPERA_SUCCESS_CODE, "查询成功!", materialLog1));
			}else{
				log.error("出库入库查询失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		
		} catch (Exception e) {
			log.error("出库入库查询失败！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	
	/*
	 * 获取出库入库列表，分页
	 */
	@RequestMapping("materialLog/getMaterialLogListPage")
	@ResponseBody
	public APIContent getMaterialLogListPage(@ModelAttribute MaterialLog materialLog,@ModelAttribute CommonParam comp,HttpServletRequest req){
		try{
				if(!StringUtils.isBlank(req.getParameter("outTimeStarts"))){
					materialLog.setOutTimeStart(req.getParameter("outTimeStarts"));
				}
				if(!StringUtils.isBlank(req.getParameter("outTimeEnds"))){
					materialLog.setOutTimeEnd(req.getParameter("outTimeEnds"));
				}
				//设置分页数
				Page<MaterialLog> materialLogListPage=materialLogService.getMaterialLogListPage(materialLog, comp.getCurrentpage(),comp.getPageSize());
				req.getSession().setAttribute("totalValue", materialLogListPage.getTotal());
				return super.putAPIData(materialLogListPage.getResult());
		
		}catch(Exception e){
			log.info("获取出库入库列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 更新和新增出库入库
	 */
	@RequestMapping("materialLog/addOrUpdateMaterialLog")
	@ResponseBody  
	public APIContent addOrUpdateMaterialLog(HttpServletRequest req,@ModelAttribute MaterialLog materialLog){
		log.info("更新出库入库信息开始");
		try {
			if(!StringUtils.isBlank(req.getParameter("outTimes"))){
				materialLog.setOutTime(Timestamp.valueOf(req.getParameter("outTimes")));
			}
			if(materialLog.getId()>0){
				materialLogService.update(materialLog);
				return new APIContent(Globals.OPERA_SUCCESS_CODE,"更新出库入库信息"+Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				materialLog.setClerk(super.getSessionUserName(req));
				materialLogService.insert(materialLog);
				return new APIContent(Globals.OPERA_SUCCESS_CODE,"新增出库入库"+Globals.OPERA_SUCCESS_CODE_DESC);
			}
			
		} catch (Exception e) {
			log.error("更新出库入库信息失败！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,e.getMessage());
		}
	}
	
	/*
	 * 删除入库出库信息
	 */
	@RequestMapping("materialLog/deleteMaterialLog")
	@ResponseBody
	public APIContent deleteMaterialLog(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute MaterialLog materialLog){
		log.info("删除出库入库开始");
		try {
			if(materialLog.getIds()!=null){
				 materialLogService.delete(materialLog);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);

			}else{
				log.error("删除出库入库失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		
		} catch (Exception e) {
			log.error("删除出库入库！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * 导出出库入库信息数据
	 * @param materialLog 
	 * @param req
	 * @return
	 */
	@RequestMapping("materialLog/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute MaterialLog materialLog,HttpServletRequest req,HttpServletResponse response){
		try{
			if(!StringUtils.isBlank(req.getParameter("outTimeStarts"))){
				materialLog.setOutTimeStart(req.getParameter("outTimeStarts"));
			}
			if(!StringUtils.isBlank(req.getParameter("outTimeEnds"))){
				materialLog.setOutTimeEnd(req.getParameter("outTimeEnds"));
			}
			materialLogService.ExportDatas(materialLog,req,response);
		}catch(Exception e){
			log.error("导出出库入库信息数据失败:",e);
		}
	}
	
	/**
	 * 导入出库入库数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("materialLog/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = materialLogService.ImportDatas(file, request);
		} catch (Exception e) {
			log.error("导入出库入库数据失败:",e);
			errorList.add("导入出库入库数据失败:,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}


    