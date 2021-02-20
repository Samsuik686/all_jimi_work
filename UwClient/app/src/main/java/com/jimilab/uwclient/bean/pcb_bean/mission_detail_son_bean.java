package com.jimilab.uwclient.bean.pcb_bean;

public class mission_detail_son_bean {

    String fatherid;
    String number;
    String materialId;
    String where;
    String cycle;

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getFatherid() {
        return fatherid;
    }

    public void setFatherid(String fatherid) {
        this.fatherid = fatherid;
    }

    public mission_detail_son_bean(String fatherid, String materialId, String number,String where,String cycle) {
        this.fatherid = fatherid;
        this.number = number;
        this.materialId = materialId;
        this.where = where;
        this.cycle = cycle;
    }
}
