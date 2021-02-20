package com.example.smt_kanban_android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smt_kanban_android.R;
import com.google.gson.internal.$Gson$Preconditions;

public class MainActivity extends BaseActivity{
    TextView esc;
    TextView button_to_kanban;
    TextView button_to_workorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        esc = findViewById(R.id.esc);
        button_to_workorder = findViewById(R.id.button_to_workorder);
        button_to_kanban = findViewById(R.id.button_to_kanban);
        button_to_kanban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,KanBanActivity.class);
                startActivity(intent);
            }
        });

        button_to_workorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WorkOrderActivity.class);
                startActivity(intent);
            }
        });
        esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("")
                        .setMessage("是否退出")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        .create();
                alertDialog2.show();
            }
        });
    }


}
