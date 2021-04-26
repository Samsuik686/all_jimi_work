package cn.concox.app.report.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.report.mapper.CallbackReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.report.CallbackReport;
import freemarker.template.Template;

@Service("callbackReportService")
@Scope("prototype")
public class CallbackReportService {

	@Resource(name="callbackReportMapper")
	CallbackReportMapper<CallbackReport> callbackReportMapper;
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	public Page<CallbackReport> getCallbackListPage(CallbackReport callbackReport, int currentPage, int pageSize) {
		Page<CallbackReport> callbackReportPage = new Page<CallbackReport>();
		callbackReportPage.setCurrentPage(currentPage);
		callbackReportPage.setSize(pageSize);
		callbackReportPage = callbackReportMapper.getCallbackListPage(callbackReportPage, callbackReport);
		if(callbackReportPage!= null && null !=callbackReportPage.getResult()){
			for(CallbackReport cr:callbackReportPage.getResult()){
				cr.setSkillEvaluate(getEvaluateDescribe(cr.getSkillEvaluate()));
				cr.setServiceEvaluate(getEvaluateDescribe(cr.getServiceEvaluate()));
			}
		}
		return callbackReportPage;
	}
	
	private String getEvaluateDescribe(String val){
		if("0".equals(val)){
			return "满意";
		}else if("1".equals(val)){
			return "比较满意";
		}else if("2".equals(val)){
			return "一般";
		}else if("3".equals(val)){
			return "不满意";
		}else if("4".equals(val)){
			return "非常不满意";
		}else {
			return "";
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void exportDatas(CallbackReport callbackReport, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CallbackReport> callback_list = callbackReportMapper.getCallbackList(callbackReport);
		if(callback_list!= null && callback_list.size() > 0){
			for(CallbackReport cr:callback_list){
				cr.setSkillEvaluate(getEvaluateDescribe(cr.getSkillEvaluate()));
				cr.setServiceEvaluate(getEvaluateDescribe(cr.getServiceEvaluate()));
			}
		}
		String[] fieldNames = new String[] {"送修批号","送修单位", "受理时间", "取机时间 ",
				"评价时间","技能评价","服务评价","评价说明"};
		Map map = new HashMap();
		map.put("size", callback_list.size() + 2);
		map.put("peList", callback_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("客户回访汇总报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(
				request.getRealPath(GlobalCons.FREEMARKER)).append("/")
				.append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("CallbacklReport.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,
				template, map);
	}
}
