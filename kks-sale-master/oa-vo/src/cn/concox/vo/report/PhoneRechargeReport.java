/*
 * Created: 2016-11-14
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 * @file: Phone.java
 * @functionName : 
 * @systemAbreviation : sale
 */
package cn.concox.vo.report;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * <pre>
 * 手机卡充值登记表(深圳移动卡)实体类
 * 数据库表名称：t_sale_recharge_phone
 * </pre>
 */
public class PhoneRechargeReport {
    private Integer cardId;
    /**
     * 字段名称：充值日期
     * 
     * 数据库字段信息:rechargeDate DATETIME(19)
     */
    private Timestamp rechargeDate;

    /**
     * 字段名称：客户名称
     * 
     * 数据库字段信息:cusName VARCHAR(255)
     */
    private String cusName;

    /**
     * 字段名称：IMEI
     * 
     * 数据库字段信息:imei VARCHAR(30)
     */
    private String imei;

    /**
     * 字段名称：手机号
     * 
     * 数据库字段信息:phone VARCHAR(11)
     */
    private String phone;

    /**
     * 字段名称：需付给卡商
     * 
     * 数据库字段信息:payOther DECIMAL(8,2)
     */
    private BigDecimal payOther;

    /**
     * 字段名称：充值面值
     * 
     * 数据库字段信息:faceValue DECIMAL(8,2)
     */
    private BigDecimal faceValue;

    /**
     * 字段名称：月数
     * 
     * 数据库字段信息:monthNumber VARCHAR(50)
     */
    private String monthNumber;

    /**
     * 字段名称：收客户费用
     * 
     * 数据库字段信息:payable DECIMAL(8,2)
     */
    private BigDecimal payable;

    /**
     * 字段名称：状态
     * 
     * 数据库字段信息:orderStatus VARCHAR(10)
     */
    private String orderStatus;

    /**
     * 字段名称：收款账号
     * 
     * 数据库字段信息:account VARCHAR(255)
     */
    private String account;

    /**
     * 字段名称：打5折
     * 
     * 数据库字段信息:isDiscount INT(10)
     */
    private Integer isDiscount;

    /**
     * 字段名称：移动系统充值
     * 
     * 数据库字段信息:isMobileCharge INT(10)
     */
    private Integer isMobileCharge;

    /**
     * 字段名称：汇款方式
     * 
     * 数据库字段信息:remittanceMethod VARCHAR(50)
     */
    private String remittanceMethod;

    /* =================================业务字段 start=============================== */
    private String cardIdsStr;

    private List<Integer> cardIds;// 数据id，批量操作时使用

    public String getCardIdsStr() {
        return cardIdsStr;
    }

    public void setCardIdsStr(String cardIdsStr) {
        this.cardIdsStr = cardIdsStr;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Integer> cardIds) {
        this.cardIds = cardIds;
    }

    private String startTime;// 开始时间

    private String endTime;// 结束时间

    private String projects;// 项目

    private BigDecimal costs;// 费用

    private String searchNOs;// 批量查询号码

    private BigDecimal ratePrice;// 税费

    private String remark;// 备注

    /* =================================业务字段 end=============================== */

    public BigDecimal getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(BigDecimal ratePrice) {
        this.ratePrice = ratePrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PhoneRechargeReport() {}

    public Timestamp getRechargeDate() {
        return this.rechargeDate;
    }

    public void setRechargeDate(Timestamp rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public String getCusName() {
        return this.cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getPayOther() {
        return this.payOther;
    }

    public void setPayOther(BigDecimal payOther) {
        this.payOther = payOther;
    }

    public BigDecimal getFaceValue() {
        return this.faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public String getMonthNumber() {
        return this.monthNumber;
    }

    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public BigDecimal getPayable() {
        return this.payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getIsDiscount() {
        return this.isDiscount;
    }

    public void setIsDiscount(Integer isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Integer getIsMobileCharge() {
        return this.isMobileCharge;
    }

    public void setIsMobileCharge(Integer isMobileCharge) {
        this.isMobileCharge = isMobileCharge;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public BigDecimal getCosts() {
        return costs;
    }

    public void setCosts(BigDecimal costs) {
        this.costs = costs;
    }

    public String getSearchNOs() {
        return searchNOs;
    }

    public void setSearchNOs(String searchNOs) {
        this.searchNOs = searchNOs;
    }

    public String getRemittanceMethod() {
        return remittanceMethod;
    }

    public void setRemittanceMethod(String remittanceMethod) {
        this.remittanceMethod = remittanceMethod;
    }

}