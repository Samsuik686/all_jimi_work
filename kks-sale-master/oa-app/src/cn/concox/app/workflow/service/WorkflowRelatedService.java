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
package cn.concox.app.workflow.service;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.concox.app.workflow.mapper.WorkflowRelatedMapper;
import cn.concox.vo.workflow.WorkflowRelated;

/**
 * 工作流程 公共处理
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
@Service("workflowRelatedService")
@Scope("prototype")
public class WorkflowRelatedService{
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="relatedMapper")
	private  WorkflowRelatedMapper<WorkflowRelated> relatedMapper;
	
}
