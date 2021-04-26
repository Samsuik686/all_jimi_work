package cn.concox.app.report.controller;

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
import cn.concox.app.report.service.WarrantyReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.WarrantyReport;


/**
 * 售后收费明细报表
 *
 */
@Controller
@Scope("prototype")
public class WarrantyReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(WarrantyReportController.class);
	
	@Resource(name="warrantyReportService")
	private WarrantyReportService warrantyReportService;
	
	
	@RequestMapping("warrantyReport/warrantyReportPage")
	@ResponseBody
	public APIContent getWarrantyReportPage(@ModelAttribute WarrantyReport report, @ModelAttribute CommonParam comp,HttpServletRequest request){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			
			if(!StringUtils.isBlank(report.getImei())) {
				String[] imeis = report.getImei().split(",");
				report.setImeis(imeis);
			}
			
			Page<WarrantyReport> warrantyPage = warrantyReportService.getWarrantyReportPage(report, comp.getCurrentpage(), comp.getPageSize());
			request.getSession().setAttribute("totalValue", warrantyPage.getTotal());
			return super.putAPIData(warrantyPage.getResult());
		}catch(Exception e){
			logger.error("获取过保返修机收费分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 *  售后收费明细报表 
	 *  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("warrantyReport/warrantyReportList")
	@ResponseBody
	public APIContent getWarrantyReportList(@ModelAttribute WarrantyReport report,HttpServletRequest request){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			List<WarrantyReport> reports=warrantyReportService.getWarrantyReportList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取过保返修机收费数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 *  售后收费明细报表
	 *  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("warrantyReport/ExportDatas")
	@ResponseBody
	public APIContent ExportDatas(@ModelAttribute WarrantyReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			warrantyReportService.ExportDatas(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出过保返修机收费数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}


}
