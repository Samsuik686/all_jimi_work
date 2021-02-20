package com.jimilab.uwclient.bean.pcb_bean;

public class Pcbcompany_bean {
    String companyCode;
    String nickname;
    String name;
    String id;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Pcbcompany_bean(String companyCode, String nickname, String name, String id) {
        this.companyCode = companyCode;
        this.nickname = nickname;
        this.name = name;
        this.id = id;
    }

    public Pcbcompany_bean() {
    }
}
