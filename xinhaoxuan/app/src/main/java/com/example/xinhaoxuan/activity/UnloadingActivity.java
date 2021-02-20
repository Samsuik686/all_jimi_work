package com.example.xinhaoxuan.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.xinhaoxuan.R;
import com.example.xinhaoxuan.adapter.ChooseShelvesAdapter;
import com.example.xinhaoxuan.adapter.UnloadComplateAdapter;
import com.example.xinhaoxuan.bean.DeviceBean;
import com.example.xinhaoxuan.bean.ShelvesBean;
import com.example.xinhaoxuan.interfaceutrl.HttpUtrl;
import com.example.xinhaoxuan.tools.OkhttpTools;
import com.example.xinhaoxuan.tools.ToastTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UnloadingActivity extends BaseActivity {
    String  ACTIVITYNAME = "UnloadingActivity";
    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            String string=(String)message.obj;
            switch (message.what){
                case -2:
                    layout_complate.setVisibility(View.GONE);
                    break;

                case -1:
                    updateGridUI();
                    break;
                case 0:
                    updateListUI();
                    break;
                case 1:
                    ToastTools.showShort(UnloadingActivity.this,string);
                    break;

            }
        }
    };

    List<ShelvesBean> listUnloading  = new ArrayList<>();
    List<ShelvesBean> liststore  = new ArrayList<>();
    List<DeviceBean> listNotEmptyUnload = new ArrayList<>();
    ImageView back;
    TextView choosehuojia;
    TextView huojia;
    TextView xiezaiwei;
    RelativeLayout layout_choose;
    LinearLayout layout_complate;
    TextView cancan_choose;
    TextView cancan_com;
    TextView complate;
    TextView sure_choose;
    TextView sure_com;
    TextView over;
    TextView start;
    RecyclerView huojialist;
    RecyclerView xiezailist;
    RecyclerView complate_list;
    ChooseShelvesAdapter chooseShelvesAdapter;
    ChooseShelvesAdapter chooseShelvesAdapter2;
    UnloadComplateAdapter unloadadapter;

    String unloadingID ="";
    String storeID ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unloading);
//        initShelves();
        initUI();
        initAction();
    }

    private void updateListUI(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chooseShelvesAdapter = new ChooseShelvesAdapter(listUnloading);
        huojialist.setLayoutManager(layoutManager);
        huojialist.setAdapter(chooseShelvesAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        chooseShelvesAdapter2 = new ChooseShelvesAdapter(liststore);
        xiezailist.setLayoutManager(layoutManager2);
        xiezailist.setAdapter(chooseShelvesAdapter2);
        layout_choose.setVisibility(View.VISIBLE);
    }
    private void updateGridUI(){
        unloadadapter =  new UnloadComplateAdapter(listNotEmptyUnload);
        GridLayoutManager layoutManager3 = new GridLayoutManager(this,3);
        complate_list.setLayoutManager(layoutManager3);
        complate_list.setAdapter(unloadadapter);
        layout_complate.setVisibility(View.VISIBLE);
    }

    private void initShelves(){

        liststore.clear();
        listUnloading.clear();
        Message message1 = new Message();
        message1.what=1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "unload/listAllEmptyUnloadAndHaveGoodsStorePositions", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(ACTIVITYNAME, "获取卸载区所有空卸载位和有货物的存储位"+e.toString());
                    message1.obj="获取卸载区所有空卸载位和有货物的存储位"+e.toString();
                    handler.sendMessage(message1);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(ACTIVITYNAME, "获取卸载区所有空卸载位和有货物的存储位=="+body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result==200){
                        JSONArray jsonArray= JSONArray.parseArray(jsonObject.getString("data"));
                        for (int i=0;i<jsonArray.size();i++){
                            ShelvesBean bean = JSON.parseObject( jsonArray.get(i).toString(),ShelvesBean.class);
                            if (bean.getType().equals("unload")){
                                listUnloading.add(bean);
                            }else if (bean.getType().equals("store")){
                                liststore.add(bean);
                            }
                        }
                        Message message0 = new Message();
                        message0.what=0;
                        handler.sendMessage(message0);
                    }else {
                        message1.obj="获取卸载区所有空卸载位和有货物的存储位："+jsonObject.get("data");
                        handler.sendMessage(message1);

                    }
                }
            },"#TOKEN#",app.getTOKEN());
        }catch (Exception ex){
            message1.obj="获取卸载区所有空卸载位和有货物的存储位："+ex.toString();
            handler.sendMessage(message1);
        }

    }
    private void initUnloadPositions(){
        listNotEmptyUnload.clear();

        Message message2 = new Message();
        message2.what=1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "unload/listAllNotEmptyUnloadPositions", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(ACTIVITYNAME, "获取所有非空卸载位"+e.toString());
                    message2.obj="获取所有非空卸载位"+e.toString();
                    handler.sendMessage(message2);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(ACTIVITYNAME, "获取所有非空卸载位=="+body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result==200){
                        JSONArray jsonArray= JSONArray.parseArray(jsonObject.getString("data"));
                        for (int i=0;i<jsonArray.size();i++){
                            DeviceBean bean = JSON.parseObject( jsonArray.get(i).toString(),DeviceBean.class);
                            listNotEmptyUnload.add(bean);
                        }
                        Message message0 = new Message();
                        message0.what=-1;
                        handler.sendMessage(message0);
                    }else {
                        message2.obj=jsonObject.get("data");
                        handler.sendMessage(message2);

                    }
                }
            },"#TOKEN#",app.getTOKEN());
        }catch (Exception ex){
            message2.obj="获取所有非空卸载位："+ex.toString();
            handler.sendMessage(message2);
        }
    }
    private void initUI(){
        back = findViewById(R.id.back);
        choosehuojia = findViewById(R.id.choosehuojia);
        complate = findViewById(R.id.complate);
        layout_complate = findViewById(R.id.layout_complate);
        cancan_com = findViewById(R.id.cancan_com);
        layout_choose = findViewById(R.id.layout_choose);
        cancan_choose = findViewById(R.id.cancan_choose);
        huojialist = findViewById(R.id.huojialist);
        xiezailist = findViewById(R.id.xiezailist);
        complate_list = findViewById(R.id.complate_list);
        sure_choose = findViewById(R.id.sure_choose);
        sure_com = findViewById(R.id.sure_com);
        over = findViewById(R.id.over);
        start = findViewById(R.id.start);
        huojia= findViewById(R.id.huojia);
        xiezaiwei= findViewById(R.id.xiezaiwei);

    }

    private void initAction(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        choosehuojia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShelves();
//                layout_choose.setVisibility(View.VISIBLE);
            }
        });
        cancan_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_choose.setVisibility(View.GONE);
            }
        });
        complate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUnloadPositions();
//                layout_complate.setVisibility(View.VISIBLE);
            }
        });
        cancan_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_complate.setVisibility(View.GONE);
            }
        });
        sure_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unloadingID = chooseShelvesAdapter.getOnclickedItem();
                storeID = chooseShelvesAdapter2.getOnclickedItem();
                if (unloadingID.equals("")==true || storeID.equals("")==true){
                    ToastTools.showShort(UnloadingActivity.this,"请选择两边位置");
                }else {
                    huojia.setText("卸载位："+chooseShelvesAdapter.getOnclickedName());
                    xiezaiwei.setText("货架："+chooseShelvesAdapter2.getOnclickedName());
                    layout_choose.setVisibility(View.GONE);
//                    callFullShelves(unloadingID,storeID);
                }

            }
        });
        sure_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unloadPositions = unloadadapter.getOnclickedItem();
                if (unloadPositions.equals("")==true){
                    ToastTools.showShort(UnloadingActivity.this,"请选择卸货位");
                }else {
                    sendShelvesBack(unloadPositions);
                }
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Over();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unloadingID.equals("")==true || storeID.equals("")==true){
                    ToastTools.showShort(UnloadingActivity.this,"请选择两边位置");
                }else {
                    callFullShelves(unloadingID,storeID);
                }
            }
        });
    }
    public void callFullShelves(String unloadPosition,String storePosition){
        Message message1 = new Message();
        message1.what=1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "unload/callFullShelves", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(ACTIVITYNAME, e.toString());
                    message1.obj=e.toString();
                    handler.sendMessage(message1);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(ACTIVITYNAME, "呼叫货架到卸载位=="+body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result==200){
                        message1.obj=jsonObject.get("data");
                        handler.sendMessage(message1);
                    }else {
                        message1.obj="呼叫货架到卸载位："+jsonObject.get("data");
                        handler.sendMessage(message1);

                    }
                }
            },"#TOKEN#",app.getTOKEN(),
                    "unloadPosition",unloadPosition,
                    "storePosition",storePosition);
        }catch (Exception ex){

            message1.obj="呼叫货架到卸载位："+ex.toString();
            handler.sendMessage(message1);
        }
    }

    public void sendShelvesBack(String unloadPositions){
        Log.d(ACTIVITYNAME,unloadPositions);
        Message message1 = new Message();
        message1.what=1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "unload/sendShelvesBack", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(ACTIVITYNAME, e.toString());
                            message1.obj=e.toString();
                            handler.sendMessage(message1);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String body = response.body().string();
                            Log.d(ACTIVITYNAME, "送回卸载位的货架=="+body);
                            JSONObject jsonObject = JSONObject.parseObject(body);
                            int result = Integer.parseInt(jsonObject.getString("code"));
                            if (result==200){
                                Message message1 = new Message();
                                message1.what=-2;
                                message1.obj=jsonObject.get("data");
                                handler.sendMessage(message1);
                            }else {
                                message1.obj="送回卸载位的货架："+jsonObject.get("data");
                                handler.sendMessage(message1);

                            }
                        }
                    },"#TOKEN#",app.getTOKEN(),
                    "unloadPositions",unloadPositions);
        }catch (Exception ex){

            message1.obj="送回卸载位的货架："+ex.toString();
            handler.sendMessage(message1);
        }
    }
    private void Over(){
        Message message1 = new Message();
        message1.what=1;
        try {
            OkhttpTools.sendOkHttpPostRequest(HttpUtrl.base_ip + "unload/finish", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(ACTIVITYNAME, e.toString());
                    message1.obj=e.toString();
                    handler.sendMessage(message1);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    Log.d(ACTIVITYNAME, "结束卸载区工作=="+body);
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("code"));
                    if (result==200){
                        message1.obj=jsonObject.get("data");
                        handler.sendMessage(message1);
                    }else {
                        message1.obj="结束卸载区工作："+jsonObject.get("data");
                        handler.sendMessage(message1);

                    }
                }
            },"#TOKEN#",app.getTOKEN());
        }catch (Exception ex){

            message1.obj="结束卸载区工作："+ex.toString();
            handler.sendMessage(message1);
        }
    }
}
