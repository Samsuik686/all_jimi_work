package com.example.xinhaoxuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.xinhaoxuan.R;
import com.example.xinhaoxuan.bean.DeviceBean;
import com.example.xinhaoxuan.bean.ShelvesBean;
import com.example.xinhaoxuan.interfaceutrl.HttpUtrl;
import com.example.xinhaoxuan.tools.LogTools;
import com.example.xinhaoxuan.tools.OkhttpTools;
import com.example.xinhaoxuan.tools.ToastTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoadingActivity extends BaseActivity {
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            String string = (String) message.obj;
            switch (message.what) {
                case -6:
                    initNumberPicker(2);
                    break;
                case -5:
                    ToastTools.showShort(LoadingActivity.this, "当前没用可用装载位");
                    break;
                case -4:

                    initNumberPicker(1);
//                    number_picker_layout.setVisibility(View.VISIBLE);
                    break;

                case 1:
                    ToastTools.showShort(LoadingActivity.this, string);
                    break;
                case 3:
                    number_picker_layout.setVisibility(View.GONE);
                    if (ActionType == 1) {
                        location_number.setText("装载位：" + PositionName + "正在呼叫货架");
                    } else if (ActionType == 2) {
                        location_number.setText("转载位：" + PositionName + "正在搬运回库");
                    }
                    ToastTools.showShort(LoadingActivity.this, string);
                    break;
            }
        }
    };
    List<DeviceBean> CallDevicelist = new ArrayList<>();
    List<DeviceBean> MoveDevicelist = new ArrayList<>();
    List<DeviceBean> list = new ArrayList<>();
    String ACTIVITYNAME = "LoadingActivity";
    ImageView back;
    TextView call_huojia;
    TextView backhuiku;
    TextView over;
    RelativeLayout number_picker_layout;
    NumberPicker testnumberpicker;
    TextView canncal;
    TextView sure;
    TextView location_number;
    int ActionType;
    String loadPosition;
    String PositionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        initData();
        initUI();
        initAction();
    }

    void initData() {
        CallDevicelist.clear();
        Message message1 = new Message();
        message1.what = 1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "load/listAllEmptyLoadPositions", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(ACTIVITYNAME, "获取所有空装载位" + e.toString());
                    message1.obj = "获取所有空装载位" + e.toString();
                    handler.sendMessage(message1);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(ACTIVITYNAME, "获取所有空装载位==" + body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result == 200) {
                        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("data"));
                        for (int i = 0; i < jsonArray.size(); i++) {
                            DeviceBean bean = JSON.parseObject(jsonArray.get(i).toString(), DeviceBean.class);
                            if (bean.isAvailable() == true) {
                                CallDevicelist.add(bean);
                            }

                        }

                        Message message1 = new Message();
                        if (CallDevicelist.size() > 0) {
                            message1.what = -4;
                        } else {
                            message1.what = -5;
                        }
                        handler.sendMessage(message1);
                    } else {
                        message1.obj = "获取所有空装载位：" + jsonObject.get("data");
                        handler.sendMessage(message1);

                    }
                }
            }, "#TOKEN#", app.getTOKEN());
        } catch (Exception ex) {
            message1.obj = "获取所有空装载位：" + ex.toString();
            handler.sendMessage(message1);
        }
    }

    void initData2() {
        MoveDevicelist.clear();
        Message message2 = new Message();
        message2.what = 1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "load/listAllNotEmptyLoadPositions", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(ACTIVITYNAME, "获取所有非空装载位" + e.toString());
                    message2.obj = "获取所有非空装载位" + e.toString();
                    handler.sendMessage(message2);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(ACTIVITYNAME, "获取所有非空装载位==" + body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result == 200) {
                        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("data"));
                        for (int i = 0; i < jsonArray.size(); i++) {
                            DeviceBean bean = JSON.parseObject(jsonArray.get(i).toString(), DeviceBean.class);
                            if (bean.isAvailable() == true) {
                                MoveDevicelist.add(bean);
                            }
                        }

                        Message message1 = new Message();
                        if (MoveDevicelist.size() > 0) {
                            message1.what = -6;
                        } else {
                            message1.what = -5;
                        }
                        handler.sendMessage(message1);
                    } else {
                        message2.obj = "获取所有非空装载位" + jsonObject.get("data");
                        handler.sendMessage(message2);

                    }
                }
            }, "#TOKEN#", app.getTOKEN());
        } catch (Exception ex) {
            message2.obj = "获取所有非空装载位：" + ex.toString();
            handler.sendMessage(message2);
        }

    }

    void initUI() {
        back = findViewById(R.id.back);
        call_huojia = findViewById(R.id.call_huojia);
        backhuiku = findViewById(R.id.backhuiku);
        over = findViewById(R.id.over);
        number_picker_layout = findViewById(R.id.number_picker_layout);
        testnumberpicker = findViewById(R.id.testnumberpicker);
        canncal = findViewById(R.id.canncal);
        sure = findViewById(R.id.sure);
        location_number = findViewById(R.id.location_number);
    }

    private void initNumberPicker(int type) {

        try{

            if (type == 1) {
                list = CallDevicelist;
            } else  {
                list = MoveDevicelist;
            }
            String[] numbers = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                numbers[i] = list.get(i).getName();
            }
            loadPosition = list.get(0).getId();
            PositionName = list.get(0).getName();

//            testnumberpicker.setDisplayedValues(null);
//            testnumberpicker.setDisplayedValues(numbers);
//            testnumberpicker.setMinValue(0);
//            testnumberpicker.setMaxValue(numbers.length - 1);
//            testnumberpicker.setValue(0);

            testnumberpicker.setDisplayedValues(null);
            testnumberpicker.setMinValue(0);
            testnumberpicker.setMaxValue(numbers.length-1);
            testnumberpicker.setValue(0);
            testnumberpicker.setDisplayedValues(numbers);
            testnumberpicker.setWrapSelectorWheel(false);

//            String[] city = {"立水桥", "霍营", "回龙观", "龙泽", "西二旗", "上地"};
//            testnumberpicker.setDisplayedValues(city);
//            testnumberpicker.setMinValue(0);
//            testnumberpicker.setMaxValue(city.length - 1);
//            testnumberpicker.setValue(0);

            testnumberpicker.setWrapSelectorWheel(false);
            testnumberpicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            testnumberpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                //当NunberPicker的值发生改变时，将会激发该方法
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    LogTools.d(ACTIVITYNAME, "oldVal=" + oldVal + " newVal:" + newVal);
                    loadPosition = list.get(newVal).getId();
                    PositionName = list.get(newVal).getName();
                }
            });
            number_picker_layout.setVisibility(View.VISIBLE);
        }catch (Exception ex){
            Message message2 = new Message();
            message2.what = 3;
            message2.obj = ex.toString();
            handler.sendMessage(message2);
        }


    }

    void initAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        call_huojia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionType = 1;
                initData();

            }
        });
        backhuiku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionType = 2;
                initData2();


            }
        });
        canncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_picker_layout.setVisibility(View.GONE);

            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeAction();
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Over();
            }
        });
    }

    String alartString = "";

    private void TakeAction() {
        String bottom_url = "";
        if (ActionType == 1) {
            bottom_url = "load/callEmptyShelves";
            alartString = "呼叫空货架到装载位";
        } else if (ActionType == 2) {
            bottom_url = "load/sendShelvesBack";
            alartString = "送回装载位的货架";
        }
        Message message1 = new Message();
        message1.what = 1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + bottom_url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(ACTIVITYNAME, alartString + e.toString());
                            message1.obj = alartString + e.toString();
                            handler.sendMessage(message1);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String body = response.body().string();
                            Log.d(ACTIVITYNAME, alartString + "==" + body);
                            JSONObject jsonObject = JSONObject.parseObject(body);
                            int result = Integer.parseInt(jsonObject.getString("code"));
                            if (result == 200) {
                                Message message2 = new Message();
                                message2.what = 3;
                                message2.obj = jsonObject.get("data");
                                handler.sendMessage(message2);
                            } else {
                                message1.obj = alartString + "：" + jsonObject.get("data");
                                handler.sendMessage(message1);

                            }
                        }
                    }, "#TOKEN#", app.getTOKEN(),
                    "loadPosition", loadPosition);
        } catch (Exception ex) {
            message1.obj = alartString + "：" + ex.toString();
            handler.sendMessage(message1);
        }
    }

    private void Over() {
        Message message1 = new Message();
        message1.what = 1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "load/finish", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(ACTIVITYNAME, e.toString());
                    message1.obj = e.toString();
                    handler.sendMessage(message1);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(ACTIVITYNAME, "结束装载区工作==" + body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result == 200) {
                        message1.obj = jsonObject.get("data");
                        handler.sendMessage(message1);
                    } else {
                        message1.obj = "结束装载区工作：" + jsonObject.get("data");
                        handler.sendMessage(message1);

                    }
                }
            }, "#TOKEN#", app.getTOKEN());
        } catch (Exception ex) {

            message1.obj = "结束装载区工作：" + ex.toString();
            handler.sendMessage(message1);
        }
    }
}
