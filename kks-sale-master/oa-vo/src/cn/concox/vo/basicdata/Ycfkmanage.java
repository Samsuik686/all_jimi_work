package cn.concox.vo.basicdata;

import java.sql.Timestamp;

/**
 * <pre>
 * 实体类
 * 数据库表名称：t_sale_ycfkmanage 400电话记录表
 * </pre>
 * 
 * @author Liao.bifeng
 * @version v1.0
 */
public class Ycfkmanage{
	/**
	 * 字段名称：id
	 *
	 * 
	 * 数据库字段信息:backId INT(11)
	 */
	private Integer backId;
	/**
	 * 字段名称：销售机型
	 * 
	 * 数据库字段信息:model VARCHAR(40)
	 */
	private String model;

	/**
	 * 字段名称：IMEI
	 * 
	 * 数据库字段信息:imei VARCHAR(40)
	 */
	private String imei;

	/**
	 * 字段名称：问题描述
	 * 
	 * 数据库字段信息:description VARCHAR(255)
	 */
	private String description;

	/**
	 * 字段名称：反馈日期
	 * 
	 * 数据库字段信息:feedBackDate DATE(10)
	 */
	private Timestamp feedBackDate;

	/**
	 * 字段名称：责任人
	 * 
	 * 数据库字段信息:recipient VARCHAR(40)
	 */
	private String recipient;

	/**
	 * 字段名称：责任单位
	 * 
	 * 数据库字段信息:responsibilityUnit VARCHAR(100)
	 */
	private String responsibilityUnit;

	/**
	 * 字段名称：处理措施
	 * 
	 * 数据库字段信息:measures VARCHAR(255)
	 */
	private String measures;
	

	/**
	 * 字段名称：状态0:待解决，1:已完成
	 * 
	 * 数据库字段信息:completionState INT(10)
	 */
	private Integer completionState;

	/**
	 * 字段名称：完成时间
	 * 
	 * 数据库字段信息:completionDate DATE(10)
	 */
	private Timestamp completionDate;

	/**
	 * 字段名称：客户姓名
	 * 
	 * 数据库字段信息:customerName VARCHAR(40)
	 */
	private String customerName;

	/**
	 * 字段名称：联系电话
	 * 
	 * 数据库字段信息:phone VARCHAR(32)
	 */
	private String phone;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remak VARCHAR(32)
	 */

	private String remak;

	/*
	 * =================================业务字段
	 * start===============================
	 */
	private Integer yid;//类别id
	private String hideMethod;//待解决时保存本次选择问题的处理措施
	
	private String startTime;// 开始时间

	private String endTime;// 结束时间
	
	private String treeDate;
	
	private Integer ynumber;//报表统计数量
	private String percent;//报表统计百分比

	/* =================================业务字段 end=============================== */

	public Integer getBackId() {
		return backId;
	}

	public void setBackId(Integer backId) {
		this.backId = backId;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getResponsibilityUnit() {
		return this.responsibilityUnit;
	}

	public void setResponsibilityUnit(String responsibilityUnit) {
		this.responsibilityUnit = responsibilityUnit;
	}

	public String getMeasures() {
		return this.measures;
	}

	public void setMeasures(String measures) {
		this.measures = measures;
	}

	public Timestamp getFeedBackDate() {
		return feedBackDate;
	}

	public void setFeedBackDate(Timestamp feedBackDate) {
		this.feedBackDate = feedBackDate;
	}

	public Integer getCompletionState() {
		return completionState;
	}

	public void setCompletionState(Integer completionState) {
		this.completionState = completionState;
	}

	public Timestamp getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemak() {
		return this.remak;
	}

	public void setRemak(String remak) {
		this.remak = remak;
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

	public Integer getYid() {
		return yid;
	}

	public void setYid(Integer yid) {
		this.yid = yid;
	}

	public Integer getYnumber() {
		return ynumber;
	}

	public void setYnumber(Integer ynumber) {
		this.ynumber = ynumber;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getHideMethod() {
		return hideMethod;
	}

	public void setHideMethod(String hideMethod) {
		this.hideMethod = hideMethod;
	}

	public String getTreeDate() {
		return treeDate;
	}

	public void setTreeDate(String treeDate) {
		this.treeDate = treeDate;
	}
	
}
