package cn.concox.vo.report;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * <pre>
 * 平台充值表实体类
 * 数据库表名称：t_sale_recharge
 * </pre>
 */
public class RechargeReport {
    private Integer rechId;
    /**
     * 字段名称：单位名称
     * 
     * 数据库字段信息:unitName VARCHAR(255)
     */
    private String unitName;

    /**
     * 字段名称：充值日期
     * 
     * 数据库字段信息:rechargeDate DATETIME(19)
     */
    private Timestamp rechargeDate;

    /**
     * 字段名称：IMEI号
     * 
     * 数据库字段信息:imei VARCHAR(30)
     */
    private String imei;

    /**
     * 字段名称：一年/终身（0/1）
     * 
     * 数据库字段信息:term INT(10)
     */
    private Integer term;

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
     * 字段名称： 收款合计
     * 
     * 数据库字段信息:totalCollection Decimal(8,2)
     */
    private BigDecimal totalCollection;

    /**
     * 字段名称：汇款方式
     * 
     * 数据库字段信息:remittanceMethod VARCHAR(50)
     */
    private String remittanceMethod;

    /**
     * 字段名称：订单状态
     * 
     * 数据库字段信息:orderStatus VARCHAR(10)
     */
    private String orderStatus;

    /**
     * 字段名称：备注
     * 
     * 数据库字段信息:remark VARCHAR(255)
     */
    private String remark;

    private BigDecimal ratePrice;// 税费

    /* =================================业务字段 start=============================== */
    private String rechIdsStr;
    
    private List<Integer> rechIds;// 数据id，批量操作时使用

    private String startTime;// 开始时间

    private String endTime;// 结束时间

    private Integer yearNumber; // 一年数量

    private Integer lifeNumber;// 终身数量

    private BigDecimal yearUnitPrice;// 一年单价

    private BigDecimal lifeUnitPrice;// 终身单价

    private BigDecimal yearTotalCollection;// 一年总价

    private BigDecimal lifeTotalCollection;// 终身总价

    private BigDecimal yearRatePrice;// 一年总价税费

    private BigDecimal lifeRatePrice;// 终身总价税费

    /* =================================业务字段 end=============================== */

    public RechargeReport() {}

    public Integer getRechId() {
        return rechId;
    }

    public void setRechId(Integer rechId) {
        this.rechId = rechId;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Timestamp getRechargeDate() {
        return this.rechargeDate;
    }

    public void setRechargeDate(Timestamp rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getTerm() {
        return this.term;
    }

    public void setTerm(Integer term) {
        this.term = term;
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

    public BigDecimal getTotalCollection() {
        return this.totalCollection;
    }

    public void setTotalCollection(BigDecimal totalCollection) {
        this.totalCollection = totalCollection;
    }

    public String getRemittanceMethod() {
        return this.remittanceMethod;
    }

    public void setRemittanceMethod(String remittanceMethod) {
        this.remittanceMethod = remittanceMethod;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRechIdsStr() {
        return rechIdsStr;
    }

    public void setRechIdsStr(String rechIdsStr) {
        this.rechIdsStr = rechIdsStr;
    }

    public List<Integer> getRechIds() {
        return rechIds;
    }

    public void setRechIds(List<Integer> rechIds) {
        this.rechIds = rechIds;
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

    public BigDecimal getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(BigDecimal ratePrice) {
        this.ratePrice = ratePrice;
    }

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
    }

    public Integer getLifeNumber() {
        return lifeNumber;
    }

    public void setLifeNumber(Integer lifeNumber) {
        this.lifeNumber = lifeNumber;
    }

    public BigDecimal getYearUnitPrice() {
        return yearUnitPrice;
    }

    public void setYearUnitPrice(BigDecimal yearUnitPrice) {
        this.yearUnitPrice = yearUnitPrice;
    }

    public BigDecimal getLifeUnitPrice() {
        return lifeUnitPrice;
    }

    public void setLifeUnitPrice(BigDecimal lifeUnitPrice) {
        this.lifeUnitPrice = lifeUnitPrice;
    }

    public BigDecimal getYearTotalCollection() {
        return yearTotalCollection;
    }

    public void setYearTotalCollection(BigDecimal yearTotalCollection) {
        this.yearTotalCollection = yearTotalCollection;
    }

    public BigDecimal getLifeTotalCollection() {
        return lifeTotalCollection;
    }

    public void setLifeTotalCollection(BigDecimal lifeTotalCollection) {
        this.lifeTotalCollection = lifeTotalCollection;
    }

    public BigDecimal getYearRatePrice() {
        return yearRatePrice;
    }

    public void setYearRatePrice(BigDecimal yearRatePrice) {
        this.yearRatePrice = yearRatePrice;
    }

    public BigDecimal getLifeRatePrice() {
        return lifeRatePrice;
    }

    public void setLifeRatePrice(BigDecimal lifeRatePrice) {
        this.lifeRatePrice = lifeRatePrice;
    }

}