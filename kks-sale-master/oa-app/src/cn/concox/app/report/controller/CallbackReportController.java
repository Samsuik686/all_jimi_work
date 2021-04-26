package cn.concox.app.report.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.report.service.CallbackReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.CallbackReport;

/**
 * 客户回访汇总报表
 * @author Aikuangyong
 *
 */
@Controller
@Scope("prototype")
public class CallbackReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(CallbackReportController.class);
	
	
	@Resource(name="callbackReportService")
	private CallbackReportService callbackReportService;
	
	
	/**
	 * [Material]
	 * 分页客户回访汇总报表  
	 * [/getCallbackListPage]  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getCallbackListPage")
	@ResponseBody
	public APIContent getCallbackListPage(@ModelAttribute CallbackReport callbackReport, HttpServletRequest req,
			@ModelAttribute CommonParam comp){ 
		try{						
			Page<CallbackReport> reports=callbackReportService.getCallbackListPage(callbackReport, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", reports.getTotal());
			
			return super.putAPIData(reports.getResult());
		}catch(Exception e){
			logger.error("获取客户回访汇总数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * [/doExportDatas]  导出数据报表
	 * @param callbackReport
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/crExportDatas")
	@ResponseBody
	public APIContent crExportDatas(@ModelAttribute CallbackReport callbackReport,HttpServletRequest request,HttpServletResponse response){ 
		try{
			callbackReportService.exportDatas(callbackReport,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取客户回访汇总数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

}
