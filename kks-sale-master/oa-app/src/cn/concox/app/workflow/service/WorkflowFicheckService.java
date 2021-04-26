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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.basicdata.mapper.SjfjmanageMapper;
import cn.concox.app.basicdata.service.TipService;
import cn.concox.app.basicdata.service.TipService.TipState;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.service.MaterialLogService;
import cn.concox.app.report.service.HwmReportService;
import cn.concox.app.workflow.mapper.WorkflowFicheckMapper;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.app.workflow.mapper.WorkflowPackMapper;
import cn.concox.app.workflow.mapper.WorkflowRepairMapper;
import cn.concox.comm.util.Convert;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Sjfjmanage;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowFicheck;
import cn.concox.vo.workflow.WorkflowPack;
import cn.concox.vo.workflow.WorkflowRepair;

/**
 * 终检  接口管理
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
@Service("ficheckService")
@Scope("prototype")
public class WorkflowFicheckService{

	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="workflowMapper")
	private  WorkflowMapper<Workflow> workflowMapper;
	
	@Resource(name="workflowFicheckMapper")
	private  WorkflowFicheckMapper<WorkflowFicheck> workflowFicheckMapper;
	
	@Resource(name = "workflowPackMapper")
	private WorkflowPackMapper<WorkflowPack> workflowPackMapper;
	
	@Resource(name="sjfjmanageMapper")
	private  SjfjmanageMapper<Sjfjmanage> sjfjmanageMapper;
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	@Resource(name = "workflowRepairMapper")
	private WorkflowRepairMapper<WorkflowRepair> workflowRepairMapper;
	
	@Autowired
	private HwmReportService hwmReportService;
	
	@Resource(name="workflowPackService")
	private WorkflowPackService workflowPackService;
	
	@Resource(name = "materialLogService")
	private MaterialLogService materialLogService;
	
	@Resource(name="tipService")
	private TipService tipService;
	
	/**
	 * 获取终检分页数据
	 * @param ficheck
	 * @return
	 */
	public Page<WorkflowFicheck> getFinCheckPage(WorkflowFicheck ficheck, Integer currentpage, Integer pageSize) { 
		Page<WorkflowFicheck> ficheckpageList = new Page<WorkflowFicheck>();
		ficheckpageList.setCurrentPage(currentpage);
		ficheckpageList.setSize(pageSize);
		ficheckpageList = workflowFicheckMapper.getFicheckPage(ficheckpageList, ficheck);
		return ficheckpageList;
	}
	
	/**
	 * 获取终检数据
	 * @param ficheck
	 * @return
	 */
	public List<WorkflowFicheck> getFinCheckList(WorkflowFicheck ficheck) { 		
		return workflowFicheckMapper.getFicheckList(ficheck);
	}
	
	public Long selGiveUpPriceCount(WorkflowFicheck ficheck){
		return workflowFicheckMapper.selGiveUpPriceCount(ficheck);
	}
	
	public WorkflowFicheck getInfo(int id) {
		return workflowFicheckMapper.getT(id);
	}
	
	/**
	 * 新增终检数据
	 * @param  WorkflowFicheck.Id  		主键ID
	 * @param  WorkflowFicheck.WfId     公共表主键ID
	 * @param  WorkflowFicheck.ispass   是否合格[1：合格,0：不合格]
	 * 
	 * @return True | False
	 * @date 2016-08-06 10:45:21
	 * 
	 */
	public boolean doInsert(WorkflowFicheck ficheck) {
		try {
			if(null != ficheck && null != ficheck.getWfId()){
				workflowFicheckMapper.insert(ficheck); 
				return true ;
			}else{
				return false ;
			}
		} catch (Exception e) {
			logger.error("新增终检信息失败！"+e); 
			return false ;
		}
	} 

	
	/**
	 * 退回至维修工作站
	 * 
	 * @param State    状态
	 * @param ids      主键ID
	 * @date  2016-08-15 18:44:26
	 */
	public boolean updateState(Integer State, String... ids){
		try {
			if(!StringUtil.isRealEmpty(ids.toString())){
				workflowFicheckMapper.updateState(State,ids); 
				return true  ;
			}else{
				return false ;
			}
		} catch (Exception e) {
			logger.error("修改终检信息失败！"+e); 
			return false ;
		}
	}

	/**
	 * 更新
	 * @param ficheck
	 * @return
	 */
	public void update(WorkflowFicheck ficheck) {
		//TODO UPDATE 公共表状态
		if(null !=ficheck && ficheck.getWfId()>0 && ficheck.getId()>0){
			if(ficheck.getIspass() ==0 ){      //TODO 不通过
				workflowMapper.updateState(6L, ficheck.getWfId().toString());          //TODO 已终检待维修
				ficheck.setState(2L); 
			}else{
				workflowMapper.updateState(7L, ficheck.getWfId().toString());          //TODO 已终检待装箱
				ficheck.setState(1L);
			}
			workflowFicheckMapper.update(ficheck);
		}
	}
	
	/**
	 * 内部维修发送终检
	 * @param ficheck
	 * @return
	 */
	public void updateRepairAgain(WorkflowFicheck ficheck) {
		//TODO UPDATE 公共表状态
		if(null !=ficheck && ficheck.getWfId()>0 && ficheck.getId()>0){
			workflowFicheckMapper.update(ficheck);
		}
	}

	public WorkflowFicheck getT(Integer Id) {
		if(Id>0){
			return workflowFicheckMapper.getT(Id);                                        
		}else{
			return null ;
		}
	}
	
	/**
	 * 根据主表ID查询终检表，查看数据是否存在
	 * @author TangYuping
	 * @version 2016年11月29日 下午2:24:39
	 * @param wfId
	 * @return
	 */
	public WorkflowFicheck getByWfid(Integer wfId){
		if(wfId>0){
			return workflowFicheckMapper.getByWfid(wfId);                                        
		}else{
			return null ;
		}
	}
	
	public boolean updateStateByWfid(Integer state, String... wfids){
		try {
			if(!StringUtil.isRealEmpty(wfids.toString())){
				workflowFicheckMapper.updateStateByWfid(state, wfids);
				return true  ;
			}else{
				return false ;
			}
		} catch (Exception e) {
			logger.error("修改终检信息失败！"+e); 
			return false ;
		}
	}
	
	/**
	 * 查询改批号的所有设备的装箱状态
	 * @param repairnNmber
	 * @return
	 */
	public List<Workflow> getStateByRepairnNmber(String repairnNmber, String imei, String state){
		List<Workflow> workflows= workflowMapper.getImeiState(repairnNmber, imei, state);
		if(null !=workflows && workflows.size()>0){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");						
			for (Workflow wf : workflows) {
				if(!StringUtil.isEmpty(wf.getW_sjfjDesc())){
					//TODO 随机附件
					String sjfj_Name = sjfjmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_sjfjDesc()));
					String sjfjIds = workflowService.sortIds(wf.getW_sjfjDesc());
					wf.setW_sjfj(sjfjIds);
					wf.setW_sjfjDesc(sjfj_Name);
				}
				if(wf.getAcceptanceTime() != null && StringUtils.isNotBlank(wf.getAcceptanceTime().toString())){
					Timestamp backTimeState = new Timestamp(hwmReportService.getInvalidTime(wf.getAcceptanceTime()).getTime());
					wf.setBackDate(df.format(backTimeState));
				}
				
				if(null != wf.getTestResult() && !"".equals(wf.getTestResult())){
					StringBuffer result = new StringBuffer();
					if(wf.getTestPerson() != null && wf.getTestTime() != null){
						result.append("———"+wf.getTestPerson()+"    "+wf.getTestTime());
						wf.setTestResult(wf.getTestResult()+result);
					}
				}
			}
		}
		return workflows;
	}
	
	/**
	 * @Title: getCountToRepairByWfids 
	 * @Description:终检工站查询选中的数据是否有不是已终检，待维修？有：不允许点击发送维修
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月9日 下午3:22:17
	 */
	public int getCountToRepairByWfids(String... ids){
		return workflowFicheckMapper.getCountToRepairByWfids(ids);
	}
	
	/**
	 * @Title: sendRepair 
	 * @Description:发送已终检，待维修数据返回维修
	 * @param ids 
	 * @author 20160308
	 * @date 2017年11月9日 下午5:05:51
	 */
	@Transactional
	public void sendRepair(String... ids) {
		this.updateStateByWfid(-1, ids);
		
		ArrayList<String> Rwids = new ArrayList<String>();    //TODO 公共表ID
		//TODO 修改维修状态 
		for (String id : ids) {
			if(!StringUtil.isRealEmpty(id)){
				WorkflowFicheck ficheck = this.getByWfid(Convert.getInteger(id));
				if(ficheck !=null && ficheck.getWfId()>0){
					Rwids.add(ficheck.getWfId().toString());
				}
			}
		}
	
		if(null!=Rwids && Rwids.size()>0){
			workflowRepairMapper.updateState(3L, StringUtil.getToArray(Rwids));
			workflowRepairMapper.updateRepairAgainCount(StringUtil.getToArray(Rwids));
			workflowService.updateState(6L, StringUtil.getToArray(Rwids));
		}
	}
	
	/**
	 * @Title: sendBackToRepair 
	 * @Description:待终检返回维修
	 * @param ids 
	 * @author 20160308
	 * @date 2017年11月28日 下午4:35:02
	 */
	@Transactional
	public void sendBackToRepair(String... ids) {
		if(!StringUtil.isEmpty(ids.toString())){
			//受理表数据改为已维修，待终检
			workflowMapper.updateState(5L,ids); 
			
			//维修表数据改为已维修，待终检
			workflowRepairMapper.updateState(2L, ids);
			
			//删除终检表数据
			workflowFicheckMapper.deleteFicheckByWfids(ids);
		}
	}
	
	/**
	 * @Title: getCountToPackByWfids 
	 * @Description:终检工站查询选中的数据是否有不是已终检，待装箱？有：不允许点击发送装箱
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月9日 下午3:22:21
	 */
	public int getCountToPackByWfids(String... ids){
		return workflowFicheckMapper.getCountToPackByWfids(ids);
	}

	/**
	 * @Title: sendPack 
	 * @Description:发送已终检，待装箱数据到装箱，同时物料出入库
	 * @param ids 
	 * @author 20160308
	 * @date 2017年11月9日 下午5:06:18
	 */
	@Transactional
	public void sendPack(String... ids) {
		try {
			this.updateStateByWfid(-1, ids);
			//TODO 新增装箱
			for (String wid : ids) {
				 if(!StringUtil.isRealEmpty(wid)){
					  WorkflowFicheck ficheck = this.getByWfid(Convert.getInteger(wid));
					  //TODO 获取送修批号
					  Workflow workflow = workflowService.getT(ficheck.getWfId());
					  workflowService.updateStateAndSendPackTime(7, ids);
					  
					  //TODO 判断装箱是否存在该批号
					  if(!workflowService.exists(workflow.getRepairnNmber())){
						  if(null !=ficheck && ficheck.getWfId()>0){
							  WorkflowPack pack = new WorkflowPack();
							  pack.setRepairnNmber(workflow.getRepairnNmber());
							  pack.setPackState(0L);
							  pack.setCreateTime(new Timestamp(System.currentTimeMillis()));
							  workflowPackService.doInsert(pack);
							  tipService.addTip(TipState.TIP_ZX);
						  }
					  }
				 }
			}
			ArrayList<String> Rwids = new ArrayList<String>();    //TODO 公共表ID
			for (String wid : ids) {
				WorkflowFicheck ficheck = this.getByWfid(Convert.getInteger(wid));
				 Rwids.add(ficheck.getWfId().toString());
			}
			if(null!=Rwids && Rwids.size()>0){
				//TODO 库存出库操作
				materialLogService.afterOutBound(StringUtil.getToArray(Rwids));
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * @Title: getCountToTestByWfids 
	 * @Description:终检工站查询选中的数据是否有不是待终检？有：不允许点击发送测试，待终检返回维修
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月10日 下午2:06:35
	 */
	public int getCountToTestByWfids(String... ids){
		return workflowFicheckMapper.getCountToTestByWfids(ids);
	}
}
