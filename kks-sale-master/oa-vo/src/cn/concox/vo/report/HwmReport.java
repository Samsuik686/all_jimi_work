package cn.concox.vo.report;

import java.sql.Timestamp;
import java.util.Date;

public class HwmReport {

	/** ----------------- 业务字段 ---------------------------- Start **/
	private Integer id;//超三天id
	
	private String cusName;// 客户名

	private String model; // 型号

	private Integer createType;//创建类型id

	private String createTypeName;//创建类型名

	private String imei;// imei号
	
	private String bill; // 订单号
	
	private String repairnNmber;//批次号

	private Timestamp acceptanceTime;// 受理时间

	private String backTime;// 返回时间

	private Double timeoutDate;// 超时天数

	private Integer timeoutDays;//超天天数

	private Integer workStation;

	private String stateDescribe;// 状态描述

	private String dutyOfficer;// 责任人

	private String timeoutRemark;// 超三天备注
	
	private String timeoutReason;//超三天原因

	private Integer timeoutReasonState;//超天原因状态，查询用（未填0，已填1）

	private Date timeoutBackTime;//应返还时间(Workflow中的backTime)，由于与当前类的backTime名字冲突，所以换了个名字。
	private String timeoutBackTimeStr;
	
	private Integer compare;//比较符号
	
	private String returnTimes;//返修次数
	
	private String isWarranty;// 是否保修

	private Timestamp packTime;// 装箱时间

	private Timestamp sendPackTime;//发送装箱时间

	private String state;// 进度

	private String cjgzDesc;// 初检故障
	
	private String zzgzType;// 故障类别
	
	private String zzgzDesc;// 最终故障

	private String dealMethod;// 处理方法

	private String solutionTwo; //处理措施2

	private String accepter;// 受理者

	private String packer;// 装箱者

	private String finChecker;// 收检人

	private String finDesc;// 收检描述

	private String engineer;// 维修人

	private String testResult;//测试结果

	private String testPerson;//测试人

	private String remark;// 受理备注
	private String repairRemark;// 修理备注
	private String finRemark;//终检备注

	private Date sendFicheckTime;//发送终检时间\
	private String sendFicheckTimeStr;

	private String onePriceRemark;//报价备注

	private Date sendPriceTime;//维修发送报价时间（报价工站数据创建时间）

	private String sendPriceTimeStr;

	private String offer;// 报价人

	private Long usage; // 用量

	private String ratio;// 比例

	private Integer repairTimeType;//查询时间类别，0表示按受理时间查询，1表示按取机时间查询(即装箱时间)，2表示发送装箱时间

	private String startTime;// 开始时间

	private String endTime;// 结束时间

	private String isPrice; //放弃报价
	
	private String freeRepair; //免费维修
	
	private String treeDate;//超三天日期树
	 
	private String timeoutState;//超三天完成状态
	
	private String repairNumberState;//超三天各状态
	
	private String repairDetailFlag;//查询维修总明细标记

	private Date payTime;

	private String payTimeStr; //payTime的字符串表示


	/** ----------------- 业务字段 ---------------------------- End **/

	
	
	public HwmReport() {
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
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

	public String getReturnTimes() {
		return returnTimes;
	}

	public void setReturnTimes(String returnTimes) {
		this.returnTimes = returnTimes;
	}
	
	public Timestamp getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(Timestamp acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public Double getTimeoutDate() {
		return timeoutDate;
	}

	public void setTimeoutDate(Double timeoutDate) {
		this.timeoutDate = timeoutDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateDescribe() {
		return stateDescribe;
	}

	public void setStateDescribe(String stateDescribe) {
		this.stateDescribe = stateDescribe;
	}

	public String getDutyOfficer() {
		return dutyOfficer;
	}

	public void setDutyOfficer(String dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public String getTimeoutRemark() {
		return timeoutRemark;
	}

	public void setTimeoutRemark(String timeoutRemark) {
		this.timeoutRemark = timeoutRemark;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public String getPacker() {
		return packer;
	}

	public void setPacker(String packer) {
		this.packer = packer;
	}

	public String getFinChecker() {
		return finChecker;
	}

	public void setFinChecker(String finChecker) {
		this.finChecker = finChecker;
	}

	public String getFinDesc() {
		return finDesc;
	}

	public void setFinDesc(String finDesc) {
		this.finDesc = finDesc;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getRepairRemark() {
		return repairRemark;
	}

	public void setRepairRemark(String repairRemark) {
		this.repairRemark = repairRemark;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getUsage() {
		return usage;
	}

	public void setUsage(Long usage) {
		this.usage = usage;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
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

	public String getIsWarranty() {
		return isWarranty;
	}

	public void setIsWarranty(String isWarranty) {
		this.isWarranty = isWarranty;
	}

	public Timestamp getPackTime() {
		return packTime;
	}

	public void setPackTime(Timestamp packTime) {
		this.packTime = packTime;
	}

	public String getCjgzDesc() {
		return cjgzDesc;
	}

	public void setCjgzDesc(String cjgzDesc) {
		this.cjgzDesc = cjgzDesc;
	}

	public String getZzgzDesc() {
		return zzgzDesc;
	}

	public void setZzgzDesc(String zzgzDesc) {
		this.zzgzDesc = zzgzDesc;
	}

	public String getDealMethod() {
		return dealMethod;
	}

	public void setDealMethod(String dealMethod) {
		this.dealMethod = dealMethod;
	}
	
	public String getIsPrice() {
		return isPrice;
	}

	public void setIsPrice(String isPrice) {
		this.isPrice = isPrice;
	}

	public String getFreeRepair() {
		return freeRepair;
	}

	public void setFreeRepair(String freeRepair) {
		this.freeRepair = freeRepair;
	}

	public String getRepairnNmber() {
		return repairnNmber;
	}

	public void setRepairnNmber(String repairnNmber) {
		this.repairnNmber = repairnNmber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFinRemark() {
		return finRemark;
	}

	public void setFinRemark(String finRemark) {
		this.finRemark = finRemark;
	}

	public String getOnePriceRemark() {
		return onePriceRemark;
	}

	public void setOnePriceRemark(String onePriceRemark) {
		this.onePriceRemark = onePriceRemark;
	}
	
	public String getTreeDate() {
		return treeDate;
	}

	public void setTreeDate(String treeDate) {
		this.treeDate = treeDate;
	}

	public String getTimeoutState() {
		return timeoutState;
	}

	public void setTimeoutState(String timeoutState) {
		this.timeoutState = timeoutState;
	}

	public String getRepairNumberState() {
		return repairNumberState;
	}

	public void setRepairNumberState(String repairNumberState) {
		this.repairNumberState = repairNumberState;
	}

	@Override
	public String toString() {
		return "HwmReport [id=" + id + ", cusName=" + cusName + ", model=" + model + ", imei=" + imei + ", repairnNmber=" + repairnNmber + ", acceptanceTime=" + acceptanceTime + ", backTime=" + backTime + ", timeoutDate=" + timeoutDate + ", stateDescribe=" + stateDescribe + ", dutyOfficer=" + dutyOfficer + ", timeoutRemark=" + timeoutRemark + ", returnTimes=" + isWarranty + ",isWarranty=" + returnTimes + ", packTime=" + packTime + ", state=" + state + ", cjgzDesc=" + cjgzDesc + ", zzgzDesc=" + zzgzDesc + ", dealMethod=" + dealMethod + ", accepter=" + accepter + ", packer=" + packer + ", finChecker=" + finChecker + ", finDesc=" + finDesc + ", engineer=" + engineer + ", remark=" + remark + ", repairRemark=" + repairRemark + ", finRemark=" + finRemark + ", onePriceRemark=" + onePriceRemark + ", offer=" + offer + ", usage=" + usage + ", ratio=" + ratio + ", startTime=" + startTime + ", endTime=" + endTime + ", isPrice=" + isPrice + ", freeRepair=" + freeRepair + "]";
	}

	public String getSolutionTwo() {
		return solutionTwo;
	}

	public void setSolutionTwo(String solutionTwo) {
		this.solutionTwo = solutionTwo;
	}

	public Integer getCompare() {
		return compare;
	}

	public void setCompare(Integer compare) {
		this.compare = compare;
	}

	public String getRepairDetailFlag() {
		return repairDetailFlag;
	}

	public void setRepairDetailFlag(String repairDetailFlag) {
		this.repairDetailFlag = repairDetailFlag;
	}

	public String getTimeoutReason() {
		return timeoutReason;
	}

	public void setTimeoutReason(String timeoutReason) {
		this.timeoutReason = timeoutReason;
	}

	public String getZzgzType() {
		return zzgzType;
	}

	public void setZzgzType(String zzgzType) {
		this.zzgzType = zzgzType;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
	}

	public Integer getRepairTimeType() {
		return repairTimeType;
	}

	public void setRepairTimeType(Integer repairTimeType) {
		this.repairTimeType = repairTimeType;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public String getCreateTypeName() {
		return createTypeName;
	}

	public void setCreateTypeName(String createTypeName) {
		this.createTypeName = createTypeName;
	}

	public Timestamp getSendPackTime() {
		return sendPackTime;
	}

	public void setSendPackTime(Timestamp sendPackTime) {
		this.sendPackTime = sendPackTime;
	}

	public Date getTimeoutBackTime() {
		return timeoutBackTime;
	}

	public void setTimeoutBackTime(Date timeoutBackTime) {
		this.timeoutBackTime = timeoutBackTime;
	}

	public Integer getTimeoutDays() {
		return timeoutDays;
	}

	public void setTimeoutDays(Integer timeoutDays) {
		this.timeoutDays = timeoutDays;
	}

	public Date getSendFicheckTime() {
		return sendFicheckTime;
	}

	public void setSendFicheckTime(Date sendFicheckTime) {
		this.sendFicheckTime = sendFicheckTime;
	}

	public Integer getWorkStation() {
		return workStation;
	}

	public void setWorkStation(Integer workStation) {
		this.workStation = workStation;
	}

	public Integer getTimeoutReasonState() {
		return timeoutReasonState;
	}

	public void setTimeoutReasonState(Integer timeoutReasonState) {
		this.timeoutReasonState = timeoutReasonState;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getSendPriceTime() {
		return sendPriceTime;
	}

	public void setSendPriceTime(Date sendPriceTime) {
		this.sendPriceTime = sendPriceTime;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}

	public String getSendPriceTimeStr() {
		return sendPriceTimeStr;
	}

	public void setSendPriceTimeStr(String sendPriceTimeStr) {
		this.sendPriceTimeStr = sendPriceTimeStr;
	}

	public String getSendFicheckTimeStr() {
		return sendFicheckTimeStr;
	}

	public void setSendFicheckTimeStr(String sendFicheckTimeStr) {
		this.sendFicheckTimeStr = sendFicheckTimeStr;
	}


	public String getTimeoutBackTimeStr() {
		return timeoutBackTimeStr;
	}

	public void setTimeoutBackTimeStr(String timeoutBackTimeStr) {
		this.timeoutBackTimeStr = timeoutBackTimeStr;
	}
}