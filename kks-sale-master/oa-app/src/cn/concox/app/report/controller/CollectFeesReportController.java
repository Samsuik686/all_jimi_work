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
import cn.concox.app.report.service.CollectFeesReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.report.CollectFeesReport;


/**
 * 售后收费年报表
 *
 */
@Controller
@Scope("prototype")
public class CollectFeesReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(CollectFeesReportController.class);
	
	@Resource(name="collectFeesReportService")
	private CollectFeesReportService collectFeesReportService;
	
	
	/**
	 *  售后收费年报表 
	 *  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("collectFeesReport/collectFeesYearReportList")
	@ResponseBody
	public APIContent getCollectFeesYearReportList(@ModelAttribute CollectFeesReport report,HttpServletRequest request){ 
		try{
			String searchDate=request.getParameter("searchDate");
			if(!StringUtils.isBlank(searchDate)){
				report.setSearchMonth(searchDate);
			}
			List<CollectFeesReport> reports=collectFeesReportService.getCollectFeesYearReportList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取售后收费年数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 *  售后收费月报表 
	 *  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("collectFeesReport/collectFeesReportList")
	@ResponseBody
	public APIContent getCollectFeesReportList(@ModelAttribute CollectFeesReport report,HttpServletRequest request){ 
		try{
			String searchDate=request.getParameter("searchDate");
			if(!StringUtils.isBlank(searchDate)){
				report.setSearchTime(searchDate);
			}
			List<CollectFeesReport> reports=collectFeesReportService.getCollectFeesReportList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取售后收费月数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 *  售后收费年报表
	 *  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("collectFeesReport/MonthExportDatas")
	@ResponseBody
	public APIContent MonthExportDatas(@ModelAttribute CollectFeesReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			String searchDate=request.getParameter("searchDate");
			if(!StringUtils.isBlank(searchDate)){
				report.setSearchMonth(searchDate);
			}
			collectFeesReportService.MonthExportDatas(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出售后收费年数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 *  售后收费月报表
	 *  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("collectFeesReport/ExportDatas")
	@ResponseBody
	public APIContent ExportDatas(@ModelAttribute CollectFeesReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			String searchDate=request.getParameter("searchDate");
			if(!StringUtils.isBlank(searchDate)){
				report.setSearchTime(searchDate);
			}
			collectFeesReportService.ExportDatas(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出售后收费月数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
