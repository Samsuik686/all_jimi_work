package cn.concox.vo.report;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <pre>
 * 销售配件收费表实体类
 * 数据库表名称：t_sale_xspjcosts
 * </pre>
 */
public class XspjcostsReport {
	private Integer saleId;

	/**
	 * 字段名称：客户名称
	 * 
	 * 数据库字段信息:cusName VARCHAR(255)
	 */
	private String cusName;

	/**
	 * 字段名称：销售日期
	 * 
	 * 数据库字段信息:saleDate DATETIME(19)
	 */
	private Timestamp saleDate;

	/**
	 * 字段名称：市场型号
	 * 
	 * 数据库字段信息:modelType VARCHAR(20)
	 */
	private String marketModel;

	/**
	 * 字段名称：物料名称
	 * 
	 * 数据库字段信息:proName VARCHAR(50)
	 */
	private String proName;

	/**
	 * 字段名称：数量
	 * 
	 * 数据库字段信息:number INT(10)
	 */
	private Integer number;

	/**
	 * 字段名称：单价
	 * 
	 * 数据库字段信息:unitPrice Decimal(8,2)
	 */
	private BigDecimal unitPrice;

	/**
	 * 字段名称：收款原因
	 * 
	 * 数据库字段信息:payReason VARCHAR(50)
	 */
	private String payReason;

	/**
	 * 字段名称：收款金额
	 * 
	 * 数据库字段信息:payPrice Decimal(8,2)
	 */
	private BigDecimal payPrice;

	/**
	 * 字段名称：汇款方式
	 * 
	 * 数据库字段信息:payMethod VARCHAR(50)
	 */
	private String payMethod;

	/**
	 * 字段名称：付款日期
	 * 
	 * 数据库字段信息:payDate DATETIME(19)
	 */
	private Timestamp payDate;

	/**
	 * 字段名称：确认结果
	 * 
	 * 数据库字段信息:payState VARCHAR(20)
	 */
	private String payState;

	/**
	 * 字段名称：备注原因
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;

	private BigDecimal logCost;// 运费
	
	private String expressType;// 快递方式
	
	private String expressNO;// 快递单号
	
	private String repairNumber;//批次配件批次号
	
	private Integer receipt;//是否开发票
	private BigDecimal rate;//税率
	private BigDecimal ratePrice;//税费
	private BigDecimal sumPrice;//总费用
	
	private String proNO;//物料编码
	private String masterOrSlave;//M\T
	private Integer groupPjId;//销售配件料组合id
	private String groupName;//组合名称
	
	private String createBy; 
//	private Timestamp createTime;
//	private String updateBy;
//	private Timestamp updateTime;

	/* =================================业务字段 start=============================== */
	private String startTime;// 开始时间

	private String endTime;// 结束时间

	private String payType;//查询汇款方式
	/* =================================业务字段 end=============================== */

	public XspjcostsReport() {
	}

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Timestamp getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(Timestamp saleDate) {
		this.saleDate = saleDate;
	}

	public String getMarketModel() {
		return marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPayReason() {
		return this.payReason;
	}

	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}

	public BigDecimal getPayPrice() {
		return this.payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Timestamp getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	public String getPayState() {
		return this.payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getRemark() {
		return this.remark;
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

	public BigDecimal getLogCost() {
		return logCost;
	}

	public void setLogCost(BigDecimal logCost) {
		this.logCost = logCost;
	}

	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}

	public String getExpressNO() {
		return expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRepairNumber() {
		return repairNumber;
	}

	public void setRepairNumber(String repairNumber) {
		this.repairNumber = repairNumber;
	}

	public Integer getReceipt() {
		return receipt;
	}

	public void setReceipt(Integer receipt) {
		this.receipt = receipt;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getRatePrice() {
		return ratePrice;
	}

	public void setRatePrice(BigDecimal ratePrice) {
		this.ratePrice = ratePrice;
	}

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getProNO() {
		return proNO;
	}

	public void setProNO(String proNO) {
		this.proNO = proNO;
	}

	public Integer getGroupPjId() {
		return groupPjId;
	}

	public void setGroupPjId(Integer groupPjId) {
		this.groupPjId = groupPjId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getMasterOrSlave() {
		return masterOrSlave;
	}

	public void setMasterOrSlave(String masterOrSlave) {
		this.masterOrSlave = masterOrSlave;
	}


}