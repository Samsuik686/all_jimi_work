package cn.concox.app.materialManage.controller;
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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.materialManage.service.ProductMaterialFileLogService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.materialManage.ProductMaterialFileLog;

/**
 * 
 */

@Controller
@RequestMapping("/productMaterialFileLog")
@Scope("prototype")
public class ProductMaterialFileLogController extends BaseController {
	Logger logger = Logger.getLogger(ProductMaterialFileLogController.class);
	
	@Resource(name = "productMaterialFileLogService")
	private ProductMaterialFileLogService productMaterialFileLogService;
	
	/**
	 * @param pm
	 * @param com
	 * @param req
	 * @return
	 */
	@RequestMapping("/getProductMaterialFileLogPage")
	@ResponseBody
	public APIContent getProductMaterialFileLogPage (@ModelAttribute ProductMaterialFileLog pm, @ModelAttribute CommonParam com,
			HttpServletRequest req) {
		try {
			String startTime=req.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				pm.setStartTime(startTime);
			}
			
			String endTime=req.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				pm.setEndTime(endTime);
			}
			Page<ProductMaterialFileLog> page = productMaterialFileLogService.productMaterialFileLogPage(pm, com.getCurrentpage(), com.getPageSize());
			req.getSession().setAttribute("totalValue",page.getTotal());
			List<ProductMaterialFileLog> list = page.getResult();
			return super.putAPIData(list);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("获取下载日志记录分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute ProductMaterialFileLog pm, HttpServletRequest req, HttpServletResponse response) {
		try {
			String startTime=req.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				pm.setStartTime(startTime);
			}
			
			String endTime=req.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				pm.setEndTime(endTime);
			}
			productMaterialFileLogService.ExportDatas(pm, req, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取下载日志记录列表数据失败:" + e.toString());
		}
	}
}
