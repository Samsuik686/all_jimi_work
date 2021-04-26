package cn.concox.app.material.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.material.service.MaterialLogTempService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.material.MaterialLogTemp;

/**
 * <pre>
 * MaterialLogTempController 出入库导入管理管理
 * </pre>
 */
@Controller
@Scope("prototype")
public class MaterialLogTempController extends BaseController {

	private static Logger log=Logger.getLogger(MaterialLogTempController.class);
	@Resource(name="materialLogTempService")
	private MaterialLogTempService materialLogTempService;
	
	/*
	 * 获取出入库导入管理列表，分页
	 */
	@RequestMapping("materialLogTemp/getMaterialLogTempList")
	@ResponseBody
	public APIContent getMaterialLogTempList(@ModelAttribute MaterialLogTemp materialLogTemp,@ModelAttribute CommonParam comp,HttpServletRequest req){
		try{
				if(!StringUtils.isBlank(req.getParameter("outTimeStarts"))){
					materialLogTemp.setOutTimeStart(req.getParameter("outTimeStarts"));
				}
				if(!StringUtils.isBlank(req.getParameter("outTimeEnds"))){
					materialLogTemp.setOutTimeEnd(req.getParameter("outTimeEnds"));
				}
				//设置分页数
				Page<MaterialLogTemp> materialLogTempListPage=materialLogTempService.getMaterialLogTempList(materialLogTemp, comp.getCurrentpage(),comp.getPageSize());
				req.getSession().setAttribute("totalValue", materialLogTempListPage.getTotal());
				return super.putAPIData(materialLogTempListPage.getResult());
		
		}catch(Exception e){
			log.info("获取出入库导入管理列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 删除入库出库信息
	 */
	@RequestMapping("materialLogTemp/deleteMaterialLogTemp")
	@ResponseBody
	public APIContent deleteMaterialLogTemp(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute MaterialLogTemp materialLogTemp){
		log.info("删除出入库导入管理开始");
		try {
			if(materialLogTemp.getIds()!=null){
				 materialLogTempService.delete(materialLogTemp);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				log.error("删除出入库导入管理失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		
		} catch (Exception e) {
			log.error("删除出入库导入管理！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * 导出出入库导入管理信息数据
	 * @param materialLogTemp 
	 * @param req
	 * @return
	 */
	@RequestMapping("materialLogTemp/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute MaterialLogTemp materialLogTemp,HttpServletRequest req,HttpServletResponse response){
		try{
			if(!StringUtils.isBlank(req.getParameter("outTimeStarts"))){
				materialLogTemp.setOutTimeStart(req.getParameter("outTimeStarts"));
			}
			if(!StringUtils.isBlank(req.getParameter("outTimeEnds"))){
				materialLogTemp.setOutTimeEnd(req.getParameter("outTimeEnds"));
			}
			materialLogTempService.ExportDatas(materialLogTemp,req,response);
		}catch(Exception e){
			log.error("导出出入库导入管理信息数据失败:",e);
		}
	}
}


    