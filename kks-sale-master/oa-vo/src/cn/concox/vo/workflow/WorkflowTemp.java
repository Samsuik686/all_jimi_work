/*
 * Created: 2016-7-20
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
package cn.concox.vo.workflow;

import java.util.Date;

/**
 * <pre>
 * 售后管理工作流公用表实体类
 * 数据库表名称：t_sale_workflow
 * </pre>
 * 
 * @author Li.ShangZhi
 * @version v1.0
 */
public class WorkflowTemp {
    private String uid;
    private String imei;
    private String lockId;
    private String lockInfo;
    private String bluetoothId;
    private String sxdwId;
    private String xhId;
    private String w_sjfj;
    private String w_cjgzId;
    private String repairnNmber;
    private String isWarranty;
    private String returnTimes;
    private String lastEngineer;
    private String strLastAccTime;
    private String strSalesTime;
    private String payDelivery;
    private String remark;
    private String acceptRemark;
    private String repairNumberRemark;
    private String customerStatus;
    private String cusName;
    private String unNormalExpressCompany;
    private String unNormalExpressNo;
    private String sfVersion;
    private String insideSoftModel;
    private String bill;
    private String outCount;
    private String testMatStatus;
    private String model;
    private String marketModel;
    private String initheckFault;
    private String sjfjname;
    private String backTime;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getLockInfo() {
        return lockInfo;
    }

    public void setLockInfo(String lockInfo) {
        this.lockInfo = lockInfo;
    }

    public String getBluetoothId() {
        return bluetoothId;
    }

    public void setBluetoothId(String bluetoothId) {
        this.bluetoothId = bluetoothId;
    }

    public String getSxdwId() {
        return sxdwId;
    }

    public void setSxdwId(String sxdwId) {
        this.sxdwId = sxdwId;
    }

    public String getXhId() {
        return xhId;
    }

    public void setXhId(String xhId) {
        this.xhId = xhId;
    }

    public String getW_sjfj() {
        return w_sjfj;
    }

    public void setW_sjfj(String w_sjfj) {
        this.w_sjfj = w_sjfj;
    }

    public String getW_cjgzId() {
        return w_cjgzId;
    }

    public void setW_cjgzId(String w_cjgzId) {
        this.w_cjgzId = w_cjgzId;
    }

    public String getRepairnNmber() {
        return repairnNmber;
    }

    public void setRepairnNmber(String repairnNmber) {
        this.repairnNmber = repairnNmber;
    }

    public String getIsWarranty() {
        return isWarranty;
    }

    public void setIsWarranty(String isWarranty) {
        this.isWarranty = isWarranty;
    }

    public String getReturnTimes() {
        return returnTimes;
    }

    public void setReturnTimes(String returnTimes) {
        this.returnTimes = returnTimes;
    }

    public String getLastEngineer() {
        return lastEngineer;
    }

    public void setLastEngineer(String lastEngineer) {
        this.lastEngineer = lastEngineer;
    }

    public String getStrLastAccTime() {
        return strLastAccTime;
    }

    public void setStrLastAccTime(String strLastAccTime) {
        this.strLastAccTime = strLastAccTime;
    }

    public String getStrSalesTime() {
        return strSalesTime;
    }

    public void setStrSalesTime(String strSalesTime) {
        this.strSalesTime = strSalesTime;
    }

    public String getPayDelivery() {
        return payDelivery;
    }

    public void setPayDelivery(String payDelivery) {
        this.payDelivery = payDelivery;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAcceptRemark() {
        return acceptRemark;
    }

    public void setAcceptRemark(String acceptRemark) {
        this.acceptRemark = acceptRemark;
    }

    public String getRepairNumberRemark() {
        return repairNumberRemark;
    }

    public void setRepairNumberRemark(String repairNumberRemark) {
        this.repairNumberRemark = repairNumberRemark;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getUnNormalExpressCompany() {
        return unNormalExpressCompany;
    }

    public void setUnNormalExpressCompany(String unNormalExpressCompany) {
        this.unNormalExpressCompany = unNormalExpressCompany;
    }

    public String getUnNormalExpressNo() {
        return unNormalExpressNo;
    }

    public void setUnNormalExpressNo(String unNormalExpressNo) {
        this.unNormalExpressNo = unNormalExpressNo;
    }

    public String getSfVersion() {
        return sfVersion;
    }

    public void setSfVersion(String sfVersion) {
        this.sfVersion = sfVersion;
    }

    public String getInsideSoftModel() {
        return insideSoftModel;
    }

    public void setInsideSoftModel(String insideSoftModel) {
        this.insideSoftModel = insideSoftModel;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getOutCount() {
        return outCount;
    }

    public void setOutCount(String outCount) {
        this.outCount = outCount;
    }

    public String getTestMatStatus() {
        return testMatStatus;
    }

    public void setTestMatStatus(String testMatStatus) {
        this.testMatStatus = testMatStatus;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarketModel() {
        return marketModel;
    }

    public void setMarketModel(String marketModel) {
        this.marketModel = marketModel;
    }

    public String getInitheckFault() {
        return initheckFault;
    }

    public void setInitheckFault(String initheckFault) {
        this.initheckFault = initheckFault;
    }

    public String getSjfjname() {
        return sjfjname;
    }

    public void setSjfjname(String sjfjname) {
        this.sjfjname = sjfjname;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }
}