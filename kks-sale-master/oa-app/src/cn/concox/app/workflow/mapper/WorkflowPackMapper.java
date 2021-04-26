package cn.concox.app.workflow.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.comm.base.rom.BaseSqlMapper;

public interface WorkflowPackMapper<T> extends BaseSqlMapper<T>{

	List<String> getPackTimeList(@Param("type")String type);

	List<String> getPackTimeByTreetime(@Param("type")String type, @Param("treeTime") String treeTime);
	
	List<String> getPackedTimeList(@Param("type")String type);

	List<String> getPackedTimeByTreetime(@Param("type")String type, @Param("treeTime") String treeTime);
}
