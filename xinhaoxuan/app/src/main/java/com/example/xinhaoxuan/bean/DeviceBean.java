package com.example.xinhaoxuan.bean;

public class DeviceBean {
    String id;
    String name;
    boolean available;
    boolean haschoosed =false;

    public boolean isHaschoosed() {
        return haschoosed;
    }

    public void setHaschoosed(boolean haschoosed) {
        this.haschoosed = haschoosed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
