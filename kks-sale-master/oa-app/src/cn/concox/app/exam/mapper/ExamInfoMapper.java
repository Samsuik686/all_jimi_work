package cn.concox.app.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.exam.Exam;

public interface ExamInfoMapper extends BaseSqlMapper<Exam>{

	public Page<Exam>  queryExamListPage(@Param(PageInterceptor.PAGE_KEY) Page<Exam> page, @Param("exam") Exam exam) throws  DataAccessException; 

	public void deleteList(@Param("exam") Exam exam);
	
	public List<Exam> queryExamInfo();
}
