package cn.concox.app.report.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.report.service.InnerRepairAgainReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.DateUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.report.InnerRepairAgainReport;
/**
 * 报表管理
 * @author Li.Shangzhi
 * @date 2016-08-19 14:28:14
 */
@Controller
@Scope("prototype")
public class InnerRepairAgainReportController extends BaseController {
	private static Logger logger = Logger.getLogger(InnerRepairAgainReportController.class);
	
	@Resource(name="innerRepairAgainReportService")
	private InnerRepairAgainReportService innerRepairAgainReportService;
	
	/**
	 * TODO A2.0
	 * 内部二次返修报表  
	 * [/getRepairagainList]  获取二次维修数据
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("innerRepairAgainReport/getRepairagainList")
	@ResponseBody
	public APIContent getRepairagainList(@ModelAttribute InnerRepairAgainReport report,HttpServletRequest req){ 
		try{
			String StartTime = req.getParameter("StartDate");
			String EndTime   = req.getParameter("EndDate");
			
			if(!StringUtil.isRealEmpty(StartTime)){
				report.setStratTime(DateUtil.getTimestampByStr(StartTime));
			}
			if(!StringUtil.isRealEmpty(EndTime)){
				report.setEndTime(DateUtil.getTimestampByStr(EndTime)); 
			}
			APIContent apiContent = new APIContent();
			//TODO 数据报表
			List<InnerRepairAgainReport> reports=innerRepairAgainReportService.getRepairAgainList(report);
			apiContent.setData(reports);
			
			//TODO 总数
			Integer sumCount = innerRepairAgainReportService.getCountRepair(report); 
			apiContent.setTotal(sumCount);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC, apiContent);
		}catch(Exception e){
			logger.error("内部二次维修数据:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * TODO A2.1
	 * 内部内部二次返修报表  
	 * [/doExportDatas]  导出数据报表
	 * @param InnerRepairAgainReport
	 * @param req
	 * @return
	 */
	@RequestMapping("innerRepairAgainReport/doExportRepairADatas")
	@ResponseBody
	public APIContent doExportRepairADatas(HttpServletRequest request,HttpServletResponse response){ 
		try{
			InnerRepairAgainReport report = new InnerRepairAgainReport();
			
			String StartTime = request.getParameter("StartDate");
			String EndTime   = request.getParameter("EndDate");
			
			if(!StringUtil.isRealEmpty(StartTime)){
				report.setStratTime(DateUtil.getTimestampByStr(StartTime));
			}
			if(!StringUtil.isRealEmpty(EndTime)){
				report.setEndTime(DateUtil.getTimestampByStr(EndTime)); 
			}
			innerRepairAgainReportService.doExportRepairADatas(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("内部二次返修报表  :"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
}
