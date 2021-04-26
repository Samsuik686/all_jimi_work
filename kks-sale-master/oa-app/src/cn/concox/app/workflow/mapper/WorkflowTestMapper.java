package cn.concox.app.workflow.mapper;

import org.apache.ibatis.annotations.Param;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowTest;


/**
 * @author TangYuping
 * @version 2017年2月20日 下午4:52:35
 * @param <T>
 */
public interface WorkflowTestMapper<T> extends BaseSqlMapper<T>{
	
	public Page<Workflow> workflowTestList(@Param(PageInterceptor.PAGE_KEY) Page<Workflow> page, 
											@Param("po") Workflow workflow);
	
	public WorkflowTest getWorkflowTestByWfId(@Param("wfId")  String wfId);
	
	public void updateStatus(@Param("status") long status, @Param("ids") String... ids);
	
	public void updateTestPerson(@Param("testPerson") String testPerson, @Param("ids") String... ids);
	
	/**
	 * @Title: getCountToComeBackByWfids 
	 * @Description:测试工站查询选中的数据是否有不是已完成？有：不允许点击返回数据来源工站
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月10日 上午10:03:07
	 */
	public int getCountToComeBackByWfids(@Param("ids") String... ids);
	
	/**
	 * @Title: getCountUpdateByWfids 
	 * @Description:测试工站查询选中的数据是否有不是未完成或未发送？有：不允许点击修改
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月10日 上午10:56:50
	 */
	public int getCountUpdateByWfids(@Param("ids") String... ids);
}
