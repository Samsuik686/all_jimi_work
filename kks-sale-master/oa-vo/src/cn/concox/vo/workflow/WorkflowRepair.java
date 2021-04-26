package cn.concox.vo.workflow;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 维修 数据库表名称：t_sale_workflow_repair
 *
 */
public class WorkflowRepair {
	/**
	 * 字段名称：主键ID
	 * 
	 * 数据库字段信息:Id INT(11)
	 */
	private Integer Id;
	/**
	 * 字段名称：公共表id
	 * 
	 * 数据库字段信息:wfId INT(10)
	 */
	private Integer wfId;

	/**
	 * 字段名称：是否人为（0：是 1：否）
	 * 
	 * 数据库字段信息:isRW INT(10)
	 */
	private Integer isRW;

	/**
	 * 字段名称：是否付款（0：是 1：否）
	 * 
	 * 数据库字段信息:isPay INT(10)
	 */
	private Integer isPay;
	
	/**
	 * 字段名称：是否报价（0：是 1：否）
	 * 
	 * 数据库字段信息:isPrice INT(10)
	 */
	private Integer isPrice;

	/**
	 * 字段名称：维修工程师
	 * 
	 * 数据库字段信息:engineer VARCHAR(50)
	 */
	private String engineer;

	/**
	 * 字段名称：维修状态（0：未处理 1:已处理）
	 * 
	 * 数据库字段信息:repairState INT(10)
	 */
	private Long repairState;

	/**
	 * 字段名称：维修备注
	 * 
	 * 数据库字段信息:repairRemark VARCHAR(255)
	 */
	private String repairRemark;
	
	/**
	 * 字段名称：报价说明
	 * 
	 * 数据库字段信息:priceRemark VARCHAR(255)
	 */
	private String priceRemark;

	/**
	 * 字段名称：创建时间
	 * 
	 * 数据库字段信息:createTime DATETIME(19)
	 */
	private Timestamp createTime;

	/**
	 * 字段名称：修改时间
	 * 
	 * 数据库字段信息:updateTime DATETIME(19)
	 */
	private Timestamp updateTime;

	/**
	 * 字段名称：最终故障标识{如：1.XXX.2.XXX.列举｝
	 * 
	 * 数据库字段信息:zzgzDesc VARCHAR(255)
	 */
	private String zzgzDesc;
	
	/**
	 * 字段名称：维修报价标识{如：1.XXX.2.XXX.列举｝
	 * 
	 * 数据库字段信息:wxbjDesc VARCHAR(255)
	 */
	private String wxbjDesc;
	
	private String dzlDesc;

	/**
	 * 字段名称：故障处理方法标识{如：1.XXX.2.XXX.列举｝
	 * 
	 * 数据库字段信息:methodDesc VARCHAR(255)
	 */
	private String methodDesc;

	/**
	 * 字段名称：维修组件标识{如：1.XXX.2.XXX.列举｝
	 * 
	 * 数据库字段信息:matDesc VARCHAR(255)
	 */
	private String matDesc;
	
	/**
	 * 字段名称：维修报价
	 * 
	 * 数据库字段信息:repairPrice Decimal(22)
	 */
	private BigDecimal repairPrice;
	
	/**
	 * 内部维修次数
	 */
	private Integer repairAgainCount; 
	
	/**
	 * 字段名称：维修报价
	 * 
	 * 数据库字段信息:sumPrice Decimal(22)
	 */
	private BigDecimal sumPrice;
	
	/**
	 * 字段名称：处理方法
	 * 
	 * 数据库字段信息 ： solution varchar(255)
	 */
	private String solution;
	
	/**
	 * 字段名称：处理方法2
	 * 
	 * 数据库字段信息 ： solutionTwo varchar(255)
	 */
	private String solutionTwo;
	
	/**
	 * 字段名称：客户放弃维修
	 * 
	 * 数据库字段信息 ： giveUpRepairStatus varchar(10)  0：正常，1：放弃维修
	 */
	private String giveUpRepairStatus;
	
	/**
	 * 字段名称：放弃报价操作人
	 * 数据库字段信息 ：giveup_price_person varchar(30)
	 */
	private String giveupPricePerson;
	
	/**
	 * 字段名称：放弃报价操作时间
	 * 数据库字段信息 ：giveup_price_time datetime
	 */
	private Timestamp giveupPriceTime;
	
	private BigDecimal hourFee;//工时费
	
	/* =================================业务字段 start===============================*/
    private String startTime;//开始时间
    
    private String endTime;//结束时间
    
    private String treeDate; 
    /* =================================业务字段 end===============================*/
	
	/* -----------------------  业务字段  start------------------- */
	private String repair_Imei;             // IMEI
	private String repair_lockId;       	// 智能锁ID
	private String repair_bluetoothId;  	//蓝牙ID
	private String repair_model;			// 主板型号
	private String repair_modelType;		//主板型号类别
	private String repair_repairnNmber; 	// 送修批号
	private String xhId;                    // 型号ID
	
	private Integer rfild;
	private Integer isWarranty;             //保内 ：0| 保外： 1
	
	private Integer priceId;
	
	private Timestamp repair_payTime;//客户付款日期
	private Timestamp repair_sendPriceTime;//发送报价日期
	private String searchEngineer;
	private String repair_cusName; //送修单位
	
	private String repair_acceptRemark; //受理备注
	private String repair_remark; //送修备注
	private String repair_repairNumberRemark; //批次备注
	private String repair_priceRemark; //报价备注
	private String repair_onePriceRemark; //单台设备报价备注
	
	private String repair_bill; // 订单号
	private String repair_sfVersion;//软件版本号
	private Integer repair_outCount; // 该订单出货总数量
	private String repair_testMatStatus; // 是否使用试流料
	private String repair_insideSoftModel; // 内部机型
	private String sllDesc;//试流料
	
	private String testResult;  //测试结果
	private String testPerson;  //测试人
	private Timestamp testTime;    //测试时间
	
	/* -----------------------  业务字段  end --------------------- */

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getWfId() {
		return wfId;
	}

	public void setWfId(Integer wfId) {
		this.wfId = wfId;
	}

	public Integer getIsRW() {
		return isRW;
	}

	public void setIsRW(Integer isRW) {
		this.isRW = isRW;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public Integer getIsPrice() {
		return isPrice;
	}

	public void setIsPrice(Integer isPrice) {
		this.isPrice = isPrice;
	}

	public String getPriceRemark() {
		return priceRemark;
	}

	public void setPriceRemark(String priceRemark) {
		this.priceRemark = priceRemark;
	}
	
	public BigDecimal getRepairPrice() {
		return repairPrice;
	}

	public void setRepairPrice(BigDecimal repairPrice) {
		this.repairPrice = repairPrice;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public Long getRepairState() {
		return repairState;
	}

	public void setRepairState(Long repairState) {
		this.repairState = repairState;
	}
	
	public String getWxbjDesc() {
		return wxbjDesc;
	}

	public void setWxbjDesc(String wxbjDesc) {
		this.wxbjDesc = wxbjDesc;
	}

	public String getRepairRemark() {
		return repairRemark;
	}

	public void setRepairRemark(String repairRemark) {
		this.repairRemark = repairRemark;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getZzgzDesc() {
		return zzgzDesc;
	}

	public void setZzgzDesc(String zzgzDesc) {
		this.zzgzDesc = zzgzDesc;
	}

	public String getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}

	public String getMatDesc() {
		return matDesc;
	}

	public void setMatDesc(String matDesc) {
		this.matDesc = matDesc;
	}

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Integer getRepairAgainCount() {
		return (repairAgainCount==null)?0:repairAgainCount;
	}

	public void setRepairAgainCount(Integer repairAgainCount) {
		this.repairAgainCount = repairAgainCount;
	}

	public String getRepair_model() {
		return repair_model;
	}

	public void setRepair_model(String repair_model) {
		this.repair_model = repair_model;
	}

	public String getRepair_repairnNmber() {
		return repair_repairnNmber;
	}

	public void setRepair_repairnNmber(String repair_repairnNmber) {
		this.repair_repairnNmber = repair_repairnNmber;
	}

	public Integer getRfild() {
		return rfild;
	}

	public void setRfild(Integer rfild) {
		this.rfild = rfild;
	}

	public Integer getIsWarranty() {
		return isWarranty;
	}

	public void setIsWarranty(Integer isWarranty) {
		this.isWarranty = isWarranty;
	}

	public String getXhId() {
		return xhId;
	}

	public void setXhId(String xhId) {
		this.xhId = xhId;
	}

	public String getRepair_Imei() {
		return repair_Imei;
	}

	public void setRepair_Imei(String repair_Imei) {
		this.repair_Imei = repair_Imei;
	}

	public String getDzlDesc() {
		return dzlDesc;
	}

	public void setDzlDesc(String dzlDesc) {
		this.dzlDesc = dzlDesc;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
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

	public String getRepair_modelType() {
		return repair_modelType;
	}

	public void setRepair_modelType(String repair_modelType) {
		this.repair_modelType = repair_modelType;
	}

	public String getSolutionTwo() {
		return solutionTwo;
	}

	public void setSolutionTwo(String solutionTwo) {
		this.solutionTwo = solutionTwo;
	}
	
	public String getGiveUpRepairStatus() {
		return giveUpRepairStatus;
	}

	public void setGiveUpRepairStatus(String giveUpRepairStatus) {
		this.giveUpRepairStatus = giveUpRepairStatus;
	}
	
	public String getGiveupPricePerson() {
		return giveupPricePerson;
	}

	public void setGiveupPricePerson(String giveupPricePerson) {
		this.giveupPricePerson = giveupPricePerson;
	}

	public Timestamp getGiveupPriceTime() {
		return giveupPriceTime;
	}

	public void setGiveupPriceTime(Timestamp giveupPriceTime) {
		this.giveupPriceTime = giveupPriceTime;
	}

	public Integer getPriceId() {
		return priceId;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	public String getRepair_onePriceRemark() {
		return repair_onePriceRemark;
	}

	public void setRepair_onePriceRemark(String repair_onePriceRemark) {
		this.repair_onePriceRemark = repair_onePriceRemark;
	}

	public Timestamp getRepair_payTime() {
		return repair_payTime;
	}

	public void setRepair_payTime(Timestamp repair_payTime) {
		this.repair_payTime = repair_payTime;
	}

	public String getSearchEngineer() {
		return searchEngineer;
	}

	public void setSearchEngineer(String searchEngineer) {
		this.searchEngineer = searchEngineer;
	}

	public String getRepair_cusName() {
		return repair_cusName;
	}

	public void setRepair_cusName(String repair_cusName) {
		this.repair_cusName = repair_cusName;
	}

	public String getRepair_acceptRemark() {
		return repair_acceptRemark;
	}

	public void setRepair_acceptRemark(String repair_acceptRemark) {
		this.repair_acceptRemark = repair_acceptRemark;
	}

	public String getRepair_remark() {
		return repair_remark;
	}

	public void setRepair_remark(String repair_remark) {
		this.repair_remark = repair_remark;
	}

	public String getRepair_repairNumberRemark() {
		return repair_repairNumberRemark;
	}

	public void setRepair_repairNumberRemark(String repair_repairNumberRemark) {
		this.repair_repairNumberRemark = repair_repairNumberRemark;
	}

	public String getRepair_priceRemark() {
		return repair_priceRemark;
	}

	public void setRepair_priceRemark(String repair_priceRemark) {
		this.repair_priceRemark = repair_priceRemark;
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

	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public Timestamp getRepair_sendPriceTime() {
		return repair_sendPriceTime;
	}

	public void setRepair_sendPriceTime(Timestamp repair_sendPriceTime) {
		this.repair_sendPriceTime = repair_sendPriceTime;
	}

	public String getRepair_bill() {
		return repair_bill;
	}

	public void setRepair_bill(String repair_bill) {
		this.repair_bill = repair_bill;
	}

	public Integer getRepair_outCount() {
		return repair_outCount;
	}

	public void setRepair_outCount(Integer repair_outCount) {
		this.repair_outCount = repair_outCount;
	}

	public String getRepair_testMatStatus() {
		return repair_testMatStatus;
	}

	public void setRepair_testMatStatus(String repair_testMatStatus) {
		this.repair_testMatStatus = repair_testMatStatus;
	}

	public String getSllDesc() {
		return sllDesc;
	}

	public void setSllDesc(String sllDesc) {
		this.sllDesc = sllDesc;
	}

	public String getRepair_sfVersion() {
		return repair_sfVersion;
	}

	public void setRepair_sfVersion(String repair_sfVersion) {
		this.repair_sfVersion = repair_sfVersion;
	}

	public String getRepair_insideSoftModel() {
		return repair_insideSoftModel;
	}

	public void setRepair_insideSoftModel(String repair_insideSoftModel) {
		this.repair_insideSoftModel = repair_insideSoftModel;
	}

	public String getRepair_lockId() {
		return repair_lockId;
	}

	public void setRepair_lockId(String repair_lockId) {
		this.repair_lockId = repair_lockId;
	}

	public String getRepair_bluetoothId() {
		return repair_bluetoothId;
	}

	public void setRepair_bluetoothId(String repair_bluetoothId) {
		this.repair_bluetoothId = repair_bluetoothId;
	}

	public BigDecimal getHourFee() {
		return hourFee;
	}

	public void setHourFee(BigDecimal hourFee) {
		this.hourFee = hourFee;
	}
	
}