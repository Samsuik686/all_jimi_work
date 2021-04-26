package cn.concox.vo.report;

import java.math.BigDecimal;

/**
 * 售后收费
 *
 */
public class CollectFeesReport {
//	private String moneyType;// 款项
//	private BigDecimal sumMoney;// 收费金额

	private String payMonth;// 月份

	private String payTimes;// 日期
	private BigDecimal repairPay;// 维修费
	private BigDecimal materialPay;// 配件费
	private BigDecimal rechargePay;// 平台费
	private BigDecimal phoneRecharge;// 手机卡充值收费
	private BigDecimal wlwkRecharge;// 物联网卡充值收费
	private BigDecimal logCost;// 运费
	private BigDecimal ratePrice;// 税费
	private BigDecimal totalPay;// 当天金额合计
	private BigDecimal sumTotalPay; // 总合计金额

	private BigDecimal tlogCost;// 报价工站运费
	private BigDecimal tratePrice;// 报价工站税费
	private BigDecimal xlogCost;// 销售配件运费
	private BigDecimal xratePrice;// 销售配件税费

	private BigDecimal phoneRatePrice;// 手机卡税费
	private BigDecimal wlwkRatePrice;// 物联网卡税费
	private BigDecimal reRatePrice;// 平台税费
	
	private String searchMonth;// 年收费查询输入时间
	private String searchTime;// 月收费查询输入时间
	
//	public String getMoneyType() {
//		return moneyType;
//	}
//
//	public void setMoneyType(String moneyType) {
//		this.moneyType = moneyType;
//	}

	public String getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

//	public BigDecimal getSumMoney() {
//		return sumMoney;
//	}
//
//	public void setSumMoney(BigDecimal sumMoney) {
//		this.sumMoney = sumMoney;
//	}

	public String getSearchMonth() {
		return searchMonth;
	}

	public void setSearchMonth(String searchMonth) {
		this.searchMonth = searchMonth;
	}

	public String getPayTimes() {
		return payTimes;
	}

	public void setPayTimes(String payTimes) {
		this.payTimes = payTimes;
	}

	public BigDecimal getRepairPay() {
		return repairPay;
	}

	public void setRepairPay(BigDecimal repairPay) {
		this.repairPay = repairPay;
	}

	public BigDecimal getMaterialPay() {
		return materialPay;
	}

	public BigDecimal getPhoneRatePrice() {
		return phoneRatePrice;
	}

	public void setPhoneRatePrice(BigDecimal phoneRatePrice) {
		this.phoneRatePrice = phoneRatePrice;
	}

	public BigDecimal getWlwkRatePrice() {
		return wlwkRatePrice;
	}

	public void setWlwkRatePrice(BigDecimal wlwkRatePrice) {
		this.wlwkRatePrice = wlwkRatePrice;
	}

	public void setMaterialPay(BigDecimal materialPay) {
		this.materialPay = materialPay;
	}

	public BigDecimal getRechargePay() {
		return rechargePay;
	}

	public void setRechargePay(BigDecimal rechargePay) {
		this.rechargePay = rechargePay;
	}

	public BigDecimal getPhoneRecharge() {
		return phoneRecharge;
	}

	public void setPhoneRecharge(BigDecimal phoneRecharge) {
		this.phoneRecharge = phoneRecharge;
	}

	public BigDecimal getWlwkRecharge() {
		return wlwkRecharge;
	}

	public void setWlwkRecharge(BigDecimal wlwkRecharge) {
		this.wlwkRecharge = wlwkRecharge;
	}

	public BigDecimal getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(BigDecimal totalPay) {
		this.totalPay = totalPay;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	public BigDecimal getSumTotalPay() {
		return sumTotalPay;
	}

	public void setSumTotalPay(BigDecimal sumTotalPay) {
		this.sumTotalPay = sumTotalPay;
	}

	public BigDecimal getLogCost() {
		return logCost;
	}

	public void setLogCost(BigDecimal logCost) {
		this.logCost = logCost;
	}

	public BigDecimal getRatePrice() {
		return ratePrice;
	}

	public void setRatePrice(BigDecimal ratePrice) {
		this.ratePrice = ratePrice;
	}

	public BigDecimal getTlogCost() {
		return tlogCost;
	}

	public void setTlogCost(BigDecimal tlogCost) {
		this.tlogCost = tlogCost;
	}

	public BigDecimal getTratePrice() {
		return tratePrice;
	}

	public void setTratePrice(BigDecimal tratePrice) {
		this.tratePrice = tratePrice;
	}

	public BigDecimal getXlogCost() {
		return xlogCost;
	}

	public void setXlogCost(BigDecimal xlogCost) {
		this.xlogCost = xlogCost;
	}

	public BigDecimal getXratePrice() {
		return xratePrice;
	}

	public void setXratePrice(BigDecimal xratePrice) {
		this.xratePrice = xratePrice;
	}

	public BigDecimal getReRatePrice() {
		return reRatePrice;
	}

	public void setReRatePrice(BigDecimal reRatePrice) {
		this.reRatePrice = reRatePrice;
	}


}
