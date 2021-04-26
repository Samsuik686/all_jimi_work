/*
 * Created: 2016-08-19 14:23:49
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
 */
package cn.concox.vo.report;

import java.math.BigDecimal;

public class Report {

    /** ----------------- 业务字段 ---------------------------- Start **/
    // [物料报价管理]
    private int id; // ID

    private String bid; // Bid

    private String proName; // 物料名称

    private String proSpeci; // 物料规格

    private String proNO; // 物料编码

    private String model; // 型号

    private String marketModel;// 市场型号

    // [二次返修报表]
    private String engineer; // 维修员

    private String returnTimes; // 返修次数

    private String returnTimesA; // 二次返修次数

    private String returnTimesP; // 返修次数概率

    private String StratTime; // 开始时间

    private String EndTime; // 结束时间

    private Integer sumCount; // 受理总数

    private Integer giveUpRepair; // 放弃维修总数

    private Integer twiceRepair; // 二次返修总数

    private Object data; // 二次维修数据

    private Integer consumption; // 用量

    private BigDecimal retailPrice; // 零售价

    private BigDecimal ourlyRates; // 工时费

    /** ----------------- 业务字段 ---------------------------- End **/

    public Report() {}

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProSpeci() {
        return proSpeci;
    }

    public void setProSpeci(String proSpeci) {
        this.proSpeci = proSpeci;
    }

    public String getProNO() {
        return proNO;
    }

    public void setProNO(String proNO) {
        this.proNO = proNO;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getReturnTimes() {
        return returnTimes;
    }

    public void setReturnTimes(String returnTimes) {
        this.returnTimes = returnTimes;
    }

    public String getReturnTimesA() {
        return returnTimesA;
    }

    public void setReturnTimesA(String returnTimesA) {
        this.returnTimesA = returnTimesA;
    }

    public String getReturnTimesP() {
        return returnTimesP;
    }

    public void setReturnTimesP(String returnTimesP) {
        this.returnTimesP = returnTimesP;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getOurlyRates() {
        return ourlyRates;
    }

    public void setOurlyRates(BigDecimal ourlyRates) {
        this.ourlyRates = ourlyRates;
    }

    public String getStratTime() {
        return StratTime;
    }

    public void setStratTime(String stratTime) {
        StratTime = stratTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public Integer getGiveUpRepair() {
        return giveUpRepair;
    }

    public void setGiveUpRepair(Integer giveUpRepair) {
        this.giveUpRepair = giveUpRepair;
    }

    public Integer getTwiceRepair() {
        return twiceRepair;
    }

    public void setTwiceRepair(Integer twiceRepair) {
        this.twiceRepair = twiceRepair;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMarketModel() {
        return marketModel;
    }

    public void setMarketModel(String marketModel) {
        this.marketModel = marketModel;
    }
}