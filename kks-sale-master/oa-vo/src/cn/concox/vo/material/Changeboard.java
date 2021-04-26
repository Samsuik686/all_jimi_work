package cn.concox.vo.material;

import java.sql.Timestamp;

/**
 * <pre>
 * 物料申请表实体类
 *  数据库表名称：t_sale_changeboard
 * </pre>
 */
public class Changeboard {
	/**
	 * 字段名称：id
	 * 
	 * 数据库字段信息:id int(11),主键，自增长
	 */
	private Integer id;

	/**
	 * 字段名称：客户名称
	 * 
	 * 数据库字段信息:cusName VARCHAR(255)
	 */
	private String cusName;

	/**
	 * 字段名称：IMEI
	 * 
	 * 数据库字段信息:imei VARCHAR(50)
	 */
	private String imei;

	/**
	 * 字段名称：主板型号
	 * 
	 * 数据库字段信息:model VARCHAR(255)
	 */
	private String model;

	/**
	 * 字段名称：申请数量
	 * 
	 * 数据库字段信息:number INT(10)
	 */
	private Integer number;

	/**
	 * 字段名称：申请人
	 * 
	 * 数据库字段信息:applicater VARCHAR(255)
	 */
	private String applicater;

	/**
	 * 字段名称：申请日期
	 * 
	 * 数据库字段信息:appTime DATETIME(19)
	 */
	private Timestamp appTime;
	
	/**
	 * 字段名称：维修|测试
	 * 
	 * 数据库字段信息:repairOrTest VARCHAR(255)
	 */
	private String repairOrTest;
	
	/**
	 * 字段名称：换板原因
	 * 
	 * 数据库字段信息:purpose VARCHAR(255)
	 */
	private String purpose;

	/**
	 * 字段名称：批复（0：同意 1：不同意）
	 * 
	 * 数据库字段信息:state INT(10)
	 */
	private Integer state;

	/**
	 * 字段名称：主管
	 * 
	 * 数据库字段信息:approver VARCHAR(255)
	 */
	private String charger;
	
	/**
	 * 字段名称：主管批复日期
	 * 
	 * 数据库字段信息:updateTime DATETIME(19)
	 */
	private Timestamp chargerUpdateTime;
	
	/**
	 * 字段名称：经理
	 * 
	 * 数据库字段信息:approver VARCHAR(255)
	 */
	private String manager;
	
	/**
	 * 字段名称：经理批复日期
	 * 
	 * 数据库字段信息:updateTime DATETIME(19)
	 */
	private Timestamp managerUpdateTime;
	
	/**
	 * 字段名称：客服文员
	 * 
	 * 数据库字段信息:serviceCharger VARCHAR(255)
	 */
	private String serviceCharger;
	
	/**
	 * 字段名称：完成日期
	 * 
	 * 数据库字段信息:serviceUpdateTime DATETIME(19)
	 */
	private Timestamp serviceUpdateTime;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;
	
	private Timestamp testBackTime;//测试返还日期
	
	private Integer isWarranty;//保内保外
	
	private Integer wfId;

	private String startTime;// 开始时间

	private String endTime;// 结束时间
	
	private Integer sendFlag;//发送标识
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getApplicater() {
		return applicater;
	}

	public void setApplicater(String applicater) {
		this.applicater = applicater;
	}

	public Timestamp getAppTime() {
		return appTime;
	}

	public void setAppTime(Timestamp appTime) {
		this.appTime = appTime;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCharger() {
		return charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	public Timestamp getChargerUpdateTime() {
		return chargerUpdateTime;
	}

	public void setChargerUpdateTime(Timestamp chargerUpdateTime) {
		this.chargerUpdateTime = chargerUpdateTime;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Timestamp getManagerUpdateTime() {
		return managerUpdateTime;
	}

	public void setManagerUpdateTime(Timestamp managerUpdateTime) {
		this.managerUpdateTime = managerUpdateTime;
	}

	public String getServiceCharger() {
		return serviceCharger;
	}

	public void setServiceCharger(String serviceCharger) {
		this.serviceCharger = serviceCharger;
	}

	public Timestamp getServiceUpdateTime() {
		return serviceUpdateTime;
	}

	public void setServiceUpdateTime(Timestamp serviceUpdateTime) {
		this.serviceUpdateTime = serviceUpdateTime;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getIsWarranty() {
		return isWarranty;
	}

	public void setIsWarranty(Integer isWarranty) {
		this.isWarranty = isWarranty;
	}

	public String getRepairOrTest() {
		return repairOrTest;
	}

	public void setRepairOrTest(String repairOrTest) {
		this.repairOrTest = repairOrTest;
	}

	public Integer getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	public Timestamp getTestBackTime() {
		return testBackTime;
	}

	public void setTestBackTime(Timestamp testBackTime) {
		this.testBackTime = testBackTime;
	}

	public Integer getWfId() {
		return wfId;
	}

	public void setWfId(Integer wfId) {
		this.wfId = wfId;
	}

}