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
package cn.concox.comm.jbpm.face;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.model.Activity;
import org.jbpm.api.task.Task;



/***
 * 
 * @author yang
 *
 */
public interface JBPMFaceCade {
	
	
	
	/***
	 * 审批通过逻辑
	 * @param taskId
	 */
	public boolean completeTask(String taskId, String pid, String name);
	
	
	
	public List completeTaskV1(String taskId, String pid, String name, Integer qh, Integer a, Integer b);
	
	/****
	 * 驳回逻辑
	 * @param taskId
	 * @param pid
	 */
	public void backTask(String taskId, String pid, String nodename);
	
	
	public void backTaskV1(String taskId, String pid, String nodename, String name);
	
	
	public BufferedImage getPicV3(String processInstanceId, String imgName, List names, Map<String,String> map, String jbpmId)throws IOException;
	
	
	/****
	 * 启动实例
	 * @param processId
	 * @param map
	 * @return
	 */
	public String runProcess(String processId, Map map);
	
	
	/***
	 * 发布流程
	 * @param zis
	 * @return
	 */
	public List upLoadProcess(ZipInputStream zis);
	
	
	/***
	 * 删除流程
	 * @param processId
	 */
	public void delProcess(String processId);
	
	
	/****
	 * 根据UID查找所有的节点
	 * @param name
	 * @return
	 */
	public List<Task> findAllTask(String name);
	
	
	public Task findTaskById(String id);
	
	/***
	 * 转发
	 * 支持单个节点转发
	 * @param taskId
	 */
	public void replaceTask(String taskId, String uid);
	
	
	/***
	 * 实例监控图
	 * @param processInstanceId
	 * @param imgName
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getPic(String processInstanceId, String imgName) throws IOException;
	
	
	
	public BufferedImage getPicV1(String processInstanceId, String imgName, List names) throws IOException;
	
	
	
	public BufferedImage getPicV2(String processInstanceId, String imgName, List names, Map<String,String> map) throws IOException;
	
	/***
	 * 流程定义监控图
	 * @param processid
	 * @param imgName
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getProcessPic(String processid, String imgName, List names, Map<String,String> map) throws IOException;
	
	
	
	public List<? extends Activity> getAllTask(ProcessDefinition definition);
	
	/***
	 * 流程定义监控图
	 * @param processid
	 * @param imgName
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getProcessPicV1(String processid, String imgName, List names) throws IOException;
	
	
	
	
	/***
	 * 流程定义监控图
	 * @param processid
	 * @param imgName
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getProcessPicV2(String processid, String imgName, List names, Map<String,String> persons) throws IOException;
	
	
	/***
	 * 拿静态流程定义所有的节点
	 * @param processId
	 * @return
	 */
	public List<? extends Activity> getAllTask(String processId);
	
	

}
