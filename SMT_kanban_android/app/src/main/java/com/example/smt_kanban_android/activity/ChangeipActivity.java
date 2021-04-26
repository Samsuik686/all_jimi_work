package com.example.smt_kanban_android.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smt_kanban_android.R;
import com.example.smt_kanban_android.appliction.App;

public class ChangeipActivity extends BaseActivity{
    TextView toast;
    EditText ipchange;
    TextView sure;
    TextView cansal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeip);
        toast = findViewById(R.id.toast);
        ipchange = findViewById(R.id.ipchange);
        sure = findViewById(R.id.sure);
        cansal = findViewById(R.id.cansal);
        ipchange.setText(app.getBase_ip());
        toast.setText("默认ip为“http://120.234.224.186:28881/”"+
                ",当前ip为：“"+ app.getBase_ip()+"”"+
                ",若修改ip请输入正确格式");
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("ip",ipchange.getText().toString());
                app.setBase_ip(ipchange.getText().toString());
                finish();
            }
        });
        cansal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
