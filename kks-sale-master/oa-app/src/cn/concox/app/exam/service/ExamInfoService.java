package cn.concox.app.exam.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.exam.mapper.ExamInfoMapper;
import cn.concox.app.exam.mapper.ExamTopicMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.memcache.Logger;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.DateUtil;
import cn.concox.comm.util.FileUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamTopic;
import cn.concox.vo.material.MaterialLog;
import cn.concox.vo.material.MaterialLogTemp;
import cn.concox.vo.materialManage.InteriorMaterial;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service
public class ExamInfoService {
	
	@Resource(name = "examInfoMapper")
	private ExamInfoMapper examInfoMapper;
	
	@Resource(name = "examTopicMapper")
	private ExamTopicMapper examTopicMapper;
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	
	@Value("${file_upload_url}")
	private String FILE_UPLOAD_URL;

	@SuppressWarnings("unchecked")
	public Page<Exam> getExamListPage(Exam Exam,int currentPage, int pageSize){
		
		Page<Exam> examPage = new Page<Exam>();
		examPage.setCurrentPage(currentPage);
		examPage.setSize(pageSize);
		
		examPage =examInfoMapper.queryExamListPage(examPage,Exam);
		return examPage;
	} 
	
	public void update(Exam exam){
		examInfoMapper.update(exam);
	} 
	
	public List<Exam> getExamInfo(){
		return examInfoMapper.queryExamInfo();
	} 
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteList(Exam exam){
		examInfoMapper.deleteList(exam);
		examTopicMapper.deleteList(exam);
	} 
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void addExam(Exam exam, MultipartFile multipartFile) throws Exception {	
		
		if ( null != multipartFile && multipartFile.getSize()>0) {
			String examId = getUUID().replaceAll("-", "");
			exam.setExamId(examId);
			exam.setCreateTime(new Date());
			exam.setState("1");
			
			// 从Excel文件获取题目列表
			List<ExamTopic> examTopicList = this.uploadExamTopicMaterialFile(multipartFile);
			//	排序和清除清除格式不符合要求的题目
			examTopicList = sort(examTopicList, examId);
			
			// 插入数据库
			examTopicMapper.insertList(examTopicList);
			examInfoMapper.insert(exam);
		} 			
	}
	
	public List<ExamTopic> uploadExamTopicMaterialFile(MultipartFile file) throws Exception {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<ExamTopic> examTopicList = new ArrayList<ExamTopic>();
		Object[] result;
		result = ReadExcel.readXls(file, null);
		// 获取题目数组m
		Object[] m = (Object[]) result[0];
		if (m.length < 3) {
			throw new Exception("没有数据");
		}
		for(int i = 3; i < m.length; i++) {
			Object[] n = (Object[]) m[i];
			ExamTopic examTopic = new ExamTopic();
			// 循环放入map
			for(int j = 0; j < n.length; j++) {
				map.put(j, (String) n[j]);
			}
			
			// 题目描述（必填）
			examTopic.setTopicDescription(map.get(0));
			//题目分值（必填）
			examTopic.setTopicScore(map.get(1));
			// 题目类型（必填）
			examTopic.setTopicType(map.get(2));
			// 试题答案（必填）
			examTopic.setTopicAnswer(map.get(3));
			// 选项A描述（非必填）
			examTopic.setTopicOptionA(map.get(4));
			// 选项B描述（非必填）
			examTopic.setTopicOptionB(map.get(5));
			// 选项C描述（非必填）
			examTopic.setTopicOptionC(map.get(6));
			// 选项D描述（非必填）
			examTopic.setTopicOptionD(map.get(7));
		
			map.clear();
			examTopicList.add(examTopic);
		}

		return examTopicList;
	}
	
	/**
	 * 对题目进行排序
	 * @param examTopicList
	 * @return
	 */
	public List<ExamTopic> sort(List<ExamTopic> examTopicList, String examId) {
		
		// 正确的题目列表
		List<ExamTopic> trueList = new ArrayList<ExamTopic>();
		
		int choice = 1;
		int fill = 1;
		int judge = 1;
		int subjective = 1;
		
		// 排序
		for(ExamTopic examTopic : examTopicList) {
		
			if(examTopic.getTopicType().equals("选择题")) {
				examTopic.setTopicSequence(choice);
				examTopic.setTopicType("1");
				choice++;
				examTopic.setExamId(examId);
				examTopic.setTopicId(getUUID());
				trueList.add(examTopic);
				continue;
			}
			
			if(examTopic.getTopicType().equals("判断题")) {
				
				examTopic.setTopicAnswer(examTopic.getTopicAnswer().equals("正确") ? "A" : "B");
				examTopic.setTopicSequence(fill);
				examTopic.setTopicType("2");
				fill++;	
				examTopic.setExamId(examId);
				examTopic.setTopicId(getUUID());
				trueList.add(examTopic);
				continue;
			}
			
			if(examTopic.getTopicType().equals("填空题")) {
				examTopic.setTopicSequence(judge);
				examTopic.setTopicType("3");
				judge++;
				examTopic.setExamId(examId);
				examTopic.setTopicId(getUUID());
				trueList.add(examTopic);
				continue;
			}
			
			if(examTopic.getTopicType().equals("主观题")) {
				examTopic.setTopicSequence(subjective);
				examTopic.setTopicType("4");
				subjective++;
				examTopic.setExamId(examId);
				examTopic.setTopicId(getUUID());
				trueList.add(examTopic);
				continue;
			}	
		}
		return trueList;
	}
	
	

	
	/**
	 * 获取时间戳
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	/**
	 * 导出试题模板
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void exportTemplate(HttpServletRequest request,HttpServletResponse response) throws IOException {

		String  fileName1  = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
				.append(GlobalCons.EXAM)
				.append("template.xls")
				.toString();
		try {
			byte[] b1 = readFileByBytes(fileName1);
			response.addHeader("Content-Disposition",
			"attachment;filename=template.xls"); 
			response.setContentType("application/x-download");
			response.getOutputStream().write(b1);
	
			} catch (Exception e) {
				System.out.println("下载失败");
			}
	
	}
	
	
	public byte[] readFileByBytes(String fileName) {
		InputStream in = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			in = new FileInputStream(fileName);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
			}
		} catch (Exception e1) {
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				
				}	
			}
		}
		return out.toByteArray();
	}
}
