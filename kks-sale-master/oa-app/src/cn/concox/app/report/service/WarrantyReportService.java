package cn.concox.app.report.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.report.mapper.WarrantyReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.report.WarrantyReport;
import freemarker.template.Template;

@Service("warrantyReportService")
@Scope("prototype")
public class WarrantyReportService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "warrantyReportMapper")
	private WarrantyReportMapper<WarrantyReport> warrantyReportMapper;

	@SuppressWarnings("unchecked")
	public Page<WarrantyReport> getWarrantyReportPage(WarrantyReport report, int currentPage, int pageSize) {
		Page<WarrantyReport> warrantyPage = new Page<WarrantyReport>();
		WarrantyReport wr = new WarrantyReport();
		BigDecimal totalMoney = new BigDecimal(0.00);
		BigDecimal ling = new BigDecimal(0.00);
		Integer number= 0;
		warrantyPage.setCurrentPage(currentPage);
		warrantyPage.setSize(pageSize);
		warrantyPage = warrantyReportMapper.getWarrantyReportPage(warrantyPage, report);
		if (null != warrantyPage.getResult() && warrantyPage.getResult().size() > 0) {
			for (WarrantyReport w : warrantyPage.getResult()) {
				String zzgz = w.getZzgzDesc();
				if (!StringUtils.isBlank(zzgz) && (!"运费".equals(zzgz) || !"税费".equals(zzgz))) {
					String ids[] = zzgz.split(",");
					String zzgzDesc = getFaultName(ids);
					w.setZzgzDesc(zzgzDesc);
				}
				totalMoney=w.getTotalMoney()==null?totalMoney.add(ling) : totalMoney.add(w.getTotalMoney());
				number=w.getNumber()==0?number+0 : number+w.getNumber();
			}
			wr.setTotalMoney(totalMoney);
			wr.setCusName("总计");
			wr.setNumber(number);
			warrantyPage.getResult().add(wr);
		}
		return warrantyPage;
	}

	/**
	 * 售后收费明细报表数据源
	 * 
	 * @param warrantyReport
	 *            报表对象
	 */
	public List<WarrantyReport> getWarrantyReportList(WarrantyReport report) {
		WarrantyReport wr =new WarrantyReport();
		BigDecimal totalMoney=new BigDecimal(0.00);
		BigDecimal ling=new BigDecimal(0.00);
		Integer number= 0;
		List<WarrantyReport> warrList = warrantyReportMapper.getWarrantyReportList(report);
		if (null != warrList && warrList.size() > 0) {
			for (WarrantyReport w : warrList) {
				String zzgz = w.getZzgzDesc();
				if (!StringUtils.isBlank(zzgz) && (!"运费".equals(zzgz) || !"税费".equals(zzgz))) {
					String ids[] = zzgz.split(",");
					String zzgzDesc = getFaultName(ids);
					w.setZzgzDesc(zzgzDesc);
				}
				totalMoney=w.getTotalMoney()==null?totalMoney.add(ling) : totalMoney.add(w.getTotalMoney());
				number=w.getNumber()==0?number+0 : number+w.getNumber();
			}
			wr.setTotalMoney(totalMoney);
			wr.setCusName("总计");
			wr.setNumber(number);
			warrList.add(wr);
		}
		return warrList;
	}

	/**
	 * 获取 最终故障
	 * 
	 * @param ids
	 * @return
	 */
	public String getFaultName(String... ids) {
		return warrantyReportMapper.getFaultName(ids);
	}

	/**
	 * 导出数据
	 * 
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(WarrantyReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cusName = report.getCusName();
		if (null != cusName && !"".equals(cusName)) {
			report.setCusName(new String(cusName.getBytes("iso8859-1"), "utf-8"));
		}
		String remAccount = report.getRemAccount();
		if (null != remAccount && !"".equals(remAccount)) {
			report.setRemAccount(new String(remAccount.getBytes("iso8859-1"), "utf-8"));
		}
		List<WarrantyReport> reports = getWarrantyReportList(report);
		String[] fieldNames = new String[] {"送修批次", "客户名称", "主板型号", "IMEI", "报价日期", "报价原因", "报价故障描述", "处理措施", "数量", "单价(￥)", "合计金额(￥)", "收款原因", "汇款方式", "付款日期", "确认结果", "备注"};
		Map map = new HashMap();
		map.put("size", reports.size() + 2);
		map.put("peList", reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("售后收费明细报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("WarrantyReport.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

}
