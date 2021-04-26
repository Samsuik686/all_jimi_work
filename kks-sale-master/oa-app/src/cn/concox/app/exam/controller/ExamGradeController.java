package cn.concox.app.exam.controller;

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
import cn.concox.app.exam.service.ExamGradeService;
import cn.concox.app.exam.service.ExamInfoService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.memcache.Logger;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.YcfkTwomanage;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamGrade;


@Controller
@Scope("prototype")
@RequestMapping("/examGrade")
public class ExamGradeController extends BaseController {

	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name = "examGradeService")
	private ExamGradeService examGradeService;
	
	/**
	 * 获取考试成绩，分页
	 * @param examGrade
	 * @param comp
	 * @param req
	 * @return
	 */
	@RequestMapping("/examGradeList")
	@ResponseBody
	public APIContent getExamListPage(@ModelAttribute ExamGrade examGrade, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			
			Page<ExamGrade> examGradeListPage = examGradeService.getExamGradeListPage(examGrade, comp.getCurrentpage(), comp.getPageSize());
			return super.putAPIData(examGradeListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取考试成绩列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 导出考生成绩
	 * @param examGrade
	 * @param req
	 * @param response
	 */
	@RequestMapping("/exportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute ExamGrade examGrade,HttpServletRequest req,HttpServletResponse response){
		try{
			examGradeService.ExportDatas(examGrade,req,response);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出考试成绩信息数据失败:"+e.toString());
		}
	}
	
	
	
}