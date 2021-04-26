package cn.concox.app.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.ExamGrade;

public interface ExamGradeMapper extends BaseSqlMapper<ExamGrade>{

	public Page<ExamGrade>  queryExamGradeListPage(@Param(PageInterceptor.PAGE_KEY) Page<ExamGrade> page, @Param("examGrade") ExamGrade examGrade) throws  DataAccessException; 

	public List<ExamGrade> queryExamGradeListPage( @Param("examGrade") ExamGrade examGrade);
	
	public void updateExamGradeByExamId(@Param("examId") String examId, @Param("examineeId") String examineeId);
	
	public ExamGrade insertExamGrade(@Param("examGrade") ExamGrade examGrade);
	
	void updateExamGrade(ExamGrade examGrade);

    List<ExamAnswer> listExamAnswer(@Param("examId")String examId, @Param("examineeId")String examineeId);

}
