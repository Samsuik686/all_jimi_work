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

import cn.concox.app.report.service.ZgzReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.report.ZgzReport;

/**
 * 保内故障总分类报表
 * @author Aikuangyong
 *
 */
@Controller
@Scope("prototype")
public class ZgzReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(ZgzReportController.class);
	
	
	@Resource(name="zgzreporteService")
	private ZgzReportService zgzreporteService;
	
	
	/**
	 * [Material]
	 * 保内故障总分类报表  
	 * [/getAccqueteList]  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/getBreakdownList")
	@ResponseBody
	public APIContent getBreakdownList(@ModelAttribute ZgzReport report,HttpServletRequest req){ 
		try{
			List<ZgzReport> reports=zgzreporteService.getBreakdownList(report);
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取机型故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * [Material]
	 * 保内故障总分类报表  
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/zgzExportDatas")
	@ResponseBody
	public APIContent zgzExportDatas(@ModelAttribute ZgzReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{
			zgzreporteService.ExportDatas(report,request,response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取机型故障数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

}
