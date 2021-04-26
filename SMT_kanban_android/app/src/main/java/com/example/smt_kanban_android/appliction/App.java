package com.example.smt_kanban_android.appliction;

import android.app.Application;

public class App extends Application {

    private static String base_ip;

    public static String getBase_ip() {
        return base_ip;
    }

    public static void setBase_ip(String base_ip) {
        App.base_ip = base_ip;
    }

    private static String TOKEN;

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        App.TOKEN = TOKEN;
    }


}
