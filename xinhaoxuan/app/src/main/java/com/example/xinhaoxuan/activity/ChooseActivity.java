package com.example.xinhaoxuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xinhaoxuan.R;

public class ChooseActivity  extends BaseActivity {
    TextView LoadingButton;
    TextView UnLoadingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_unloading);
        LoadingButton = findViewById(R.id.loading);
        UnLoadingButton = findViewById(R.id.unloading);
        LoadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,LoadingActivity.class);
                startActivity(intent);
            }
        });
        UnLoadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,UnloadingActivity.class);
                startActivity(intent);
            }
        });
    }
}
