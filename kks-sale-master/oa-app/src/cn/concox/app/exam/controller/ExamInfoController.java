package cn.concox.app.exam.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.concox.app.common.page.Page;
import cn.concox.app.exam.service.ExamInfoService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.memcache.Logger;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.exam.Exam;


@Controller
@Scope("prototype")
@RequestMapping("/examInfo")
public class ExamInfoController extends BaseController {

	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name = "examInfoService")
	private ExamInfoService examInfoService;
	
	
	/**
	 * 获取所有考试姓名和编号
	 * @return
	 */
	@RequestMapping("/getAllExam")
	@ResponseBody
	public APIContent getAllExam(){
		try {
			List<Exam> examInfoList = examInfoService.getExamInfo();
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC, examInfoList);
		} catch (Exception e) {
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
		
	}
	
	/**
	 * 获取考试信息，分页
	 * @param exam
	 * @param comp
	 * @param req
	 * @return
	 */
	@RequestMapping("/examInfoList")
	@ResponseBody
	public APIContent getExamListPage(@ModelAttribute Exam exam, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			
			Page<Exam> examListPage = examInfoService.getExamListPage(exam, comp.getCurrentpage(), comp.getPageSize());
			return super.putAPIData(examListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取考试信息列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导出试题模板
	 * @return
	 */
	@RequestMapping("/exportTemplate")
	@ResponseBody
	public APIContent exportTemplate(HttpServletRequest request,HttpServletResponse response){
		try {
			examInfoService.exportTemplate(request, response);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
		
	}
	
	/**
	 * 新增考试
	 * @param exam
	 * @param multipartFile
	 * @param req
	 * @return
	 */
	@RequestMapping("/addExam")
	@ResponseBody
	public APIContent addExam(@ModelAttribute Exam exam,
			@RequestParam(value="examTopicFile", required=false) MultipartFile multipartFile, 
			 HttpServletRequest req){
		try {
			examInfoService.addExam(exam, multipartFile);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("获取考试成绩列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
		
	}
	
	/**
	 * 开始考试
	 * @param req
	 * @param resp
	 * @param exam
	 * @return
	 */
	@RequestMapping("/startExam")
	@ResponseBody
	public APIContent startExam(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Exam exam){
		logger.info("开始考试开始");
		try {
			if(exam.getExamId() != null && exam.getState().equals("1")){
				exam.setState("2");
				examInfoService.update(exam);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"开始考试"+Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				logger.error("开始考试失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("开始考试失败：！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 结束考试
	 * @param req
	 * @param resp
	 * @param exam
	 * @return
	 */
	@RequestMapping("/stopExam")
	@ResponseBody
	public APIContent stopExam(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Exam exam){
		logger.info("结束考试开始");
		try {
			if(exam.getExamId() != null && exam.getState().equals("2")){
				exam.setState("3");
				examInfoService.update(exam);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"结束考试"+Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				logger.error("结束考试失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("结束考试失败：！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除考试
	 * @param req
	 * @param resp
	 * @param exam
	 * @return
	 */
	@RequestMapping("/delExam")
	@ResponseBody
	public APIContent delExam(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute Exam exam){
		logger.info("删除考试开始");
		try { 
			if(exam.getExamId() != null && exam.getState().equals("1")){
				exam.setExamIds(exam.getExamId().split(","));
				examInfoService.deleteList(exam);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"删除考试"+Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				logger.error("删除考试失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("删除考试失败：！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	
}