package cn.concox.app.exam.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.exam.mapper.ExamGradeMapper;
import cn.concox.app.exam.mapper.MarkScoreMapper;
import cn.concox.app.exam.mapper.StartExamMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.ExamGrade;
import cn.concox.vo.exam.ExamTopic;
import freemarker.template.Template;

@Service
public class ExamGradeService {

    @Resource(name = "examGradeMapper")
    private ExamGradeMapper examGradeMapper;

    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource(name = "startExamMapper")
    private StartExamMapper startExamMapper;

    @Resource(name = "markScoreMapper")
    private MarkScoreMapper markScoreMapper;

    public Page<ExamGrade> getExamGradeListPage(ExamGrade examGrade, int currentPage, int pageSize) {

        Page<ExamGrade> examGradePage = new Page<ExamGrade>();
        examGradePage.setCurrentPage(currentPage);
        examGradePage.setSize(pageSize);
        examGradePage = examGradeMapper.queryExamGradeListPage(examGradePage, examGrade);
        return examGradePage;
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    public void ExportDatas(ExamGrade examGrade, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<ExamGrade> examGradeList = examGradeMapper.queryExamGradeListPage(examGrade);

        // 导出时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String exportDate = sdf.format(date).toString();
        String[] fieldNames = new String[] { "序号", "考试名称", "考生姓名", "考生成绩", "评阅状态 ", "考试用时", "开始时间" };

        Map map = new HashMap();
        map.put("size", examGradeList.size() + 1);
        map.put("peList", examGradeList);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        map.put("exportDate", exportDate);
        String fileName = new StringBuilder("考生成绩表--").append(exportDate).append(GlobalCons.FILE_ENDTYPE_XLS)
                .toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.EXAM).append("examGrade.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

    /**
     * 计算选择题分数
     */
    public void markChoiceScore(String examId, String examineeId) {

        // 根据考试examId拿到所有题目的答案
        List<ExamTopic> examTopics = startExamMapper.listExamTopic(examId);

        // 根据考试examId和学生编号拿到学生做的题
        List<ExamAnswer> examAnswers = examGradeMapper.listExamAnswer(examId, examineeId);

        for (ExamAnswer answer : examAnswers) {
            for (ExamTopic topic : examTopics) {
                // 答案和题目相同且为选择题
                if (answer.getTopicId().equals(topic.getTopicId())
                        && (answer.getTopicType().equals("1") || answer.getTopicType().equals("2"))) {
                    if (compareArg(answer.getExamineeAnswer(), topic.getTopicAnswer())) {
                        answer.setExamineeScore(Integer.valueOf(topic.getTopicScore()));
                    } else {
                        answer.setExamineeScore(0);
                    }
                    markScoreMapper.updateExamAnswer(answer);
                }
            }
        }

    }

    /**
     * 判断ac = ca
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean compareArg(String a, String b) {
        if (StringUtil.isEmpty(a) || StringUtil.isEmpty(b)) {
            return false;
        }
        if (a.length() != b.length()) {
            return false;
        }
        a = a.toLowerCase();
        b = b.toLowerCase();

        char[] ac = a.toCharArray();
        char[] bc = b.toCharArray();
        Arrays.sort(ac);
        Arrays.sort(bc);
        a = String.valueOf(ac);
        b = String.valueOf(bc);
        return a.equals(b);
    }

    /**
     * @Title: updateExamGradeByExamId
     * @Description:计算总成绩
     * @param examId
     * @param examineeId
     * @author Wang Xirui
     * @date 2019年7月15日 下午5:42:38
     */
    public void updateExamGradeByExamId(String examId, String examineeId) {
        examGradeMapper.updateExamGradeByExamId(examId, examineeId);
    }

    /**
     * @Title: updateExamGrade
     * @Description:更新成绩表状态和考试用时
     * @param examGrade
     * @author Wang Xirui
     * @date 2019年7月16日 上午9:50:23
     */
    public void updateExamGrade(ExamGrade examGrade) {
        examGradeMapper.updateExamGrade(examGrade);
    }
}
