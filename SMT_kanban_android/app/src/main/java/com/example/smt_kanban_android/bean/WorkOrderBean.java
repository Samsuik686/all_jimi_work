package com.example.smt_kanban_android.bean;

public class WorkOrderBean {
    String line;
    String nest_quantity;
    String client;
    String already_product;
    String defects_number;
    String consecutive_number;
    String id;
    String defects_rate;
    String state;
    String zhidan;
    String machine_name;
    String standard_product;
//
//    public WorkOrderBean(String line, String nest_quantity, String client, String already_product, String defects_number, String consecutive_number, String id, String defects_rate, String state, String zhidan, String machine_name, String standard_product) {
//        this.line = line;
//        this.nest_quantity = nest_quantity;
//        this.client = client;
//        this.already_product = already_product;
//        this.defects_number = defects_number;
//        this.consecutive_number = consecutive_number;
//        this.id = id;
//        this.defects_rate = defects_rate;
//        this.state = state;
//        this.zhidan = zhidan;
//        this.machine_name = machine_name;
//        this.standard_product = standard_product;
//    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getNest_quantity() {
        return nest_quantity;
    }

    public void setNest_quantity(String nest_quantity) {
        this.nest_quantity = nest_quantity;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAlready_product() {
        return already_product;
    }

    public void setAlready_product(String already_product) {
        this.already_product = already_product;
    }

    public String getDefects_number() {
        return defects_number;
    }

    public void setDefects_number(String defects_number) {
        this.defects_number = defects_number;
    }

    public String getConsecutive_number() {
        return consecutive_number;
    }

    public void setConsecutive_number(String consecutive_number) {
        this.consecutive_number = consecutive_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefects_rate() {
        return defects_rate;
    }

    public void setDefects_rate(String defects_rate) {
        this.defects_rate = defects_rate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZhidan() {
        return zhidan;
    }

    public void setZhidan(String zhidan) {
        this.zhidan = zhidan;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public String getStandard_product() {
        return standard_product;
    }

    public void setStandard_product(String standard_product) {
        this.standard_product = standard_product;
    }
}
