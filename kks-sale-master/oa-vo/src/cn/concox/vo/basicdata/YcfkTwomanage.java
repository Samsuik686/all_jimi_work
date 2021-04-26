package cn.concox.vo.basicdata;

import java.sql.Timestamp;
import java.util.List;

/**
 * <pre>
 * 实体类
 * 数据库表名称：t_sale_ycfkmanage_manage 异常反馈表
 * </pre>
 */
public class YcfkTwomanage {
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
	 * 字段名称：保内/保外
	 * 
	 * 数据库字段信息:warranty INT(10)
	 */
	private Integer warranty;

	/**
	 * 字段名称：问题描述
	 * 
	 * 数据库字段信息:description VARCHAR(255)
	 */
	private String description;

	/**
	 * 字段名称：配件数量
	 * 
	 * 数据库字段信息:number INT(10)
	 */
	private Integer number;

	/**
	 * 字段名称：反馈人
	 * 
	 * 数据库字段信息:feedBackPerson VARCHAR(40)
	 */
	private String feedBackPerson;

	/**
	 * 字段名称：反馈日期
	 * 
	 * 数据库字段信息:feedBackDate DATE(10)
	 */
	private Timestamp feedBackDate;

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

	/**
	 * 字段名称：状态0:待解决，1:已完成，
	 * 
	 * 数据库字段信息:completionState INT(10)
	 */
	private Integer completionState;

	private Integer ycfk_completionState;// 异常反馈查询管理里的状态

	private String ycfk_currentSite;// 异常反馈查询管理工站

	// 客服站 状态 数据库字段信息:kf_status INT(10) 字段名称：状态0:待解决，1:已完成，-1已发送
	private Integer customerStatus;

	// 终端站 状态 数据库字段信息:zd_status INT(10)
	private Integer termailStatus;

	// 平台站 状态 数据库字段信息:pt_status INT(10)
	private Integer platformStatus;

	// 品质站 状态 数据库字段信息:pz_status INT(10)
	private Integer qualityStatus;

	// 产品站 状态 数据库字段信息:cp_status INT(10)
	private Integer productStatus;

	// 维修站 状态 数据库字段信息:wx_status INT(10)
	private Integer repairStatus;

	// 项目站 状态 数据库字段信息:xm_status INT(10)
	private Integer projectStatus;

	// 几米站 状态 数据库字段信息:jimi_status INT(10)
	private Integer jimiStatus;

	// 市场站 状态 数据库字段信息:market_status INT(10)
	private Integer marketStatus;
	
	// 研发站 状态 数据库字段信息:research_status INT(10)
	private Integer researchStatus;

	// 测试站 状态 数据库字段信息:test_status INT(10)
	private Integer testStatus;

	// 物联网卡站 状态 数据库字段信息:iot_status INT(10)
	private Integer iotStatus;
	
	// SIM卡平台站 状态 数据库字段信息:sim_status INT(10)
	private Integer simStatus;

	//问题严重程度
	private Integer severityFlag;

	//原因分析
	private String analysis;

	//库存品处理策略
	private String strategy;

	/**
	 * 字段名称：责任人
	 * 
	 * 数据库字段信息:recipient VARCHAR(40)
	 */
	private String recipient;

	// 终端站 处理人 数据库字段信息:zd_person varchar(30)
	private String termailPerson;

	// 平台站 处理人 数据库字段信息:zd_person varchar(30)
	private String platformPerson;

	// 品质站 处理人 数据库字段信息:zd_person varchar(30)
	private String qualityPerson;

	// 产品站 处理人 数据库字段信息:zd_person varchar(30)
	private String productPerson;

	// 维修站 处理人 数据库字段信息:wx_person varchar(30)
	private String repairPerson;

	// 项目站 处理人 数据库字段信息:xm_person varchar(30)
	private String projectPerson;

	// 几米站 处理人 数据库字段信息:jimi_person varchar(30)
	private String jimiPerson;

	// 市场站 处理人 数据库字段信息:market_person varchar(30)
	private String marketPerson;
	
	// 客服站 处理人 数据库字段信息:kf_person varchar(30)
	private String customerPerson;
	
	// 研发站 处理 数据库字段信息:research_person varchar(30)
	private String researchPerson;

	// 测试站 处理 数据库字段信息:test_person varchar(30)
	private String testPerson;

	// 物联网卡站 处理 数据库字段信息:iot_person varchar(30)
	private String iotPerson;
	
	// SIM卡平台站 处理 数据库字段信息:sim_person varchar(30)
	private String simPerson;


	/**
	 * 字段名称：当前跟进人
	 * 
	 * 数据库字段信息:followupPeople VARCHAR(40)
	 */
	private String followupPeople;

	// 设备当前所在工站 current_site VARCHAR(40)
	private String currentSite;

	// zd_createTime '设备发送到终端的时间'
	private Timestamp termailCreateTime;

	// pt_createTime '设备发送到平台的时间'
	private Timestamp platformCreateTime;

	// pz_createTime '设备发送到品质的时间'
	private Timestamp qualityCreateTime;

	// cp_createTime '设备发送到产品的时间'
	private Timestamp productCreateTime;

	// wx_createTime '设备发送到维修的时间'
	private Timestamp repairCreateTime;

	// m_createTime '设备发送到项目的时间'
	private Timestamp projectCreateTime;

	// jimi_createTime '设备发送到几米的时间'
	private Timestamp jimiCreateTime;

	// market_createTime '设备发送到市场的时间'
	private Timestamp marketCreateTime;
	
	// kf_createTime '设备发送到客服的时间'
	private Timestamp customerCreateTime;
	
	// research_createTime '设备发送到研发的时间'
	private Timestamp researchCreateTime;

	// test_createTime '设备发送到测试的时间'
	private Timestamp testCreateTime;
	
	// iot_createTime '设备发送到物联网卡的时间'
	private Timestamp iotCreateTime;
	
	// sim_createTime '设备发送到SIM卡平台的时间'
	private Timestamp simCreateTime;
	
	
	
	private Integer levelFlag;//紧急度
	
	private Integer checkResult;//验收结果 0：待验收；1：NG；2：OK
	
	private String checker;//验收人

	/*
	 * =================================业务字段
	 * start===============================
	 */
	private String startTime;// 开始时间

	private String endTime;// 结束时间

	private String treeDate;

	private String nextSite; // 下一个工站

	private List<YcfkManageFile> ycfkFileList; // 对应的附件信息

	private String currLoginer;// 当前登录人（判断是否是管理员，查询跟进人时显示数据）
	
	private Double timeoutDate;// 超时天数
	
	private Integer sortId;//序号

	private List<String> follows;//跟进人员，仅查询用

	//抄送人，仅查询
	private List<String> copyPersonArray;

	//抄送人，逗号分隔
	private String copyPerson;

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

	public Integer getWarranty() {
		return this.warranty;
	}

	public void setWarranty(Integer warranty) {
		this.warranty = warranty;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getFeedBackPerson() {
		return feedBackPerson;
	}

	public void setFeedBackPerson(String feedBackPerson) {
		this.feedBackPerson = feedBackPerson;
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

	public String getFollowupPeople() {
		return this.followupPeople;
	}

	public void setFollowupPeople(String followupPeople) {
		this.followupPeople = followupPeople;
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

	public String getTreeDate() {
		return treeDate;
	}

	public void setTreeDate(String treeDate) {
		this.treeDate = treeDate;
	}

	public String getCurrentSite() {
		return currentSite;
	}

	public void setCurrentSite(String currentSite) {
		this.currentSite = currentSite;
	}

	public String getNextSite() {
		return nextSite;
	}

	public void setNextSite(String nextSite) {
		this.nextSite = nextSite;
	}

	public Integer getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(Integer customerStatus) {
		this.customerStatus = customerStatus;
	}

	public Integer getTermailStatus() {
		return termailStatus;
	}

	public void setTermailStatus(Integer termailStatus) {
		this.termailStatus = termailStatus;
	}

	public Integer getPlatformStatus() {
		return platformStatus;
	}

	public void setPlatformStatus(Integer platformStatus) {
		this.platformStatus = platformStatus;
	}

	public Integer getQualityStatus() {
		return qualityStatus;
	}

	public void setQualityStatus(Integer qualityStatus) {
		this.qualityStatus = qualityStatus;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
	}

	public Integer getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Integer projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getTermailPerson() {
		return termailPerson;
	}

	public void setTermailPerson(String termailPerson) {
		this.termailPerson = termailPerson;
	}

	public String getPlatformPerson() {
		return platformPerson;
	}

	public void setPlatformPerson(String platformPerson) {
		this.platformPerson = platformPerson;
	}

	public String getQualityPerson() {
		return qualityPerson;
	}

	public void setQualityPerson(String qualityPerson) {
		this.qualityPerson = qualityPerson;
	}

	public String getProductPerson() {
		return productPerson;
	}

	public void setProductPerson(String productPerson) {
		this.productPerson = productPerson;
	}

	public String getRepairPerson() {
		return repairPerson;
	}

	public void setRepairPerson(String repairPerson) {
		this.repairPerson = repairPerson;
	}

	public String getProjectPerson() {
		return projectPerson;
	}

	public void setProjectPerson(String projectPerson) {
		this.projectPerson = projectPerson;
	}

	public Timestamp getTermailCreateTime() {
		return termailCreateTime;
	}

	public void setTermailCreateTime(Timestamp termailCreateTime) {
		this.termailCreateTime = termailCreateTime;
	}

	public Timestamp getPlatformCreateTime() {
		return platformCreateTime;
	}

	public void setPlatformCreateTime(Timestamp platformCreateTime) {
		this.platformCreateTime = platformCreateTime;
	}

	public Timestamp getQualityCreateTime() {
		return qualityCreateTime;
	}

	public void setQualityCreateTime(Timestamp qualityCreateTime) {
		this.qualityCreateTime = qualityCreateTime;
	}

	public Timestamp getProductCreateTime() {
		return productCreateTime;
	}

	public void setProductCreateTime(Timestamp productCreateTime) {
		this.productCreateTime = productCreateTime;
	}

	public Timestamp getRepairCreateTime() {
		return repairCreateTime;
	}

	public void setRepairCreateTime(Timestamp repairCreateTime) {
		this.repairCreateTime = repairCreateTime;
	}

	public Timestamp getProjectCreateTime() {
		return projectCreateTime;
	}

	public void setProjectCreateTime(Timestamp projectCreateTime) {
		this.projectCreateTime = projectCreateTime;
	}

	public Integer getJimiStatus() {
		return jimiStatus;
	}

	public void setJimiStatus(Integer jimiStatus) {
		this.jimiStatus = jimiStatus;
	}

	public String getJimiPerson() {
		return jimiPerson;
	}

	public void setJimiPerson(String jimiPerson) {
		this.jimiPerson = jimiPerson;
	}

	public Timestamp getJimiCreateTime() {
		return jimiCreateTime;
	}

	public void setJimiCreateTime(Timestamp jimiCreateTime) {
		this.jimiCreateTime = jimiCreateTime;
	}

	public List<YcfkManageFile> getYcfkFileList() {
		return ycfkFileList;
	}

	public void setYcfkFileList(List<YcfkManageFile> ycfkFileList) {
		this.ycfkFileList = ycfkFileList;
	}

	public String getCurrLoginer() {
		return currLoginer;
	}

	public void setCurrLoginer(String currLoginer) {
		this.currLoginer = currLoginer;
	}

	public Integer getYcfk_completionState() {
		return ycfk_completionState;
	}

	public void setYcfk_completionState(Integer ycfk_completionState) {
		this.ycfk_completionState = ycfk_completionState;
	}

	public String getYcfk_currentSite() {
		return ycfk_currentSite;
	}

	public void setYcfk_currentSite(String ycfk_currentSite) {
		this.ycfk_currentSite = ycfk_currentSite;
	}

	public Integer getMarketStatus() {
		return marketStatus;
	}

	public void setMarketStatus(Integer marketStatus) {
		this.marketStatus = marketStatus;
	}

	public String getMarketPerson() {
		return marketPerson;
	}

	public void setMarketPerson(String marketPerson) {
		this.marketPerson = marketPerson;
	}

	public Timestamp getMarketCreateTime() {
		return marketCreateTime;
	}

	public void setMarketCreateTime(Timestamp marketCreateTime) {
		this.marketCreateTime = marketCreateTime;
	}

	public Integer getLevelFlag() {
		return levelFlag;
	}

	public void setLevelFlag(Integer levelFlag) {
		this.levelFlag = levelFlag;
	}

	public String getCustomerPerson() {
		return customerPerson;
	}

	public void setCustomerPerson(String customerPerson) {
		this.customerPerson = customerPerson;
	}

	public Timestamp getCustomerCreateTime() {
		return customerCreateTime;
	}

	public void setCustomerCreateTime(Timestamp customerCreateTime) {
		this.customerCreateTime = customerCreateTime;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Double getTimeoutDate() {
		return timeoutDate;
	}

	public void setTimeoutDate(Double timeoutDate) {
		this.timeoutDate = timeoutDate;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Integer getResearchStatus() {
		return researchStatus;
	}

	public void setResearchStatus(Integer researchStatus) {
		this.researchStatus = researchStatus;
	}

	public Integer getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Integer testStatus) {
		this.testStatus = testStatus;
	}

	public Integer getIotStatus() {
		return iotStatus;
	}

	public void setIotStatus(Integer iotStatus) {
		this.iotStatus = iotStatus;
	}

	public String getResearchPerson() {
		return researchPerson;
	}

	public void setResearchPerson(String researchPerson) {
		this.researchPerson = researchPerson;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
	}

	public String getIotPerson() {
		return iotPerson;
	}

	public void setIotPerson(String iotPerson) {
		this.iotPerson = iotPerson;
	}

	public Timestamp getResearchCreateTime() {
		return researchCreateTime;
	}

	public void setResearchCreateTime(Timestamp researchCreateTime) {
		this.researchCreateTime = researchCreateTime;
	}

	public Timestamp getTestCreateTime() {
		return testCreateTime;
	}

	public void setTestCreateTime(Timestamp testCreateTime) {
		this.testCreateTime = testCreateTime;
	}

	public Timestamp getIotCreateTime() {
		return iotCreateTime;
	}

	public void setIotCreateTime(Timestamp iotCreateTime) {
		this.iotCreateTime = iotCreateTime;
	}

	public Integer getSimStatus() {
		return simStatus;
	}

	public void setSimStatus(Integer simStatus) {
		this.simStatus = simStatus;
	}

	public String getSimPerson() {
		return simPerson;
	}

	public void setSimPerson(String simPerson) {
		this.simPerson = simPerson;
	}

	public Timestamp getSimCreateTime() {
		return simCreateTime;
	}

	public void setSimCreateTime(Timestamp simCreateTime) {
		this.simCreateTime = simCreateTime;
	}

	public List<String> getFollows() {
		return follows;
	}

	public void setFollows(List<String> follows) {
		this.follows = follows;
	}

	public Integer getSeverityFlag() {
		return severityFlag;
	}

	public void setSeverityFlag(Integer severityFlag) {
		this.severityFlag = severityFlag;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}


	public List<String> getCopyPersonArray() {
		return copyPersonArray;
	}

	public void setCopyPersonArray(List<String> copyPersonArray) {
		this.copyPersonArray = copyPersonArray;
	}

	public String getCopyPerson() {
		return copyPerson;
	}

	public void setCopyPerson(String copyPerson) {
		this.copyPerson = copyPerson;
	}
}
