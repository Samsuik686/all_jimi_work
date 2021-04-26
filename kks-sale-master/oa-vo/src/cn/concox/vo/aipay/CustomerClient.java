package cn.concox.vo.aipay;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CustomerClient {

	private Integer id;

	private String repairnNmber;

	private String cusName;

	private String imei;

	private String marketModel; //机型

	private Integer isWarranty;

	private Timestamp acceptTime;

	private Timestamp payTime;

	private Long state;

	private String sjfjDesc;

	private String cjgzDesc;

	private String zzgzDesc;

	private BigDecimal sumPrice;//维修总费用

	private String phone;

	private BigDecimal totalsum;

	private BigDecimal logCost;
	
	/**
	 * 延保费
	 */
	private BigDecimal prolongCost;
	
	public BigDecimal getProlongCost() {
        return prolongCost;
    }

    public void setProlongCost(BigDecimal prolongCost) {
        this.prolongCost = prolongCost;
    }

    private BigDecimal totalPrice;//总费用
	
	private String solutionTwo; // 解决措施2

	private String solution; // 解决措施
	
	private String isRW; //是否人为
	
	private BigDecimal ratePrice; //税费
	
	private BigDecimal batchPjCosts; //批次配件费
	
	private BigDecimal sumRepair;
	
	private String wxbjDesc; //维修报价描述
	
	private String searchPrice; //只查询待付款数据

	private String remAccount; //客户付款方式
	// 交易流水
	private String outTradeNo;
	
	private String expressCompany; //快递公司
	
	private String expressNO; //快递单号
	
	private String giveUpRepairStatus; //客户放弃维修状态 0：正常，1：放弃
	
	private String isPay; //是否付款
	
	private String searchKey; //查询关键字
	
	private String remark;//送修备注
	
	private String loginPwd;//登录密码
	
	private String treeTime;//日期树（年-月）
	
    private String startTime;//批量导出开始时间
    
    private String endTime;//批量结束时间
    
    private String treeMonth;//月

	public CustomerClient() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMarketModel() {
		return marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}

	public Integer getIsWarranty() {
		return isWarranty;
	}

	public void setIsWarranty(Integer isWarranty) {
		this.isWarranty = isWarranty;
	}

	public Timestamp getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Timestamp acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getSjfjDesc() {
		return sjfjDesc;
	}

	public void setSjfjDesc(String sjfjDesc) {
		this.sjfjDesc = sjfjDesc;
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

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getTotalsum() {
		return totalsum == null ? BigDecimal.ZERO : totalsum;
	}

	public void setTotalsum(BigDecimal totalsum) {
		this.totalsum = totalsum;
	}

	public BigDecimal getLogCost() {
		return logCost == null ? BigDecimal.ZERO : logCost;
	}

	public void setLogCost(BigDecimal logCost) {
		this.logCost = logCost;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice == null ? BigDecimal.ZERO : totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSolutionTwo() {
		return solutionTwo;
	}

	public void setSolutionTwo(String solutionTwo) {
		this.solutionTwo = solutionTwo;
	}

	public String getIsRW() {
		return isRW;
	}

	public void setIsRW(String isRW) {
		this.isRW = isRW;
	}

	public BigDecimal getRatePrice() {
		return ratePrice;
	}

	public void setRatePrice(BigDecimal ratePrice) {
		this.ratePrice = ratePrice;
	}

	public BigDecimal getBatchPjCosts() {
		return batchPjCosts;
	}

	public void setBatchPjCosts(BigDecimal batchPjCosts) {
		this.batchPjCosts = batchPjCosts;
	}

	public BigDecimal getSumRepair() {
		return sumRepair;
	}

	public void setSumRepair(BigDecimal sumRepair) {
		this.sumRepair = sumRepair;
	}

	public String getWxbjDesc() {
		return wxbjDesc;
	}

	public void setWxbjDesc(String wxbjDesc) {
		this.wxbjDesc = wxbjDesc;
	}

	public String getSearchPrice() {
		return searchPrice;
	}

	public void setSearchPrice(String searchPrice) {
		this.searchPrice = searchPrice;
	}

	public String getRemAccount() {
		return remAccount;
	}

	public void setRemAccount(String remAccount) {
		this.remAccount = remAccount;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNO() {
		return expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public String getGiveUpRepairStatus() {
		return giveUpRepairStatus;
	}

	public void setGiveUpRepairStatus(String giveUpRepairStatus) {
		this.giveUpRepairStatus = giveUpRepairStatus;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getTreeTime() {
		return treeTime;
	}

	public void setTreeTime(String treeTime) {
		this.treeTime = treeTime;
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

	public String getTreeMonth() {
		return treeMonth;
	}

	public void setTreeMonth(String treeMonth) {
		this.treeMonth = treeMonth;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
}
