package cn.concox.app.report.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.ZgzmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.report.mapper.TestMaterialReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Zgzmanage;
import cn.concox.vo.report.TestMaterialReport;
import freemarker.template.Template;

@Service("testMaterialReportService")
@Scope("prototype")
public class TestMaterialReportService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "testMaterialReportMapper")
	private TestMaterialReportMapper<TestMaterialReport> testMaterialReportMapper;
	
	@Resource(name="zgzmanageMapper")
	private  ZgzmanageMapper<Zgzmanage> zgzmanageMapper;

	/**
	 * 试流料返修明细报表分页显示
	 * @param report
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<TestMaterialReport> getTestMaterialReportList(TestMaterialReport report, int currentPage, int pageSize) {
		Page<TestMaterialReport> tPage = new Page<TestMaterialReport>();
		tPage.setCurrentPage(currentPage);
		tPage.setSize(pageSize);
		tPage = testMaterialReportMapper.getTestMaterialReportList(tPage, report);
		
		for (TestMaterialReport t : tPage.getResult()) {
			if(!StringUtil.isEmpty(t.getZzgzDesc())){
				//TODO 最终故障
				String zzgz_Name = zgzmanageMapper.getGRoupConcat(StringUtil.split(t.getZzgzDesc()));
				t.setZzgzDesc(zzgz_Name);
			}
		}
		return tPage;
	}
	
	/**
	 * 试流料不良率汇总报表分页显示
	 * @param report
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<TestMaterialReport> getTestMaterialReportRercentList(TestMaterialReport report, int currentPage, int pageSize) {
		Page<TestMaterialReport> tPage = new Page<TestMaterialReport>();
		tPage.setCurrentPage(currentPage);
		tPage.setSize(pageSize);
		tPage = testMaterialReportMapper.getTestMaterialReportRercentList(tPage, report);
		return tPage;
	}

	/**
	 * 试流料返修明细报表
	 * 
	 * @param testMaterialReport
	 *            报表对象
	 */
	public List<TestMaterialReport> getTestMaterialReportList(TestMaterialReport report) {
		List<TestMaterialReport> tList = testMaterialReportMapper.getTestMaterialReportList(report);
		for (TestMaterialReport t : tList) {
			if(null == t.getTestMatfalg()){
				t.setTestMatfalg(0);
			}
			if(!StringUtil.isEmpty(t.getZzgzDesc())){
				//TODO 最终故障
				String zzgz_Name = zgzmanageMapper.getGRoupConcat(StringUtil.split(t.getZzgzDesc()));
				t.setZzgzDesc(zzgz_Name);
			}
		}
		return tList;
	}
	
	/**
	 * 试流料不良率汇总报表
	 * 
	 * @param testMaterialReport
	 *            报表对象
	 */
	public List<TestMaterialReport> getTestMaterialReportRercentList(TestMaterialReport report) {
		List<TestMaterialReport> warrList = testMaterialReportMapper.getTestMaterialReportRercentList(report);
		TestMaterialReport test = new TestMaterialReport();
		Integer count = 0;
		for (TestMaterialReport t : warrList) {
			count += t.getMatCount();
		}
		test.setMaterialNumber("总计");
		test.setMatCount(count);
		warrList.add(warrList.size(), test);
		return warrList;
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
	public void exportDatasTestMaterialReport(TestMaterialReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String startTime = report.getStartTime();
		String endTime = report.getEndTime();
		String materialName = report.getMaterialName();
		String matName = "";
		StringBuilder dispaly = new StringBuilder();
		boolean stNull = (startTime == null || "".equals(startTime)),etNull = (endTime == null || "".equals(endTime));
		if(!stNull && !etNull){
			dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		if(stNull && !etNull){
			 dispaly.append("(--").append(endTime).append(")");
		}
		if(!stNull && etNull){
			 Date date = new Date();
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");       
		     endTime = sdf.format(date).toString();
		     dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		
		if(!StringUtil.isRealEmpty(materialName)){//搜索的物料名称
			matName = "(" + materialName.replace("<", "(").replace(">", ">").replace("*", "×") + ")";
		}
		List<TestMaterialReport> reports = getTestMaterialReportList(report);
		String[] fieldNames = new String[] {"订单号", "项目", "IMEI", "出货日期", "物料编码", "物料名称", "试流料", "维修员", "最终故障", "处理措施", "受理时间"};
		Map map = new HashMap();
		map.put("size", reports.size() + 2);
		map.put("matName", matName);
		map.put("dispalyDate", dispaly);
		map.put("peList", reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("试流料"+matName+ "返修明细报表" + dispaly).append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("TestMaterialReport.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
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
	public void exportDatasTestMaterialReportRercent(TestMaterialReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String curDate = "(" + sdf.format(date) + ")";//导出当前日期
		List<TestMaterialReport> reports = getTestMaterialReportRercentList(report);
		String[] fieldNames = new String[] {"订单号", "项目", "物料编码", "物料名称", "创建人", "创建时间", "物料规格", "所要替换的物料编码", 
				"位号", "供应商", "SMT试流时间", "SMT工单", "SMT试流结果", "组装试流时间", "组装试流结果", "试流判定", "出货日期", "问题数量", "订单数量", "百分比"};
		Map map = new HashMap();
		map.put("size", reports.size() + 2);
		map.put("curDate", curDate);
		map.put("peList", reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("试流料不良率汇总报表" + curDate).append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("TestMaterialReportRercent.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

}
