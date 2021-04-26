package cn.concox.app.report.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.report.mapper.CollectFeesReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.report.CollectFeesReport;
import freemarker.template.Template;

@Service("collectFeesReportService")
@Scope("prototype")
public class CollectFeesReportService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "collectFeesReportMapper")
	private CollectFeesReportMapper<CollectFeesReport> collectFeesReportMapper;

	/**
	 * 月收费报表数据源
	 * 
	 * @param collectFeesReport
	 */
	public List<CollectFeesReport> getCollectFeesYearReportList(CollectFeesReport report) {
		return comm(report, 0);
	}

	public List<CollectFeesReport> comm(CollectFeesReport report,int type){
		List<CollectFeesReport> list = new ArrayList<CollectFeesReport>();
		if(type == 0){
			list=collectFeesReportMapper.getCollectFeesYearReportList(report);
		}else if(type == 1){
			list = collectFeesReportMapper.getCollectFeesReportList(report);
		}
		BigDecimal sumRepairPay = BigDecimal.ZERO,
				   sumMaterialPay = BigDecimal.ZERO,
				   sumRechargePay = BigDecimal.ZERO,
				   sumPhoneRecharge = BigDecimal.ZERO,
				   sumWlwkRecharge = BigDecimal.ZERO,
				   sumLogCost = BigDecimal.ZERO,
				   sumRatePrice = BigDecimal.ZERO;
		CollectFeesReport sumReport = new CollectFeesReport();
		for (CollectFeesReport l : list) {
			if (l.getXlogCost() == null) {  //销售配件运费
				l.setXlogCost(BigDecimal.ZERO);
			}
			if (l.getTlogCost() == null) {  //报价工站运费
				l.setTlogCost(BigDecimal.ZERO);
			}
			l.setLogCost(l.getXlogCost().add(l.getTlogCost()));
			sumLogCost = sumLogCost.add(l.getLogCost());
			
			if (l.getXratePrice() == null) {  //销售配件税费
				l.setXratePrice(BigDecimal.ZERO);
			}
			if (l.getTratePrice() == null) {  //报价工站税费
				l.setTratePrice(BigDecimal.ZERO);
			}
			if (l.getPhoneRatePrice() == null) {  //手机卡税费
				l.setPhoneRatePrice(BigDecimal.ZERO);
			}
			if (l.getWlwkRatePrice() == null) {  //物联网卡税费
				l.setWlwkRatePrice(BigDecimal.ZERO);
			}
			if (l.getReRatePrice() == null) {  //平台税费
				l.setReRatePrice(BigDecimal.ZERO);
			}
			l.setRatePrice(l.getXratePrice().add(l.getTratePrice()).add(l.getPhoneRatePrice()).add(l.getWlwkRatePrice()).add(l.getReRatePrice()));
			sumRatePrice = sumRatePrice.add(l.getRatePrice());
			
			if (l.getRepairPay() == null) {  //维修费
				l.setRepairPay(BigDecimal.ZERO);
				sumRepairPay = sumRepairPay.add(BigDecimal.ZERO);
			}else{
				sumRepairPay = sumRepairPay.add(l.getRepairPay());
			}
			if (l.getMaterialPay() == null) {  //配件费
				l.setMaterialPay(BigDecimal.ZERO);
				sumMaterialPay = sumMaterialPay.add(BigDecimal.ZERO);
			}else{
				sumMaterialPay = sumMaterialPay.add(l.getMaterialPay());
			}
			if (l.getRechargePay() == null) {  //平台费
				l.setRechargePay(BigDecimal.ZERO);
				sumRechargePay = sumRechargePay.add(BigDecimal.ZERO);
			}else{
				sumRechargePay = sumRechargePay.add(l.getRechargePay());
			}
			if (l.getPhoneRecharge() == null) {  //手机卡费
				l.setPhoneRecharge(BigDecimal.ZERO);
				sumPhoneRecharge = sumPhoneRecharge.add(BigDecimal.ZERO);
			}else{
				sumPhoneRecharge = sumPhoneRecharge.add(l.getPhoneRecharge());
			}
			if (l.getWlwkRecharge() == null) {  //物联网卡费
				l.setWlwkRecharge(BigDecimal.ZERO);
				sumWlwkRecharge = sumWlwkRecharge.add(BigDecimal.ZERO);
			}else{
				sumWlwkRecharge = sumWlwkRecharge.add(l.getWlwkRecharge());
			}
			l.setTotalPay(l.getRepairPay().add(l.getMaterialPay()).add(l.getRechargePay()).add(l.getPhoneRecharge()).add(l.getWlwkRecharge()).add(l.getLogCost()).add(l.getRatePrice()));			
		}
		if(type == 0){
			sumReport.setPayMonth("年汇总");
		}else if(type == 1){
			sumReport.setPayTimes("月汇总");
		}
		sumReport.setRepairPay(sumRepairPay);
		sumReport.setMaterialPay(sumMaterialPay);
		sumReport.setRechargePay(sumRechargePay);
		sumReport.setPhoneRecharge(sumPhoneRecharge);
		sumReport.setWlwkRecharge(sumWlwkRecharge);
		sumReport.setLogCost(sumLogCost);
		sumReport.setRatePrice(sumRatePrice);
		sumReport.setTotalPay(sumRepairPay.add(sumMaterialPay).add(sumRechargePay).add(sumPhoneRecharge).add(sumWlwkRecharge).add(sumLogCost).add(sumRatePrice));
		list.add(sumReport);
		return list;
	}
	
	/**
	 * 售后收费报表数据源
	 * 
	 * @param collectFeesReport
	 */
	public List<CollectFeesReport> getCollectFeesReportList(CollectFeesReport report) {
	
		return comm(report, 1);
	}

	/**
	 * 导出售后收费年报表数据
	 * 
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void MonthExportDatas(CollectFeesReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CollectFeesReport> reports = getCollectFeesYearReportList(report);
		String[] fieldNames = new String[] { "月份", "维修费(￥)", "配件费(￥)", "平台费(￥)", "手机卡费(￥)", "物联网卡费(￥)", "运费(￥)", "税费(￥)", "月汇总(￥)" };
		Map map = new HashMap();
		map.put("size", reports.size() + 2);
		map.put("peList", reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("售后收费年报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("CollectFeesYearReport.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	/**
	 * 导出售后收费月报表数据
	 * 
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(CollectFeesReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CollectFeesReport> reports = getCollectFeesReportList(report);
		String[] fieldNames = new String[] { "日期", "维修费(￥)", "配件费(￥)", "平台费(￥)", "手机卡费(￥)", "物联网卡费(￥)", "运费(￥)", "税费(￥)", "日汇总(￥)" };
		Map map = new HashMap();
		map.put("size", reports.size() + 2);
		map.put("peList", reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("售后收费月报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("CollectFeesReport.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

}
