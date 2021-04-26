package com.example.smt_kanban_android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.smt_kanban_android.R;
import com.example.smt_kanban_android.appliction.App;
import com.example.smt_kanban_android.interfaceutrl.HttpUtrl;
import com.example.smt_kanban_android.util.HttpUtil;
import com.example.smt_kanban_android.util.ToastTools;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity{
    String LoginActivity = "LoginActivity";
    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case 1:
                    String string=(String)message.obj;
                    ToastTools.showShort(LoginActivity.this,string);
                    break;
            }
        }
    };
    EditText userid;
    EditText passwords;
    TextView change_id;
    TextView login;
    String id;
    String pwd;
    MyTask myTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userid = findViewById(R.id.userid);
        passwords = findViewById(R.id.passwords);
        login = findViewById(R.id.login);
        change_id = findViewById(R.id.change_id);
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        String Id=sharedPreferences.getString("userid","");
        String pswords=sharedPreferences.getString("passwords","");

        String ip=sharedPreferences.getString("ip","http://120.234.224.186:28881/");
        app.setBase_ip(ip);

        userid.setText(Id);
        passwords.setText(pswords);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = userid.getText().toString();
                pwd = passwords.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                //步骤3：将获取过来的值放入文件
                editor.putString("userid",id);
                editor.putString("passwords",pwd);
                //步骤4：提交
                editor.commit();
                login.setEnabled(false);
                login.setText("正在登陆，请稍后");
                myTask = (MyTask) new MyTask().execute();
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });
        change_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ChangeipActivity.class);
                startActivity(intent);
            }
        });
    }
    private class MyTask extends AsyncTask<String, Integer, String> {
        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {

            // 执行前显示提示
        }
        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected String doInBackground(String... params) {
            Message message1 = new Message();
            message1.what=1;
            try {
                HttpUtil.sendOkHttpPostRequest(app.getBase_ip() + "user/login", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(LoginActivity, e.toString());
                            message1.obj="错误信息："+e.toString();
                            handler.sendMessage(message1);

                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String body = response.body().string();
                            Log.d(LoginActivity, "登录返回数据=="+body);
                            JSONObject jsonObject = JSONObject.parseObject(body);
                            int result = Integer.parseInt(jsonObject.getString("code"));
                            if (result==200){
                                Log.d(LoginActivity, "TOKEN=="+jsonObject.getString("data"));
//                               JSONObject tokenObject = JSONObject.parseObject(jsonObject.getString("data"));
                                JSONObject tokenObject = jsonObject.getJSONObject("data");
                                app.setTOKEN(tokenObject.getString("TOKEN"));
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else {
                                message1.obj="错误信息："+jsonObject.get("data");
                                handler.sendMessage(message1);
                                Log.d(LoginActivity, String.valueOf(jsonObject.get("data")));
                            }
                        }
                    },"user",id,
                    "password",pwd);
            }catch (Exception e) {
                Log.d(LoginActivity, e.toString());
                message1.obj="错误信息："+e.toString();
                handler.sendMessage(message1);
            }
            return null;
        }

        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        @Override
        protected void onProgressUpdate(Integer... progresses) {
        }
        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(String result) {
            // 执行完毕后，则更新UI
            login.setEnabled(true);
            login.setText("登陆");
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {
        }
    }

}