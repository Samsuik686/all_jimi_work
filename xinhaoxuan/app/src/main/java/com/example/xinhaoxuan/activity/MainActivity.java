package com.example.xinhaoxuan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.xinhaoxuan.R;
import com.example.xinhaoxuan.appliction.App;
import com.example.xinhaoxuan.interfaceutrl.HttpUtrl;
import com.example.xinhaoxuan.tools.OkhttpTools;
import com.example.xinhaoxuan.tools.ToastTools;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case 1:
                    String string=(String)message.obj;
                    ToastTools.showShort(MainActivity.this,string);
                    break;
            }
        }
    };
    String MAINACTIVITY = "MainActivity";
    TextView Login;
    EditText userid;
    EditText passwords;
    String id;
    String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.login);
        userid = findViewById(R.id.userid);
        passwords = findViewById(R.id.passwords);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = userid.getText().toString();
                pwd = passwords.getText().toString();
                if (id.equals("")==true && pwd.equals("")==true){
                    ToastTools.showShort(MainActivity.this,"账号和用户名不能为空");
                }else {
                    Login();
                }

            }
        });
    }



    private void Login(){
        Message message1 = new Message();
        message1.what=1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "user/login", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(MAINACTIVITY, e.toString());
                    message1.obj=e.toString();
                    handler.sendMessage(message1);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(MAINACTIVITY, "登录返回数据=="+body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result==200){
                        Log.d(MAINACTIVITY, "TOKEN=="+jsonObject.getString("data"));
                        app.setTOKEN(jsonObject.getString("data"));
                        Intent intent = new Intent(MainActivity.this,ChooseActivity.class);
                        startActivity(intent);
                    }else {
                        message1.obj="错误信息："+jsonObject.get("data");
                        handler.sendMessage(message1);

                    }
                }
            },"user",id,
                    "password",pwd);
        }catch (Exception ex){
            Log.d(MAINACTIVITY,ex.toString());
            message1.obj="错误信息："+ex.toString();
            handler.sendMessage(message1);
        }

    }
}