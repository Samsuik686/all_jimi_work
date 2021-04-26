package cn.concox.app.cumstomFlow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Ycfkmanage;
import cn.concox.vo.customProcess.CustomTask;
import cn.concox.vo.customProcess.CustomTaskFile;

public interface CustomTaskMapper<T> extends BaseSqlMapper<T>{
	public Page<CustomTask>  queryCustomTaskListPage(@Param(PageInterceptor.PAGE_KEY) Page<CustomTask> page, @Param("po") T po) throws  DataAccessException; 

	public List<CustomTask> queryCustomTaskListPage(@Param("po") CustomTask po);
	
	public List<CustomTask> ycfkCountList(CustomTask ycfk);
	
	public void deleteBySerials(@Param("ids")String... ids);
	
	public void deleteByFlowName(@Param("flowName")String flowName);
	
	@Delete("DELETE FROM t_sale_custom_task_file WHERE yid IN (SELECT id FROM t_sale_custom_task where flow_name = #{flowName})")
	public void deleteFileByFlowName(@Param("flowName")String flowName);
	
	@Select("SELECT file_url FROM t_sale_custom_task_file WHERE yid IN (SELECT id FROM t_sale_custom_task where flow_name = #{flowName})")
	public List<String> selectFileUrlByFlowName(@Param("flowName")String flowName);
	
	public void insertYcfkFile(CustomTaskFile file);
	
	public List<CustomTaskFile> getCustomTaskFile(@Param("yid")Integer yid);
	
	public void deleteFileByFid(@Param("fid") Integer fid);
	
	@Select("SELECT a.file_url from"
			+ "(SELECT file_url,count(0) as count FROM t_sale_custom_task_file where file_url =" 
			+ "(SELECT file_url FROM t_sale_custom_task_file WHERE fid = #{fid})"
			+ ") AS a where a.count = 1")
	public String selectFileUrlByFid(@Param("fid") Integer fid);
	
	/**
	 * 删除异常反馈时连带删除附件信息
	 * @author TangYuping
	 * @version 2017年4月14日 下午2:33:31
	 * @param yids
	 */
	public void deleteFileByYid(@Param("yids")String[] yids);
	
	public List<String> selectFileUrlByYids(@Param("yids")String[] yids);
	
	public Page<CustomTask>  queryYcfkmanageListPage(@Param(PageInterceptor.PAGE_KEY) Page<CustomTask> page, @Param("po") T po) throws  DataAccessException; 

	public List<CustomTask> ycfkCountList(Ycfkmanage ycfk);

	public void updateStateFinish(@Param("ids")String... ids);
	
	public List<CustomTask> selectByIds(@Param("ids")String... ids);

	@Update("update t_sale_custom_task set state = '2' where now() > expire_date and state != '2'")
	public void updateExpireFixed();
		
}
