package com.example.smt_kanban_android.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smt_kanban_android.R;
import com.example.smt_kanban_android.adapter.UnloadComplateAdapter;
import com.example.smt_kanban_android.appliction.App;
import com.example.smt_kanban_android.bean.WorkOrderBean;
import com.example.smt_kanban_android.interfaceutrl.HttpUtrl;
import com.example.smt_kanban_android.util.HttpUtil;
import com.example.smt_kanban_android.util.ToastTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WorkOrderActivity extends BaseActivity {
    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case 1:
                    String string=(String)message.obj;
                    ToastTools.showShort(WorkOrderActivity.this,string);
                    break;
                case 2:
                    PageTextview.setText("第"+pageNumber+"页,共"+totalPage+"页,共"+ totalRow +"条" );
//            waiting.setVisibility(View.GONE);
                    if (pageNumber.equals(totalPage)){
//                        next.setBackgroundColor(R.drawable.jianbianhui);
                        next.setEnabled(false);
                    }else {
//                        next.setBackgroundColor(R.drawable.jianbianyuanjiao);
                        next.setEnabled(true);
                    }
                    if (pageNumber.equals("1")){
//                        previous.setBackgroundColor(R.drawable.jianbianhui);
                        previous.setEnabled(false);
                    }else {
//                        previous.setBackgroundColor(R.drawable.jianbianyuanjiao);
                        previous.setEnabled(true);
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    String WorkOrderActivity = "WorkOrderActivity";
    String[] linenumItems = {"","301", "302","303","304","305","306","307","308","309","310"};
    String[] statesItems ={"","未开始","进行中","已完成"};
    Calendar calendar= Calendar.getInstance(Locale.CHINA);
    TextView clear;
    TextView search;
    TextView over_time;
    TextView waiting;
    TextView PageTextview;
    TextView time;
    TextView previous;
    TextView next;
    Spinner linenum ;
    Spinner state ;
    EditText workerorder;
    EditText client;
    RecyclerView recycle;
    UnloadComplateAdapter adapter;
    List<WorkOrderBean> list = new ArrayList<>();

    String httpTOKEN;
    String httpline = "";
    String httpzhidan = "";
    String httpstate = "";
    String httpclient = "";
    static String httpstartTime = "";
    static String httpendTime = "";
    int pageNo;
    int pageSize = 20;


    String totalRow;
    String pageNumber;
    String totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_workerorder);
        pageNo = 1;
        initUI();
        initDate();

    }
    private void initDate(){
        httpclient = client.getText().toString();
        httpzhidan = workerorder.getText().toString();
        list.clear();
        Message message1 = new Message();
        message1.what=1;
        Log.d(WorkOrderActivity," 传递数据:line="+httpline+" pageNo="+pageNo+" httpzhidan="
                +httpzhidan+" httpstate="+httpstate+" httpstartTime="+httpstartTime+" httpclien="+httpclient+" httpendTime="+httpendTime);
        try {
            HttpUtil.sendOkHttpPostRequest(app.getBase_ip() + "config/selectZhidan", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(WorkOrderActivity, e.toString());
                            ToastTools.showShort(WorkOrderActivity.this,e.toString());
                            message1.obj="错误信息："+e.toString();
                            handler.sendMessage(message1);

                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String body = response.body().string();
                            Log.d(WorkOrderActivity, "获取列表数据=="+body);
                            JSONObject jsonObject = JSONObject.parseObject(body);
                            int result = Integer.parseInt(jsonObject.getString("code"));
                            if (result==200){
                                JSONObject dateObject = jsonObject.getJSONObject("data");
                                totalRow = dateObject.getString("totalRow");
                                pageNumber = dateObject.getString("pageNumber");
                                totalPage = dateObject.getString("totalPage");
                                JSONArray datalistArray = JSONArray.parseArray(dateObject.getString("list"));
                                for (int i=0;i<datalistArray.size();i++){
                                    WorkOrderBean bean = JSON.parseObject(datalistArray.get(i).toString(), WorkOrderBean.class);
                                    list.add(bean);
                                }
                                Message message2 = new Message();
                                message2.what=2;
                                handler.sendMessage(message2);
                            }else {
                                message1.obj="错误信息："+jsonObject.get("data");
                                handler.sendMessage(message1);
                                Log.d(WorkOrderActivity, String.valueOf(jsonObject.get("data")));
                            }
                        }
                    },"TOKEN",app.getTOKEN(),
                    "pageNo",String.valueOf(pageNo),
                    "pageSize",String.valueOf(pageSize),
                    "zhidan",httpzhidan,
                    "state",httpstate,
                    "client",httpclient,
                    "startTime",httpstartTime,
                    "endTime",httpendTime,
                    "line",httpline);
        }catch (Exception e) {
            Log.d(WorkOrderActivity, e.toString());
            message1.obj="错误信息："+e.toString();
            handler.sendMessage(message1);
        }
    }
    private void initUI(){
        totalPage = "1";
        pageNo = 1;
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        client = findViewById(R.id.client);
        PageTextview = findViewById(R.id.PageTextview);
        waiting = findViewById(R.id.waiting);
        clear = findViewById(R.id.clear);
        search = findViewById(R.id.search);
        over_time = findViewById(R.id.over_time);
        time = findViewById(R.id.time);
        state = findViewById(R.id.state);
        workerorder = findViewById(R.id.workerorder);
        recycle = findViewById(R.id.recycle);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        adapter = new UnloadComplateAdapter(list);
        recycle.setLayoutManager(layoutManager2);
        recycle.setAdapter(adapter);
        linenum =  findViewById(R.id.linenum);
        ArrayAdapter<String> adapterline = new ArrayAdapter<String>(this, R.layout.myspinner, linenumItems);
        adapterline.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        linenum.setAdapter(adapterline);
        linenum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                Log.d("sxmmm",linenumItems[pos]);
                httpline = linenumItems[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
            }
        });
        ArrayAdapter<String> stateadapter = new ArrayAdapter<String>(this, R.layout.myspinner, statesItems);
        stateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateadapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                Log.d("sxmmm",statesItems[pos]);
                httpstate = statesItems[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNo--;
                initDate();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNo++;
                initDate();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastTools.showShort(WorkOrderActivity.this,"请选择开始时间");
                showDatePickerDialog(WorkOrderActivity.this,  4, calendar,time);;
            }
        });
        over_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastTools.showShort(WorkOrderActivity.this,"请选择结束时间");
                showOverDatePickerDialog(WorkOrderActivity.this,  4, calendar,over_time);;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDate();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linenum.setSelection(0,true);
                state.setSelection(0,true);
                workerorder.setText("");
                client.setText("");
                time.setText("");
                over_time.setText("");
                httpTOKEN ="";
                httpline="";
                httpzhidan="";
                httpstate="";
                httpclient = "";
                httpstartTime = "";
                httpendTime = "";
            }
        });
    }
    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId , Calendar calendar,TextView tv) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//                tv.setText("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                tv.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                String monthstring = "";
                String daystring= "";
                if (monthOfYear+1<10){
                    monthstring = "0"+(monthOfYear+1);
                }else {
                    monthstring = ""+monthOfYear;
                }
                if (dayOfMonth<10){
                    daystring = "0"+dayOfMonth;
                }else {
                    daystring = ""+dayOfMonth;
                }
                httpstartTime = year+"-"+monthstring+"-" +daystring+"/00:00:01";
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void showOverDatePickerDialog(Activity activity, int themeResId , Calendar calendar,TextView tv) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                String monthstring = "";
                String daystring= "";
                if (monthOfYear+1<10){
                    monthstring = "0"+(monthOfYear+1);
                }else {
                    monthstring = ""+monthOfYear;
                }
                if (dayOfMonth<10){
                    daystring = "0"+dayOfMonth;
                }else {
                    daystring = ""+dayOfMonth;
                }
                httpendTime = year+"-"+monthstring+"-" +daystring+"/23:59:59";
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
