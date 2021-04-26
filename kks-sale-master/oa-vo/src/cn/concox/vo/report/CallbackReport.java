/*
 * Created: 2016-08-19 14:23:49
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
package cn.concox.vo.report;

import java.sql.Timestamp;

public class CallbackReport {

	/** ----------------- 业务字段 ---------------------------- Start **/
	private String repairnNmber; // 批次号

	private String cusName;// 客户名

	private Timestamp acceptanceTime;// 受理时间

	private Timestamp sendTime;// 发货时间
	
	private Timestamp evaluateTime;// 评价时间

	private String skillEvaluate;// 技能评价

	private String serviceEvaluate;// 服务评价
	
	private String fremark;//评价说明

	private String startTime; // 开始时间

	private String endTime; // 结束时间

	/** ----------------- 业务字段 ---------------------------- End **/

	public CallbackReport() {
	}

	public String getRepairnNmber() {
		return repairnNmber;
	}

	public void setRepairnNmber(String repairnNmber) {
		this.repairnNmber = repairnNmber;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Timestamp getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(Timestamp acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getSkillEvaluate() {
		return skillEvaluate;
	}

	public void setSkillEvaluate(String skillEvaluate) {
		this.skillEvaluate = skillEvaluate;
	}

	public String getServiceEvaluate() {
		return serviceEvaluate;
	}

	public void setServiceEvaluate(String serviceEvaluate) {
		this.serviceEvaluate = serviceEvaluate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Timestamp getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(Timestamp evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public String getFremark() {
		return fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	
}