/*
 * Created: 2016-7-12
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
 **/
package cn.concox.comm.jbpm.hq;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;


/***
 * 一票否决制
 * @author hl
 *
 */
public class CounterSignCommand implements Command<Boolean> {
	private static final long serialVersionUID = 1L;
	public static final String VAR_SIGN = "sign";
	private String transitionPassName;			 
	private String transitionNoPassName;		
	private String parentTaskId;				
	private Task parentTask;					
	private String pid;
	private Task joinsignTask;
	
	
	public Integer getA() {
		return a;
	}
	public void setA(Integer a) {
		this.a = a;
	}
	public Integer getB() {
		return b;
	}
	public void setB(Integer b) {
		this.b = b;
	}

	private Integer a;
	private Integer b;
	
	
	private ProcessDefinitionImpl pd;
	
	public ProcessDefinitionImpl getPd() {
		return pd;
	}
	public void setPd(ProcessDefinitionImpl pd) {
		this.pd = pd;
	}

	private ProcessEngine processEngine;
	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	private TaskService taskService;
	
	public TaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private boolean sign;

	public boolean isSign() {
		return sign;
	}
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	public String getPid() {
		return pid;
	}
	public void setJoinsignTask(Task joinsignTask) {
		this.joinsignTask = joinsignTask;
	}

	
	private String pidyhh;
	
	public String getPidyhh() {
		return pidyhh;
	}
	public void setPidyhh(String pidyhh) {
		this.pidyhh = pidyhh;
	}
	/**
	 * 构造方法
	 * 
	 * @param transitionPassName
	 * @param trainsitionNoPassName
	 * @param parentTaskId
	 */
	public CounterSignCommand(String transitionPassName,String trainsitionNoPassName, String parentTaskId, boolean sign) {
		super();
		this.sign = sign;
		this.transitionPassName = transitionPassName;
		this.transitionNoPassName = trainsitionNoPassName;
		this.parentTaskId = parentTaskId;
		
		
		
	}
	
	public Boolean process() {
		TaskService taskService = this.taskService;
		this.parentTask = taskService.getTask(parentTaskId);
		this.pid = parentTask.getExecutionId();
		String joinsignTaskId = joinsignTask.getId();
		if (!sign) {
			taskService.completeTask(joinsignTaskId);
			taskService.addTaskComment(parentTaskId, joinsignTask.getAssignee()+ "审批意见:" + VAR_SIGN);
			return true;
		}
		TaskImpl test  =(TaskImpl)this.parentTask;
		taskService.completeTask(joinsignTaskId); 
		
		if ( taskService.getSubTasks(parentTaskId).size() == 0||a==b) {
		
			if (this.pd == null) {
				pd = (ProcessDefinitionImpl) this.processEngine.getRepositoryService().createProcessDefinitionQuery().deploymentId(pidyhh).uniqueResult();
		        
			}
			ActivityImpl sourceActivity = pd.findActivity(parentTask.getActivityName());  //yuan
			taskService.completeTask(parentTaskId, sourceActivity.findDefaultTransition().getName());
			
			return true;
		}
		
		
		taskService.addTaskComment(parentTaskId, joinsignTask.getAssignee()+ "审批意见:" + VAR_SIGN);
		taskService.getSubTasks(parentTaskId).size();
		
		
		
		
		
		
		
		// 返回false。表示会签活动尚未结束,继续惊进行
		return false;
	}

	

	@Override
	public Boolean execute(Environment environment) throws Exception {
		TaskService taskService = environment.get(TaskService.class);// 获取任务服务
		this.parentTask = taskService.getTask(parentTaskId);// 计算出主任务和流程实例ID
		this.pid = parentTask.getExecutionId();
		String joinsignTaskId = joinsignTask.getId();// 获取当前的会签任务
		if (!sign) {
			taskService.completeTask(joinsignTaskId);// 首先结束会签任务
			taskService.addTaskComment(parentTaskId, joinsignTask.getAssignee()+ "审批意见:" + VAR_SIGN);// 为会签主任务增加一条会签意见的注释记录,一杯日后查询
			return true;// 结束主任务并流向预定义的会签否决转移
		}
		TaskImpl test  =(TaskImpl)this.parentTask;
		taskService.completeTask(joinsignTaskId); 
		// 判断是否还有会签子任务
		if (taskService.getSubTasks(parentTaskId).size() == 0) {
			// 已无会签子任务,表明全体会签通过
			// 结束主任务，并流向会签通过转移
			taskService.completeTask(parentTaskId);
			// 返回true,表示会签活动结束
			return true;
		}
		
		
		//taskService.completeTask(joinsignTaskId);// 会签任务通过
		taskService.addTaskComment(parentTaskId, joinsignTask.getAssignee()+ "审批意见:" + VAR_SIGN);// 为主任务增加一条注释
		taskService.getSubTasks(parentTaskId).size();// [color=red]此处会出错。。[/color]
		
		
		
		
		
		
		
		// 返回false。表示会签活动尚未结束,继续惊进行
		return false;
	}

}
