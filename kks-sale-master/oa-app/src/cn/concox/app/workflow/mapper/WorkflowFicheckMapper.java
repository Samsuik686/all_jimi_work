/*
 * Created: 2016-7-20
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.app.workflow.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.workflow.WorkflowFicheck;
/**
 * 工作流程公共处理 
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
public interface WorkflowFicheckMapper<T> extends BaseSqlMapper<T>{

	void updateState(@Param("state") Integer state, @Param("ids") String... ids);  
	
	public WorkflowFicheck getByWfid(Integer wfId);
	
	@SuppressWarnings("rawtypes")
	public Page<WorkflowFicheck> getFicheckPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") WorkflowFicheck ficheck);
	
	public List<WorkflowFicheck> getFicheckList(WorkflowFicheck ficheck);
	
	public Long selGiveUpPriceCount(WorkflowFicheck ficheck);
	
	public void updateStateByWfid(@Param("state") Integer state, @Param("ids") String... ids);
	
	/**
	 * @Title: getCountToRepairByWfids 
	 * @Description:终检工站查询选中的数据是否有不是已终检，待维修？有：不允许点击发送维修
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月9日 下午3:22:17
	 */
	public int getCountToRepairByWfids(@Param("ids") String... ids);
	
	/**
	 * @Title: getCountToPackByWfids 
	 * @Description:终检工站查询选中的数据是否有不是已终检，待装箱？有：不允许点击发送装箱
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月9日 下午3:22:21
	 */
	public int getCountToPackByWfids(@Param("ids") String... ids);
	
	/**
	 * @Title: getCountToTestByWfids 
	 * @Description:终检工站查询选中的数据是否有不是待终检？有：不允许点击发送测试，待终检返回维修
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月10日 下午2:06:35
	 */
	public int getCountToTestByWfids(@Param("ids") String... ids);
	
	/**
	 * @Title: deleteFicheckByWfids 
	 * @Description:删除终检数据
	 * @param ids 
	 * @author 20160308
	 * @date 2017年11月28日 下午4:54:33
	 */
	public void deleteFicheckByWfids(@Param("ids") String... ids);
	
}
