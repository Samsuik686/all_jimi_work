package com.jimilab.uwclient.bean.pcb_bean;

public class supplier_bean {
    String companyName;
    String enabledString;
    String name;
    String id;
    String enabled;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEnabledString() {
        return enabledString;
    }

    public void setEnabledString(String enabledString) {
        this.enabledString = enabledString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public supplier_bean() {
    }

    public supplier_bean(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
