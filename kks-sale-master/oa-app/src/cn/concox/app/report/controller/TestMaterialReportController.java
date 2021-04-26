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
import cn.concox.app.report.service.TestMaterialReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.TestMaterialReport;


/**
 * 试流料返修明细报表
 *
 */
@Controller
@Scope("prototype")
public class TestMaterialReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(TestMaterialReportController.class);
	
	@Resource(name="testMaterialReportService")
	private TestMaterialReportService testMaterialReportService;
	
	/**
	 * 试流料返修明细报表 分页数据
	 * @param report
	 * @param comp
	 * @param request
	 * @return
	 */
	@RequestMapping("testMaterialReport/testMaterialReportPage")
	@ResponseBody
	public APIContent getTestMaterialReportPage(@ModelAttribute TestMaterialReport report, @ModelAttribute CommonParam comp,HttpServletRequest request){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			
			String saleStartTime=request.getParameter("saleStartDate");
			if(!StringUtils.isBlank(saleStartTime)){
				report.setSaleStartTime(saleStartTime);
			}
			String SaleEndTime=request.getParameter("SaleEndDate");
			if(!StringUtils.isBlank(SaleEndTime)){
				report.setSaleEndTime(SaleEndTime);
			}
			Page<TestMaterialReport> warrantyPage = testMaterialReportService.getTestMaterialReportList(report, comp.getCurrentpage(), comp.getPageSize());
			request.getSession().setAttribute("totalValue", warrantyPage.getTotal());
			return super.putAPIData(warrantyPage.getResult());
		}catch(Exception e){
			logger.error("获取试流料出问题分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 *  试流料返修明细报表 
	 *  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("testMaterialReport/testMaterialReportList")
	@ResponseBody
	public APIContent getTestMaterialReportList(@ModelAttribute TestMaterialReport report,HttpServletRequest request){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			String saleStartTime=request.getParameter("saleStartDate");
			if(!StringUtils.isBlank(saleStartTime)){
				report.setSaleStartTime(saleStartTime);
			}
			String SaleEndTime=request.getParameter("SaleEndDate");
			if(!StringUtils.isBlank(SaleEndTime)){
				report.setSaleEndTime(SaleEndTime);
			}
			List<TestMaterialReport> reports=testMaterialReportService.getTestMaterialReportList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取试流料出问题数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 *  试流料返修明细报表
	 *  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("testMaterialReport/exportDatasTestMaterialReport")
	@ResponseBody
	public APIContent exportDatasTestMaterialReport(@ModelAttribute TestMaterialReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			String saleStartTime=request.getParameter("saleStartDate");
			if(!StringUtils.isBlank(saleStartTime)){
				report.setSaleStartTime(saleStartTime);
			}
			String SaleEndTime=request.getParameter("SaleEndDate");
			if(!StringUtils.isBlank(SaleEndTime)){
				report.setSaleEndTime(SaleEndTime);
			}
			testMaterialReportService.exportDatasTestMaterialReport(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出试流料出问题数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 试流料不良率汇总报表 分页数据
	 * @param report
	 * @param comp
	 * @param request
	 * @return
	 */
	@RequestMapping("testMaterialReport/TestMaterialReportRercentPage")
	@ResponseBody
	public APIContent getTestMaterialReportRercentPage(@ModelAttribute TestMaterialReport report, @ModelAttribute CommonParam comp,HttpServletRequest request){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			Page<TestMaterialReport> warrantyPage = testMaterialReportService.getTestMaterialReportRercentList(report, comp.getCurrentpage(), comp.getPageSize());
			request.getSession().setAttribute("totalValue", warrantyPage.getTotal());
			return super.putAPIData(warrantyPage.getResult());
		}catch(Exception e){
			logger.error("获取各试流料出问题比例分页数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 *  试流料不良率汇总报表 
	 *  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("testMaterialReport/testMaterialReportRercentList")
	@ResponseBody
	public APIContent getTestMaterialReportRercentList(@ModelAttribute TestMaterialReport report,HttpServletRequest request){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			List<TestMaterialReport> reports=testMaterialReportService.getTestMaterialReportRercentList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取各试流料出问题比例数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 *  试流料不良率汇总报表
	 *  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("testMaterialReport/exportDatasTestMaterialReportRercent")
	@ResponseBody
	public APIContent exportDatasTestMaterialReportRercent(@ModelAttribute TestMaterialReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			String startTime=request.getParameter("startDate");
			if(!StringUtils.isBlank(startTime)){
				report.setStartTime(startTime);
			}
			String endTime=request.getParameter("endDate");
			if(!StringUtils.isBlank(endTime)){
				report.setEndTime(endTime);
			}
			testMaterialReportService.exportDatasTestMaterialReportRercent(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出各试流料出问题比例数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}



}
