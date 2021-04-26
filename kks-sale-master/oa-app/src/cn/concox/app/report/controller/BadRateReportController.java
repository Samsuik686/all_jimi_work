package cn.concox.app.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.LockIdUrlService;
import cn.concox.app.report.service.BadRateReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.JavaNetURLRESTFulClient;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.LockIdUrl;
import cn.concox.vo.basicdata.Saledata;
import cn.concox.vo.report.BadRateReport;

/**
 * 机器不良率报表
 * @author Aikuangyong
 *
 */
@Controller
@Scope("prototype")
public class BadRateReportController extends BaseController {

	private static Logger logger = Logger.getLogger(BadRateReportController.class);
	
	@Value("${ams_goods_url}")
	private String AMS_GOODS_URL;

	@Value("${ams_sales_url}")
	private String AMS_SALES_URL;


	@Resource(name="badRateReportService")
	private BadRateReportService badRateReportService;
	
	@Resource(name="lockIdUrlService")
	private LockIdUrlService lockIdUrlService;


	/**
	 * [Material]
	 *  机器不良率报表
	 * [/fenBreakdownList]  获取数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/badrateList")
	@ResponseBody
	public APIContent badrateList(@ModelAttribute BadRateReport report,HttpServletRequest req){ 
		try{
			List<BadRateReport> reports =badRateReportService.badrateList(report);		//返修机数量			
			return super.putAPIData(reports);
		}catch(Exception e){
			logger.error("获取机器不良率数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}


	/**
	 * [Material]
	 *  机器不良率报表
	 * [/doExportDatas]  导出数据报表
	 * @param report
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/badrateExportDatas")
	@ResponseBody
	public APIContent badrateExportDatas(@ModelAttribute BadRateReport report,HttpServletRequest request,HttpServletResponse response){ 
		try{	
			String model = report.getModel();
			if (null != model && !"".equals(model)) {
				report.setModel(new String(model.getBytes("iso8859-1"),"utf-8"));
			}	
			List<BadRateReport> reports = badRateReportService.badrateList(report);
			badRateReportService.ExportDatas(reports,request,response,report);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取机器不良率数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}


	/**
	 * 根据IMEI查询AMS系统销售数据
	 * @param req
	 * @return
	 */
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("reportCon/saleList")
	@ResponseBody
	public APIContent getSaleList(HttpServletRequest req) {
		try {
			String imei=req.getParameter("s_imei");
			List<Saledata> list = new ArrayList<Saledata>();	
			//String json = "{'imei':'353419030099626','sfVersion':'ET200_20_60DM2_B25E_F0','eqpDepartment':1,'sn':null,'phone':null,'deputySfVersion':null,'boxNumber':'BOX001','bill':'AAA','status':1,'mcType':'ET200','outDate':'2014-07-19 00:00:00','productionDate':'2014-07-18 00:00:00','hdVersion':'V01','msg':'0','deputyHdVersion':null,'cpName':'新思为','xpId':'E0B0F1C1089B2C0B16B877F585559504'}";
			if(!"".equals(imei)){
				String json = null;
				if(imei.trim().length() == 15){
					json = JavaNetURLRESTFulClient.restSale(imei.trim(), "", AMS_SALES_URL); //ams设备销售数据接口
				}else {
					LockIdUrl lockIdUrl = lockIdUrlService.matchLongStr(imei.trim());
					if(null != lockIdUrl && null != lockIdUrl.getLockInfo()){//输入信息（imei或智能锁id或只能锁id二维码）
						 if(StringUtil.isDigit(imei.trim())){//输入数字小于15位，默认是智能锁id
							 lockIdUrl.setLockId(imei.trim());
						 }
					}
					if(null != lockIdUrl.getLockId()){
						json = JavaNetURLRESTFulClient.restSale("", lockIdUrl.getLockId(), AMS_SALES_URL); //ams设备销售数据接口
					}
				}
				if(null != json){
					JSONObject jsonObject = JSONObject.fromObject(json);
					String msg = jsonObject.getString("msg"); 
					//从接口获取数据成功
					if("0".equals(msg)){
						/** 
						 * json对象转换成java对象 
						 */  
						Saledata data = (Saledata) JSONObject.toBean(jsonObject,Saledata.class);  
						list.add(data);
					}			 
				}
			}		
			return super.putAPIData(list);			
		} catch (Exception e) {
			logger.error("获取AMS系统销售数据失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}


}
