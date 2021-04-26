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
package cn.concox.comm.jbpm;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.examples.task.assignee.Order;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.concox.comm.jbpm.face.JBPMFaceCade;
import cn.concox.comm.jbpm.hq.CounterSignCommand;







@Component("JBPMFaceCade")
@Scope("prototype")
public class JBPMFaceCadeImpl implements JBPMFaceCade{
	
	
	@Resource(name="processEngine")
	ProcessEngine processEngine;
	
	@Resource(name="repositoryService")
	RepositoryService repositoryService;
	@Resource(name="executionService")
	ExecutionService executionService;
	@Resource(name="taskService")
	TaskService taskService;
	
	
	/****
	 * 审批通过
	 * @param taskId
	 */
	@Override
	public boolean completeTask(String taskId, String pid, String name) {
		//TaskService taskService = processEngine.getTaskService();
		Task task = taskService.getTask(taskId);
		List subs = taskService.getSubTasks(taskId);
		if (subs!=null && subs.size()>0) {
			List datas = taskService.findPersonalTasks(name);
			if (datas!=null && datas.size()>0) {
				Task subtask  = (Task) datas.get(0);
				CounterSignCommand cs = new CounterSignCommand("审批通过", "审批不通过", task.getId(), true);
				boolean  result = false;
				cs.setJoinsignTask(subtask);
				result =processEngine.execute(cs);
				return false;
			}
		}else{
			taskService.completeTask(taskId);
			
			ProcessInstance pi = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(pid).uniqueResult();
			if (pi==null) {
				//说明流程已经到了最后,更改MATTER实例
				return true;
			}
			return false;
		}
		return false;
		
	}
	
	
	

	
	/***
	 * 回退
	 * @param taskId
	 * @param pid
	 */
	@Override
	public void backTask(String taskId, String pid, String nodename) {
		
	     EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;  
	     org.jbpm.pvm.internal.env.EnvironmentImpl  env=null; 
	     //TaskService taskService =processEngine.getTaskService();
	     //RepositoryService rs = this.processEngine.getRepositoryService();
		 Task task = taskService.getTask(taskId);
		 List subs = taskService.getSubTasks(taskId);
		 if (subs!=null && subs.size()>0) {
			 
			 List datas = taskService.findPersonalTasks("b");
				if (datas!=null && datas.size()>0) {
					Task subtask  = (Task) datas.get(0);
					CounterSignCommand cs = new CounterSignCommand("审批通过", "审批不通过", task.getId(), false);
					boolean  result = false;
					cs.setJoinsignTask(subtask);
					result =processEngine.execute(cs);
					return;
				}
			 
		 }else{
			 TaskImpl sourceTask = (TaskImpl) taskService.getTask(taskId);
		     try {  
		   	  ProcessDefinitionImpl pd = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().deploymentId(pid).uniqueResult();
		   	  env = ((EnvironmentFactory)processEngine).openEnvironment();
		         //取得当前流程的活动定定义  
		         ActivityImpl sourceActivity = pd.findActivity(sourceTask.getActivityName());  
		         //取得目标的活动定义  
		         ActivityImpl destActivity=pd.findActivity(nodename);  
		         //为两个节点创建连接  
		         TransitionImpl transition = sourceActivity.createOutgoingTransition();  
		         transition.setName("to" + nodename);
		         transition.setDestination(destActivity);
		         sourceActivity.addOutgoingTransition(transition);  
//		         System.out.println("sourceActivity.getName() = "+sourceActivity.getName());  
//		         System.out.println("destActivity.getName() = "+destActivity.getName());  
		           
		         Map<String, Object> variables = new HashMap<String, Object>();   
		         variables.put("order", new Order("johndoe"));  
//		         System.out.println("task1.getId() = "+sourceTask.getId());  
		          
		         taskService.completeTask(sourceTask.getId(),transition.getName(),variables);  
		     }catch(Exception ex){  
		    	 ex.printStackTrace();
		         ex.getMessage();  
		     }finally{    
		         env.close();  
		     }  
		 }
		
		
		
	}
	
	
	
	/***
	 * 回退
	 * @param taskId
	 * @param pid
	 */
	@Override
	public void backTaskV1(String taskId, String pid, String nodename, String name) {
		
	     EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;  
	     //org.jbpm.pvm.internal.env.EnvironmentImpl  env=null; 
	     //TaskService taskService =processEngine.getTaskService();
	    // RepositoryService rs = this.processEngine.getRepositoryService();
		 Task task = taskService.getTask(taskId);
		 List subs = taskService.getSubTasks(taskId);
		 if (subs!=null && subs.size()>0) {
			 List datas = taskService.findPersonalTasks(name);
				if (datas!=null && datas.size()>0) {
					Task subtask  = (Task) datas.get(0);
					TaskImpl sourceTask = (TaskImpl) taskService.getTask(taskId);
					 ProcessDefinitionImpl pd = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().deploymentId(pid).uniqueResult();
//				   	  env = ((EnvironmentFactory)processEngine).openEnvironment();
				         //取得当前流程的活动定定义  
				         ActivityImpl sourceActivity = pd.findActivity(sourceTask.getActivityName());  
				         //取得目标的活动定义  
				         ActivityImpl destActivity=pd.findActivity(nodename);  
				         //为两个节点创建连接  
				         TransitionImpl transition = sourceActivity.createOutgoingTransition();  
				         transition.setName("to" + nodename);
				         transition.setDestination(destActivity);
				         sourceActivity.addOutgoingTransition(transition);  
					 CounterSignCommand cs = new CounterSignCommand("审批通过", transition.getName(), task.getId(), false);
					boolean  result = false;
					cs.setJoinsignTask(subtask);
					cs.setTaskService(taskService);
					cs.setPidyhh(pid);
					result =cs.process();
					//result =processEngine.execute(cs);
					Map<String, Object> variables = new HashMap<String, Object>();   
			         variables.put("order", new Order("johndoe"));  
					taskService.completeTask(task.getId(), transition.getName(),variables);
					return;
				}
		 }else{
			 TaskImpl sourceTask = (TaskImpl) taskService.getTask(taskId);
		     try {  
		   	  ProcessDefinitionImpl pd = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().deploymentId(pid).uniqueResult();
		   	  //env = ((EnvironmentFactory)processEngine).openEnvironment();
		         //取得当前流程的活动定定义  
		         ActivityImpl sourceActivity = pd.findActivity(sourceTask.getActivityName());  
		         //取得目标的活动定义  
		         ActivityImpl destActivity=pd.findActivity(nodename);  
		         //为两个节点创建连接  
		         TransitionImpl transition = sourceActivity.createOutgoingTransition();  
		         transition.setName("to" + nodename);
		         transition.setDestination(destActivity);
		         sourceActivity.addOutgoingTransition(transition);
		         Map<String, Object> variables = new HashMap<String, Object>();   
		         variables.put("order", new Order("johndoe"));  
		         taskService.completeTask(sourceTask.getId(),transition.getName(),variables);  
		     }catch(Exception ex){  
		    	 ex.printStackTrace();
		         ex.getMessage();  
		     }finally{    
		         //env.close();  
		     }  
		 }
	}
	
	
	
	/****
	 * 审批通过
	 * @param taskId
	 */
	@Override
	public List completeTaskV1(String taskId, String pid, String name, Integer qh, Integer a, Integer b) {
		//TaskService taskService = processEngine.getTaskService();
		//RepositoryService rs = this.processEngine.getRepositoryService();
		ProcessInstance pi = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(pid).uniqueResult();
		Task task = taskService.getTask(taskId);
		List subs = taskService.getSubTasks(taskId);
		List rtdata = new ArrayList();
		Boolean isend = false;
		Integer hq = 0;
		if (subs!=null && subs.size()>0) {
			List datas = taskService.findPersonalTasks(name);
			if (datas!=null && datas.size()>0) {
				Task subtask  = (Task) datas.get(0);
				CounterSignCommand cs = new CounterSignCommand("审批通过", "审批不通过", task.getId(), true);
				boolean  result = false;
				cs.setJoinsignTask(subtask);
				cs.setTaskService(taskService);
				ProcessDefinitionImpl pd = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
				cs.setPidyhh(pd.getDeploymentId());
				cs.setPd(pd);
				cs.setA(a);
				cs.setB(b);
				
				result =cs.process();
				//判断是否最后一个节点
				
				
				
				hq =1;
				rtdata.add(false);rtdata.add(1);
			}
		}else{
			
			 ProcessDefinitionImpl pd = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
	         ActivityImpl sourceActivity = pd.findActivity(task.getActivityName());  //yuan
			taskService.completeTask(taskId, sourceActivity.findDefaultTransition().getName());
		}
		
		if (pi.isEnded()) {
			
			isend= true;
			//说明流程已经到了最后,更改MATTER实例
			
		}
		rtdata.add(isend);rtdata.add(hq);
		return rtdata;
		
	}
	
	
	
	
	/****
	 * 启动流程
	 * @param processId
	 * @param map
	 * @return
	 */
	@Override
	public String runProcess(String processId, Map map) {
		ExecutionService es = processEngine.getExecutionService();
		RepositoryService rp = processEngine.getRepositoryService();
		ProcessInstance pi = es.startProcessInstanceByKey(processId,map);
		return pi.getId();
	}
	
	
	/*****
	 * 发布流程
	 * @param zis
	 * @return
	 */
	@Override
	public List upLoadProcess(ZipInputStream zis) {
		
		//TaskService ts = processEngine.getTaskService();
		//RepositoryService repositoryService = processEngine.getRepositoryService();
		String processid = repositoryService.createDeployment().addResourcesFromZipInputStream(zis).deploy();
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(processid).uniqueResult();
		List<String> datas = new ArrayList<String>();
		String key = pd.getKey();
		datas.add(key);datas.add(pd.getDeploymentId());datas.add(processid);datas.add(pd.getId());
		return datas;
	}
	@Override
	public void delProcess(String processId) {

		//processEngine.getRepositoryService().deleteDeployment(processId);
		repositoryService.deleteDeployment(processId);
		
		
	}
	
	/***
	 * 查找某用户下所有的待审节点信息
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unused")
	@Override
	public List<Task> findAllTask(String name) {
		//ExecutionService es = processEngine.getExecutionService();
		//TaskService ts = processEngine.getTaskService();
		List<Task> tasks = this.taskService.findGroupTasks(name);
		List<Task> taskss = this.taskService.findPersonalTasks(name);
		List<Task> datas = new ArrayList<Task>();
		
		if (taskss!=null && taskss.size()>0) {
			for (int i = 0; i < taskss.size(); i++) {
				Task task = taskss.get(i);
				if (task.getName()==null||task.getName().equals("会签节点")) {
					continue;
				}
				datas.add(task);
			}
		}
		if (tasks!=null && tasks.size()>0) {
			tasks.addAll(datas);
		}else{
			return datas;
		}
		return tasks;
		
	}
	
	/***
	 * 转发节点信息
	 * @param taskId  lu360414717
	 * @param uid
	 */
	@Override
	public void replaceTask(String taskId, String uid) {
		org.jbpm.pvm.internal.env.EnvironmentImpl env = ((EnvironmentFactory)processEngine).openEnvironment();
		try{
			//TaskService taskService = processEngine.getTaskService();
			Task task = taskService.getTask(taskId);
			//task.setAssignee("cc");
			task.setAssignee(uid);
		} finally{
		   env.close();
		}
	}
	
	
	/****
	 * 
	 * 
	 
	 * 
	 */
	
	@Override
	public BufferedImage getPic(String processInstanceId, String imgName) throws IOException{
		
		//RepositoryService rs = processEngine.getRepositoryService();
		//ExecutionService es = processEngine.getExecutionService();
		ProcessInstance pi = this.executionService.findProcessInstanceById(processInstanceId);
		String pdId = pi.getProcessDefinitionId();
		ProcessDefinition pd = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		Set<String> activityNames = pi.findActiveActivityNames();
		
		BufferedImage image = null;
		for (String activityName : activityNames)  {
		    ActivityCoordinates ac = this.repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), activityName);
		    InputStream is = this.repositoryService.getResourceAsStream(pd.getDeploymentId(), imgName);
		    if (is != null) {
		    	image = ImageIO.read(is);
			    Graphics g = image.getGraphics();
			    g.setColor(new Color(255, 0, 0));
			    g.drawRect(ac.getX()+3, ac.getY(), ac.getWidth(), ac.getHeight());
			    g.setColor(new Color(0, 0, 0));
			    g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
				g.drawString("会签节点",ac.getX(), ac.getY());
			    g.dispose();
			    
			}
		    
		}
		
		
		return image;
	}
	
	
	
	@Override
	public BufferedImage getPicV1(String processInstanceId, String imgName, List names) throws IOException{
		
		//RepositoryService rs = processEngine.getRepositoryService();
		//ExecutionService es = processEngine.getExecutionService();
		ProcessInstance pi = this.executionService.findProcessInstanceById(processInstanceId);
		String pdId = pi.getProcessDefinitionId();
		ProcessDefinition pd = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		
		String did = pd.getDeploymentId();
		
		Set<String> activityNames = pi.findActiveActivityNames();
		BufferedImage image = null;
		
		InputStream is = this.repositoryService.getResourceAsStream(pd.getDeploymentId(), imgName);
		image = ImageIO.read(is);
	    Graphics g = image.getGraphics();
	    List<ActivityImpl> allnames = (List<ActivityImpl>) this.getAllTask(pd.getKey());
		for (ActivityImpl a : allnames)  {
			ActivityCoordinates ac = this.repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), a.getName());
		    
			
			
			for (int i = 0; i < names.size(); i++) {
				String name = (String) names.get(i);
				if (name.equals(a.getName())) {
					 g.setColor(new Color(0, 0, 0));
					 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
					 g.drawString("会签节点",ac.getX(), ac.getY());
				}
			}
			
			
		}
		
		
		
		
		for (String activityName : activityNames)  {
		    ActivityCoordinates ac = this.repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), activityName);
		    //InputStream is = rs.getResourceAsStream(pd.getDeploymentId(), imgName);
		    if (is != null) {
		    	//image = ImageIO.read(is);
			    //Graphics g = image.getGraphics();
			    g.setColor(new Color(255, 0, 0));
			    g.drawRect(ac.getX()+3, ac.getY(), ac.getWidth(), ac.getHeight());
			    
			   /** for (int i = 0; i < names.size(); i++) {
					String name = (String) names.get(i);
					if (name.equals(activityName)) {
						 g.setColor(new Color(0, 0, 0));
						 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
						 g.drawString("会签节点",ac.getX(), ac.getY());
					}
				}***/
				g.dispose();
			}
		}
		return image;
	}
	
	
	
	
	@Override
	public BufferedImage getPicV2(String processInstanceId, String imgName, List names, Map<String,String> map) throws IOException{
		
		//RepositoryService rs = processEngine.getRepositoryService();
		//ExecutionService es = processEngine.getExecutionService();
		ProcessInstance pi = this.executionService.findProcessInstanceById(processInstanceId);
		String pdId = pi.getProcessDefinitionId();
		ProcessDefinition pd = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		Set<String> activityNames = pi.findActiveActivityNames();
		BufferedImage image = null;
		
		String did = pd.getDeploymentId();
		
		InputStream is = this.repositoryService.getResourceAsStream(pd.getDeploymentId(), imgName);
		image = ImageIO.read(is);
	    Graphics g = image.getGraphics();
	    List<ActivityImpl> allnames = (List<ActivityImpl>) this.getAllTask(pd.getDeploymentId());
		for (ActivityImpl a : allnames)  {
			ActivityCoordinates ac = this.repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), a.getName());
		    
			String key = "acd"+ac.getX()+ac.getY()+ac.getWidth()+ac.getHeight();
			String persons = map.get(key);
			
			 g.setColor(new Color(0, 0, 0));
			 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
			 g.drawString(persons,ac.getX(), ac.getY());
			
			for (int i = 0; i < names.size(); i++) {
				String name = (String) names.get(i);
				if (name.equals(a.getName())) {
					 g.setColor(new Color(102, 0, 153));
					 g.setFont(new Font("宋体", Font.BOLD, 12));
					 g.drawString("会签",ac.getX(), ac.getY()+ac.getHeight());
				}
			}
			
			
		}
		
		for (String activityName : activityNames)  {
		    ActivityCoordinates ac = this.repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), activityName);
		    //InputStream is = rs.getResourceAsStream(pd.getDeploymentId(), imgName);
		    if (is != null) {
		    	//image = ImageIO.read(is);
			    //Graphics g = image.getGraphics();
			    g.setColor(new Color(255, 0, 0));
			    g.drawRect(ac.getX()+3, ac.getY(), ac.getWidth(), ac.getHeight());
			    
			   /** for (int i = 0; i < names.size(); i++) {
					String name = (String) names.get(i);
					if (name.equals(activityName)) {
						 g.setColor(new Color(0, 0, 0));
						 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
						 g.drawString("会签节点",ac.getX(), ac.getY());
					}
				}***/
				g.dispose();
			}
		}
		return image;
	}
	
	
	
	@Override
	public BufferedImage getPicV3(String processInstanceId, String imgName, List names, Map<String,String> map, String jbpmId) throws IOException{
		
		//RepositoryService rs = processEngine.getRepositoryService();
		//ExecutionService es = processEngine.getExecutionService();
		ProcessInstance pi = this.executionService.findProcessInstanceById(processInstanceId);
		Set<String> activityNames = pi.findActiveActivityNames();
		BufferedImage image = null;
		InputStream is = this.repositoryService.getResourceAsStream(jbpmId, imgName);
		image = ImageIO.read(is);
	    Graphics g = image.getGraphics();
	    List<ActivityImpl> allnames = (List<ActivityImpl>) this.getAllTask(jbpmId);
	    
		for (ActivityImpl a : allnames)  {
			String s = pi.getProcessDefinitionId();
			ActivityCoordinates ac = this.repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), a.getName());
		    
			String key = "acd"+ac.getX()+ac.getY()+ac.getWidth()+ac.getHeight();
			String persons = map.get(key);
			
			 g.setColor(new Color(0, 0, 0));
			 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
			 g.drawString(persons,ac.getX(), ac.getY());
			
			for (int i = 0; i < names.size(); i++) {
				String name = (String) names.get(i);
				if (name.equals(a.getName())) {
					 g.setColor(new Color(102, 0, 153));
					 g.setFont(new Font("宋体", Font.BOLD, 12));
					 g.drawString("会签",ac.getX(), ac.getY()+ac.getHeight());
				}
			}
			
		}
		
		for (String activityName : activityNames)  {
		    ActivityCoordinates ac = this.repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), activityName);
		    //InputStream is = rs.getResourceAsStream(pd.getDeploymentId(), imgName);
		    if (is != null) {
		    	//image = ImageIO.read(is);
			    //Graphics g = image.getGraphics();
			    g.setColor(new Color(255, 0, 0));
			    g.drawRect(ac.getX()+3, ac.getY(), ac.getWidth(), ac.getHeight());
			    
			   /** for (int i = 0; i < names.size(); i++) {
					String name = (String) names.get(i);
					if (name.equals(activityName)) {
						 g.setColor(new Color(0, 0, 0));
						 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
						 g.drawString("会签节点",ac.getX(), ac.getY());
					}
				}***/
				g.dispose();
			}
		}
		return image;
	}
	
	
	
	@Override
	public BufferedImage getProcessPic(String processid, String imgName,List names, Map<String,String> map) throws IOException{
	
		//RepositoryService rs = processEngine.getRepositoryService();
		//ExecutionService es = processEngine.getExecutionService();
		ProcessDefinition pd = this.repositoryService.createProcessDefinitionQuery().deploymentId(processid).uniqueResult();
		BufferedImage image = null;
		String did = pd.getDeploymentId();
		InputStream is = repositoryService.getResourceAsStream(pd.getDeploymentId(), imgName);
		image = ImageIO.read(is);
		
		Graphics g = image.getGraphics();
	    List<ActivityImpl> allnames = (List<ActivityImpl>) this.getAllTask(pd);
	    
	    	for (ActivityImpl a : allnames)  {
				ActivityCoordinates ac = repositoryService.getActivityCoordinates(pd.getId(), a.getName());
				String key = "acd"+ac.getX()+ac.getY()+ac.getWidth()+ac.getHeight();
				String persons = map.get(key);
				
				 g.setColor(new Color(0, 0, 0));
				 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
				 if (persons!=null && persons.length()>0) {
					 g.drawString(persons,ac.getX(), ac.getY());
				}
				for (int i = 0; i < names.size(); i++) {
					String name = (String) names.get(i);
					if (name.equals(a.getName())) {
						 g.setColor(new Color(102, 0, 153));
						 g.setFont(new Font("宋体", Font.BOLD, 12));
						 g.drawString("会签",ac.getX(), ac.getY()+ac.getHeight());
					}
				}
				
				
			}
		
		return image;
}
	
	
	
	
	@Override
	public BufferedImage getProcessPicV1(String processid, String imgName, List names) throws IOException{
	
		//RepositoryService rs = processEngine.getRepositoryService();
		//ExecutionService es = processEngine.getExecutionService();
		ProcessDefinition pd = this.repositoryService.createProcessDefinitionQuery().deploymentId(processid).uniqueResult();
		BufferedImage image = null;
//		System.out.println(pd.getImageResourceName());
		InputStream is = repositoryService.getResourceAsStream(pd.getDeploymentId(), imgName);
		image = ImageIO.read(is);
		Graphics g = image.getGraphics();
		List<ActivityImpl> allnames = (List<ActivityImpl>) this.getAllTask(pd.getKey());
		for (ActivityImpl a : allnames)  {
			ActivityCoordinates ac = repositoryService.getActivityCoordinates(pd.getId(), a.getName());
		    
			for (int i = 0; i < names.size(); i++) {
				String name = (String) names.get(i);
				if (name.equals(a.getName())) {
					 g.setColor(new Color(0, 0, 0));
					 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
					 g.drawString("会签节点",ac.getX(), ac.getY());
				}
			}
		}
		g.dispose();
		
		
		
		return image;
}
	
	
	@Override
	public BufferedImage getProcessPicV2(String processid, String imgName, List names, Map<String,String> map) throws IOException{
	
		//RepositoryService rs = processEngine.getRepositoryService();
		//ExecutionService es = processEngine.getExecutionService();
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(processid).uniqueResult();
		BufferedImage image = null;
		InputStream is = repositoryService.getResourceAsStream(pd.getDeploymentId(), imgName);
		image = ImageIO.read(is);
		Graphics g = image.getGraphics();
		List<ActivityImpl> allnames = (List<ActivityImpl>) this.getAllTask(pd.getDeploymentId());
		for (ActivityImpl a : allnames)  {
			ActivityCoordinates ac = repositoryService.getActivityCoordinates(pd.getId(), a.getName());
		    String key = "acd"+ac.getX()+ac.getY()+ac.getWidth()+ac.getHeight();
			String persons = map.get(key);
			
			 g.setColor(new Color(0, 0, 0));
			 g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 12));
			 if (persons!=null && persons.length()>0) {
				 g.drawString(persons,ac.getX(), ac.getY());
			 }
			for (int i = 0; i < names.size(); i++) {
				String name = (String) names.get(i);
				if (name.equals(a.getName())) {
					 g.setColor(new Color(102, 0, 153));
					 g.setFont(new Font("宋体", Font.BOLD, 12));
					 g.drawString("会签",ac.getX(), ac.getY()+ac.getHeight());
				}
			}
		}
		g.dispose();
		
		
		
		return image;
}
	
	
	@Override
	 public List<? extends Activity> getAllTask(String processId){

		 //RepositoryService repositoryService = processEngine.getRepositoryService();
		 
		 ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(processId).uniqueResult();

		 ProcessDefinitionImpl definitionimpl = (ProcessDefinitionImpl)definition;
		 
		 List datas = new ArrayList();

		 List<? extends Activity> list = definitionimpl.getActivities();
		 
		 for (Activity activity : list) {
			 
			 //ActivityCoordinates ac = repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), activityName);

			 if(activity.getType().equals("task")){
				 
				 datas.add(activity);
				 
			 }
		 }
		 
		 return datas;
		 
	 }
	
	@Override
	 public List<? extends Activity> getAllTask(ProcessDefinition definition){

		 //RepositoryService repositoryService = processEngine.getRepositoryService();

		 ProcessDefinitionImpl definitionimpl = (ProcessDefinitionImpl)definition;
		 
		 List datas = new ArrayList();

		 List<? extends Activity> list = definitionimpl.getActivities();
		 
		 for (Activity activity : list) {
			 
			 //ActivityCoordinates ac = repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), activityName);

			 if(activity.getType().equals("task")){
				 
				 datas.add(activity);
				 
			 }
		 }
		 
		 return datas;
		 
	 }
	

	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}


	@Override
	public Task findTaskById(String id) {
		Task task = this.processEngine.getTaskService().getTask(id);
		return task;
	}
	

}

