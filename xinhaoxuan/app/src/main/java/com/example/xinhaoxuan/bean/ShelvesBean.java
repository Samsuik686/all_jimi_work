package com.example.xinhaoxuan.bean;

public class ShelvesBean {
    //{ "id": 1, "name":"1", "type": "unload",//unload是卸载位，store是存储位 "available": false }
    String id;
    String name;
    String type;
    boolean available;

    boolean haschoosed;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
