package com.example.xinhaoxuan.appliction;

import android.app.Application;

public class App extends Application {
    private static String TOKEN;

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        App.TOKEN = TOKEN;
    }
}
