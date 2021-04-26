package com.example.smt_kanban_android.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.smt_kanban_android.appliction.App;
import com.example.smt_kanban_android.bean.WorkOrderBean;
import com.example.smt_kanban_android.interfaceutrl.HttpUtrl;
import com.example.smt_kanban_android.util.EchartOptionUtil;
import com.example.smt_kanban_android.util.HttpUtil;
import com.example.smt_kanban_android.util.ToastTools;
import com.example.smt_kanban_android.view.EchartView;
import com.example.smt_kanban_android.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class KanBanActivity extends BaseActivity {
    String KanBanActivity = "KanBanActivity";
    String[] linenumItems = {"","301", "302", "303", "304", "305", "306", "307", "308", "309", "310"};
    Spinner chooseline;
    Object[] x;
    Object[] y;
    Object[] x1;
    Object[] y1;
    ImageView reflesh;
    EchartView lineChart;
    EchartView lineChart2;
    EditText workerorder;
    EditText client;

    TextView search;
    TextView cannel;
    TextView click_time;

    String httpline;
    String httpzhidan = "";
    String httpclient = "";
    String httptime = "";

    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    TextView zhidan_view;
    TextView machine_name_view;
    TextView nest_quantity_view;
    TextView client_http_view;
    TextView start_time_view;
    TextView bad_record1_view;
    TextView bad_record2_view;
    TextView bad_record3_view;
    TextView frequency_view;

    TextView shife_leader_view;
    ImageView shife_leader_Pic_view;

    TextView line_enginner_view;
    ImageView line_enginner_Pic_view;

    TextView IPQC_view;
    ImageView IPQC_Pic_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        httpline = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanban);
        initUI();
        initEchartsDate();
        getdatehttpdate();



    }

    private void initUI() {
        zhidan_view= findViewById(R.id.zhidan_view);
        machine_name_view= findViewById(R.id.machine_name_view);
        nest_quantity_view= findViewById(R.id.nest_quantity_view);
        client_http_view= findViewById(R.id.client_http_view);
        start_time_view= findViewById(R.id.start_time_view);

        bad_record1_view= findViewById(R.id.bad_record1_view);
        bad_record2_view= findViewById(R.id.bad_record2_view);
        bad_record3_view= findViewById(R.id.bad_record3_view);

        frequency_view= findViewById(R.id.frequency_view);
        line_enginner_view= findViewById(R.id.line_enginner_view);
        line_enginner_Pic_view= findViewById(R.id.line_enginner_Pic_view);
        shife_leader_view= findViewById(R.id.shife_leader);
        shife_leader_Pic_view= findViewById(R.id.shife_leader_Pic_view);
        IPQC_view= findViewById(R.id.IPQC_view);
        IPQC_Pic_view= findViewById(R.id.IPQC_Pic_view);

        click_time = findViewById(R.id.click_time);
        search = findViewById(R.id.search);
        cannel = findViewById(R.id.cannel);
        client = findViewById(R.id.client);
        workerorder = findViewById(R.id.workerorder);
        reflesh = findViewById(R.id.reflesh);
        lineChart = findViewById(R.id.lineChart);
        chooseline = findViewById(R.id.chooseline);
        lineChart2 = findViewById(R.id.lineChart2);
        ArrayAdapter<String> adapterline = new ArrayAdapter<String>(this, R.layout.myspinner, linenumItems);
        adapterline.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseline.setAdapter(adapterline);
        chooseline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // TODO
                Log.d("sxmmm", linenumItems[pos]);
                httpline = linenumItems[pos];
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
            }
        });
        cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpline = "";
                httpzhidan = "";
                httpclient = "";
                httptime = "";
                workerorder.setText("");
                client.setText("");
                click_time.setText("选择日期时间");
                chooseline.setSelection(0,true);
                choosetimetxt = "";
            }
        });
        reflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shereprence 存下当前线号然后重新刷新页面，因为webview有时候因为系统运算或者内存问题不加载，刷新下就好
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
            }
        });
        click_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(KanBanActivity.this, 4, calendar,handler);
                ;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initEchartsDate();
                getdatehttpdate();


            }
        });
    }

    String zhidan = "";
    String machine_name = "";
    String nest_quantity = "";
    String client_http = "";
    String start_time = "";


    String bad_record1 = "";
    String bad_record2 = "";
    String bad_record3 = "";

    String frequency = "";
    String shife_leader = "";
    String shife_leader_Pic = "";
    String line_enginner = "";
    String line_enginner_Pic = "";
    String IPQC = "";
    String IPQC_Pic = "";


    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    String string = (String) message.obj;
                    ToastTools.showShort(KanBanActivity.this, string);
                    Log.d("sxmmm","走了case1");

                    refreshLineChart();
                    refreshLineChart2();
                    zhidan_view.setText("工单："+zhidan);
                    machine_name_view.setText("机型："+ machine_name);
                    nest_quantity_view.setText("套料数："+nest_quantity);
                    client_http_view.setText("客户："+ client_http);
                    start_time_view.setText("开始时间："+start_time);

                    bad_record1_view.setText(bad_record1);
                    bad_record2_view.setText(bad_record2);
                    bad_record3_view.setText(bad_record3);

                    frequency_view.setText(frequency);
                    line_enginner_view.setText(line_enginner);
                    Glide.with(KanBanActivity.this)
                            .load(line_enginner_Pic)
                            .into(line_enginner_Pic_view);

                    shife_leader_view.setText(shife_leader);
                    Glide.with(KanBanActivity.this)
                            .load(shife_leader_Pic)
                            .into(shife_leader_Pic_view);
                    IPQC_view.setText(IPQC);
                    Glide.with(KanBanActivity.this)
                            .load(IPQC_Pic)
                            .into(IPQC_Pic_view);

                    refreshLineChart();
                    refreshLineChart2();
                    break;
                case 2:
                    Log.d("sxmmm","走了case2");
                    refreshLineChart();
                    refreshLineChart2();
                    zhidan_view.setText("工单："+zhidan);
                    machine_name_view.setText("机型："+ machine_name);
                    nest_quantity_view.setText("套料数："+nest_quantity);
                    client_http_view.setText("客户："+ client_http);
                    start_time_view.setText("开始时间："+start_time);

                    bad_record1_view.setText(bad_record1);
                    bad_record2_view.setText(bad_record2);
                    bad_record3_view.setText(bad_record3);

                    frequency_view.setText(frequency);
                    line_enginner_view.setText(line_enginner);
                    Glide.with(KanBanActivity.this)
                            .load(line_enginner_Pic)
                            .into(line_enginner_Pic_view);

                    shife_leader_view.setText(shife_leader);
                    Glide.with(KanBanActivity.this)
                            .load(shife_leader_Pic)
                            .into(shife_leader_Pic_view);
                    IPQC_view.setText(IPQC);
                    Glide.with(KanBanActivity.this)
                            .load(IPQC_Pic)
                            .into(IPQC_Pic_view);

                    refreshLineChart();
                    refreshLineChart2();
                    break;
                case 3:
                    showTimePickerDialog(KanBanActivity.this,2, click_time,calendar);
                    break;
                case 4:
                    Intent intent = new Intent(KanBanActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    double yAxis1 = 0;
    double yAxis2 = 0;

    void getdatehttpdate() {
        httpzhidan = workerorder.getText().toString();
        httpclient = client.getText().toString();
        httptime = choosetimetxt;
        if(httpzhidan.equals("")==false && httptime.equals("")==true){
            ToastTools.showShort(KanBanActivity.this, "选择订单号必须选择时间");
        }else {
            standard_product = "0";
            defects_rate = "0";
            Message message1 = new Message();
            message1.what = 1;
            zhidan =  "";
            machine_name =  "";
            nest_quantity =  "";
            client_http =  "";
            start_time =  "";
            bad_record1 =  "";
            bad_record2 = "";
            bad_record3 =  "";
            frequency =  "";
            line_enginner =  "";
            line_enginner_Pic =  "";
            shife_leader =  "";
            shife_leader_Pic =  "";
            IPQC =  "";
            IPQC_Pic =  "";
            x = new Object[0];
            y1 = new Object[0];
            y = new Object[0];
            yAxis1 = 0;
            yAxis2 = 0;
            Log.d(KanBanActivity, " 传递数据:line=" + httpline + " httptime=" + httptime + " httpclien=" + httpclient + " httpzhidan=" + httpzhidan);
            try {
                HttpUtil.sendOkHttpPostRequest(app.getBase_ip() + "board/getRealtimeDate", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(KanBanActivity, e.toString());
                                ToastTools.showShort(KanBanActivity.this, e.toString());
                                message1.obj = "错误信息：" + e.toString();
                                handler.sendMessage(message1);

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String body = response.body().string();
                                Log.d(KanBanActivity, "获取列表数据==" + body);
                                JSONObject jsonObject = JSONObject.parseObject(body);
                                int result = Integer.parseInt(jsonObject.getString("code"));
                                if (result == 200) {
                                    JSONObject dateObject = jsonObject.getJSONObject("data");
                                    zhidan = dateObject.getString("zhidan");
                                    machine_name = dateObject.getString("machine_name");
                                    nest_quantity = dateObject.getString("nest_quantity");
                                    client_http = dateObject.getString("client");
                                    start_time = dateObject.getString("startTime");
                                    bad_record1 = dateObject.getString("bad_record1");
                                    bad_record2 = dateObject.getString("bad_record2");
                                    bad_record3 = dateObject.getString("bad_record3");
                                    frequency = dateObject.getString("frequency");
                                    line_enginner = dateObject.getString("line_enginner");
                                    line_enginner_Pic = dateObject.getString("line_enginner_Pic");
                                    shife_leader = dateObject.getString("shife_leader");
                                    shife_leader_Pic = dateObject.getString("shife_leader_Pic");
                                    IPQC = dateObject.getString("IPQC");
                                    IPQC_Pic = dateObject.getString("IPQC_Pic");
                                    standard_product =  dateObject.getString("standard_product");
                                    defects_rate =  dateObject.getString("defects_rate");
                                    JSONArray datalistArray = JSONArray.parseArray(dateObject.getString("realTimedate"));
                                    x = new Object[datalistArray.size()];
                                    y1 = new Object[datalistArray.size()];
                                    y = new Object[datalistArray.size()];
                                    for (int i = 0; i < datalistArray.size(); i++) {
                                        JSONObject itemObject = datalistArray.getJSONObject(i);
                                        x[i] = itemObject.getString("dateTime");
                                        y1[i] = itemObject.getString("rate");
                                        y[i] = itemObject.getString("num");
                                        if (Double.parseDouble(String.valueOf( y[i]))>yAxis1){
                                            yAxis1 = Double.parseDouble(String.valueOf( y[i]));
                                        }
                                        if (Double.parseDouble(String.valueOf( y1[i]))>yAxis2){
                                            yAxis2 = Double.parseDouble(String.valueOf( y1[i]));
                                        }
                                    }
                                    if (Double.parseDouble(standard_product)>yAxis1){
                                        yAxis1 = Double.parseDouble(standard_product);
                                    }
                                    if (Double.parseDouble(defects_rate)>yAxis2){
                                        yAxis2 = Double.parseDouble(String.valueOf( defects_rate));
                                    }

                                    Log.d(KanBanActivity, "yAxis==" + yAxis1 +"   "+yAxis2);
                                    Message message2 = new Message();
                                    message2.what = 2;
                                    handler.sendMessage(message2);
                                }
                                else if (result == 211) {
                                    Message message2 = new Message();
                                    message2.what = 4;
                                    handler.sendMessage(message2);
                                }else {
                                    message1.obj = "错误信息：" + jsonObject.get("data");
                                    handler.sendMessage(message1);
                                    Log.d(KanBanActivity, String.valueOf(jsonObject.get("data")));
                                }
                            }
                        }, "TOKEN", app.getTOKEN(),
                        "client", httpclient,
                        "dateTime", httptime,
                        "zhidan", httpzhidan,
                        "line", httpline
                );
            } catch (Exception e) {
                Log.d(KanBanActivity, e.toString());
                message1.obj = "错误信息：" + e.toString();
                handler.sendMessage(message1);
            }
        }

    }
    private void initEchartsDate() {
        lineChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshLineChart();
            }
        });

        lineChart2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshLineChart2();
            }
        });
    }

    String standard_product ="0";
    String defects_rate = "0";


    private void refreshLineChart() {

        lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y,standard_product,yAxis1));
    }
//private void refreshLineChart(){
//    Log.d("sxmmm","走了refreshLineChart");
//    Object[] x = new Object[]{
//            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
//    };
//    Object[] y = new Object[]{
//            820, 932, 901, 934, 1290, 1330, 1320
//    };
//    lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
//}

    private void refreshLineChart2() {

        lineChart2.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions2(x, y1,defects_rate,yAxis2));
    }
    static String choosedatetxt = "";
    static  String choosetimetxt = "";

    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, Calendar calendar, Handler handler ) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//                tv.setText("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                String monthstring = "";
                String daystring = "";
                if (monthOfYear + 1 < 10) {
                    monthstring = "0" + (monthOfYear + 1);
                } else {
                    monthstring = "" + monthOfYear;
                }
                if (dayOfMonth < 10) {
                    daystring = "0" + dayOfMonth;
                } else {
                    daystring = "" + dayOfMonth;
                }
                choosedatetxt = year+"-"+monthstring+"-" +daystring;
                Message message2 = new Message();
                message2.what = 3;
                handler.sendMessage(message2);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog(activity, themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourOfDaystr= "";
                        String minuteOfDaystr= "";
                        if (hourOfDay  < 10) {
                            hourOfDaystr = "0" + hourOfDay ;
                        } else {
                            hourOfDaystr = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            minuteOfDaystr = "0" + minute;
                        } else {
                            minuteOfDaystr = "" + minute;
                        }
                        tv.setText(choosedatetxt+'/' + hourOfDaystr + ":" + minuteOfDaystr  + ":"+"00");
                        choosetimetxt = choosedatetxt+'/' + hourOfDaystr + ":" + minuteOfDaystr  + ":"+"00";
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                , true).show();
    }


}