package cn.concox.app.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamTopic;

public interface ExamTopicMapper extends BaseSqlMapper<Exam>{
	
	public void insertList(@Param("list") List<ExamTopic> list);

	public void deleteList(Exam exam);
}
