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
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.workflow.WorkflowRelated;
/**
 * 工作流程公共处理 
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
public interface WorkflowRelatedMapper<T> extends BaseSqlMapper<T>{
	
	public void doInsert(WorkflowRelated related) throws DataAccessException;

	public void deleteByIds(@Param("ids") String... ids); 

}
