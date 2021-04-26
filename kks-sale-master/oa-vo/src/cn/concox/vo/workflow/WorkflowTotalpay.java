package cn.concox.vo.workflow;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <pre>
 * 批次报价总记录表实体类
 * 数据库表名称：t_sale_workflow_totalpay
 * </pre>
 */
public class WorkflowTotalpay {
    private Integer id;
    /**
     * 字段名称：批次号
     * 
     * 数据库字段信息:repairNumber VARCHAR(50)
     */
    private String repairNumber;

    /**
     * 字段名称：付款原因
     * 
     * 数据库字段信息:payReason VARCHAR(255)
     */
    private String payReason;

    /**
     * 字段名称：付款方式
     * 
     * 数据库字段信息:remAccount VARCHAR(255)
     */
    private String remAccount;

    /**
     * 字段名称：付款日期
     * 
     * 数据库字段信息:payTime DATETIME(19)
     */
    private Timestamp payTime;

    /**
     * 字段名称：维修批次的总金额（组件+工时费+故障报价）
     * 
     * 数据库字段信息:repairMoney Decimal(22)
     */
    private BigDecimal repairMoney;

    /**
     * 字段名称：物流费
     * 
     * 数据库字段信息:logCost Decimal(22)
     */
    private BigDecimal logCost;

    /**
     * 字段名称：延保费
     * 
     * 数据库字段信息:prolongCost Decimal(22)
     */
    private BigDecimal prolongCost;

    /**
     * 字段名称：总金额（维修批次总费用+运费）
     * 
     * 数据库字段信息:totalMoney Decimal(22)
     */
    private BigDecimal totalMoney;

    /**
     * 是否付款
     */
    private Integer isPay;

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
     * 字段名称：客户收货方式（顺付：Y,到付：N）
     * 
     * 数据库字段信息:customerReceipt VARCHAR(10)
     */
    private String customerReceipt;

    /**
     * 字段名称：报价备注
     * 
     * 数据库字段信息:priceRemark VARCHAR(200)
     */
    private String priceRemark;

    /**
     * 字段名称：是否要开发票
     * 
     * 数据库字段信息:receipt varchar(10) 0：是，1否
     */
    private String receipt;

    /**
     * 字段名称：税率
     * 
     * 数据库字段信息:rate decimal(8,2)
     */
    private BigDecimal rate;

    /**
     * 字段名称：税费
     * 
     * 数据库字段信息:ratePrice decimal(8,2)
     */
    private BigDecimal ratePrice;

    /* =============================业务字段 START=========================================== */
    private boolean isAutoPay;// 是否人工确认付款

    private BigDecimal batchPjCosts;// 批次添加配件总费用

    private BigDecimal sumPrice; // 维修总费用
    /* =============================业务字段 END=========================================== */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepairNumber() {
        return repairNumber;
    }

    public void setRepairNumber(String repairNumber) {
        this.repairNumber = repairNumber;
    }

    public String getPayReason() {
        return payReason;
    }

    public void setPayReason(String payReason) {
        this.payReason = payReason;
    }

    public String getRemAccount() {
        return remAccount;
    }

    public void setRemAccount(String remAccount) {
        this.remAccount = remAccount;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getRepairMoney() {
        return repairMoney;
    }

    public void setRepairMoney(BigDecimal repairMoney) {
        this.repairMoney = repairMoney;
    }

    public BigDecimal getLogCost() {
        return logCost;
    }

    public void setLogCost(BigDecimal logCost) {
        this.logCost = logCost;
    }

    public BigDecimal getProlongCost() {
        return prolongCost;
    }

    public void setProlongCost(BigDecimal prolongCost) {
        this.prolongCost = prolongCost;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
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

    public String getCustomerReceipt() {
        return customerReceipt;
    }

    public void setCustomerReceipt(String customerReceipt) {
        this.customerReceipt = customerReceipt;
    }

    public String getPriceRemark() {
        return priceRemark;
    }

    public void setPriceRemark(String priceRemark) {
        this.priceRemark = priceRemark;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public boolean getIsAutoPay() {
        return isAutoPay;
    }

    public void setIsAutoPay(boolean isAutoPay) {
        this.isAutoPay = isAutoPay;
    }

    public BigDecimal getBatchPjCosts() {
        return batchPjCosts;
    }

    public void setBatchPjCosts(BigDecimal batchPjCosts) {
        this.batchPjCosts = batchPjCosts;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(BigDecimal ratePrice) {
        this.ratePrice = ratePrice;
    }

}
