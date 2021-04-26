package cn.concox.vo.report;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * 过保返修机收费
 *
 */
public class WarrantyReport {
	private String warrId;// id
	private String modelType;//主板型号
	private String repairNumber;//批次号
	private String state;// 状态（装箱8）
	private String imei;// IEMI
	private Integer isWarranty;//保内/保外
	private Integer isRW;//是否人为
	private String cusName;// 客户名称
	private String zzgzDesc;//故障描述
	private Timestamp priceDate;// 报价日期
	private String priceReason;// 报价原因
	private Integer number;// 数量
	private BigDecimal unitPrice;//单价
	private String payReason;//汇款原因
	private BigDecimal totalMoney;// 合计金额
	private String remAccount;// 汇款方式
	private Timestamp payDate;// 付款日期
	private Integer isPay;// 确认结果

	private String startTime;// 查询开始时间
	private String endTime;// 查询结束时间
	private String payType;//查询汇款方式
	private String[] imeis;// 查询多个imei
	
	private String priceRemark;//备注
	
	private String solution;//处理措施
	
	public String[] getImeis() {
		return imeis;
	}

	public void setImeis(String[] imeis) {
		this.imeis = imeis;
	}


	public String getWarrId() {
		return warrId;
	}

	public void setWarrId(String warrId) {
		this.warrId = warrId;
	}

	public String getRepairNumber() {
		return repairNumber;
	}

	public void setRepairNumber(String repairNumber) {
		this.repairNumber = repairNumber;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getZzgzDesc() {
		return zzgzDesc;
	}

	public void setZzgzDesc(String zzgzDesc) {
		this.zzgzDesc = zzgzDesc;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPayReason() {
		return payReason;
	}

	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getIsWarranty() {
		return isWarranty;
	}

	public void setIsWarranty(Integer isWarranty) {
		this.isWarranty = isWarranty;
	}

	public Integer getIsRW() {
		return isRW;
	}

	public void setIsRW(Integer isRW) {
		this.isRW = isRW;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Timestamp getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Timestamp priceDate) {
		this.priceDate = priceDate;
	}

	public String getPriceReason() {
		return priceReason;
	}

	public void setPriceReason(String priceReason) {
		this.priceReason = priceReason;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getRemAccount() {
		return remAccount;
	}

	public void setRemAccount(String remAccount) {
		this.remAccount = remAccount;
	}

	public Timestamp getPayDate() {
		return payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPriceRemark() {
		return priceRemark;
	}

	public void setPriceRemark(String priceRemark) {
		this.priceRemark = priceRemark;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
}
