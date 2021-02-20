package com.jimilab.uwclient.bean.pcb_bean;

public class mission_bean {
    String supplierName;
    String fileName;
    String createTimeString;
    String supplier;
    String typeString;
    String id;
    String state;
    String stateString;
    String priority;
    String type;
    String remarks;
    String status;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateString() {
        return stateString;
    }

    public void setStateString(String stateString) {
        this.stateString = stateString;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public mission_bean(String supplierName, String fileName, String createTimeString, String supplier, String typeString, String id, String state, String stateString, String priority, String type, String remarks, String status) {
        this.supplierName = supplierName;
        this.fileName = fileName;
        this.createTimeString = createTimeString;
        this.supplier = supplier;
        this.typeString = typeString;
        this.id = id;
        this.state = state;
        this.stateString = stateString;
        this.priority = priority;
        this.type = type;
        this.remarks = remarks;
        this.status = status;
    }

    public mission_bean() {
    }
}
