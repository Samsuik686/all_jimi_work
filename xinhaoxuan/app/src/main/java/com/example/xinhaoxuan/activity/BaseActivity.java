package com.example.xinhaoxuan.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xinhaoxuan.appliction.App;

public class BaseActivity extends AppCompatActivity {
    protected App app;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();

    }
}
