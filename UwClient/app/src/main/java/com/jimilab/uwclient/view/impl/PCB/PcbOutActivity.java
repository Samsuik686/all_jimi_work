package com.jimilab.uwclient.view.impl.PCB;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jimilab.uwclient.BuildConfig;
import com.jimilab.uwclient.R;
import com.jimilab.uwclient.adapter.PCBSpinnerAdapter;
import com.jimilab.uwclient.adapter.PcbImpAdapter;
import com.jimilab.uwclient.bean.pcb_bean.Pcbcompany_bean;
import com.jimilab.uwclient.bean.pcb_bean.mission_bean;
import com.jimilab.uwclient.bean.pcb_bean.mission_detail_bean;
import com.jimilab.uwclient.bean.pcb_bean.mission_detail_son_bean;
import com.jimilab.uwclient.bean.pcb_bean.supplier_bean;
import com.jimilab.uwclient.utils.OkHttpUtils;
import com.jimilab.uwclient.view.impl.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PcbOutActivity extends BaseActivity {

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case 0:
                    GetZxingDate();
                    break;
                case -2:
                    showToast("成功结束,重新获取任务列表");
                    getMissionList();
                    break;
                case -1:
                    String string=(String)message.obj;
                    showToast(string);
                    break;
                case 1:
                    pcbcompanyadapter.notifyDataSetChanged();
                    break;
                case 2:
                    supplieradapter.notifyDataSetChanged();
                    break;
                case 3:
                    missionadapter.notifyDataSetChanged();
                    break;
                case 4:
                    MissionInadapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.sp_supplier)
    Spinner sp_supplier;
    @BindView(R.id.sp_company)
    Spinner sp_company;
    @BindView(R.id.mission_name)
    Spinner mission_name;
    @BindView(R.id.myrecycle)
    RecyclerView myrecycle;
    @BindView(R.id.locan)
    TextView locan;
    @BindView(R.id.et_sampling_material)
    EditText etSamplingMaterial;
    @BindView(R.id.finish_mession)
    Button finish_mession;
    @BindView(R.id.reflesh)
    Button reflesh;
    @BindView(R.id.finish_lack)
    Button finish_lack;
    ArrayList<supplier_bean> supplierlist = new ArrayList<>();
    PCBSpinnerAdapter supplieradapter;
    ArrayList<Pcbcompany_bean> pcbcompanylist = new ArrayList<>();
    PCBSpinnerAdapter pcbcompanyadapter;
    ArrayList<mission_bean> missionlist = new ArrayList<>();
    PCBSpinnerAdapter missionadapter;
    LinearLayoutManager linearLayoutManager;
    List<mission_detail_bean> mlist = new ArrayList<>();
    PcbImpAdapter MissionInadapter;
    //扫描的二维码分解字段
    String[] materialValues;
    //最后扫描到的二维码
    String scanMaterial;
    //最后扫到的二维码转化的javabean
    mission_detail_son_bean new_bean;
    //是否进行了扫描动作
    boolean hasxzing;
    //扫描了的二维码的料号名字
    String name;
    //倘若物料对应，则对应哪个item
    int zxing_number;
    //公司id，客户id，任务id，选择位置
    String company_id = null;
    String supplier_id = null;
    String mission_id = null;

    String packingListItemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcbout);
        ButterKnife.bind(this);
        initView();

        SpinnerCompany();
        initSpinnerAction();
    }
    //初始化下拉选择事件
    private void initSpinnerAction() {
        sp_company.setAdapter(pcbcompanyadapter);
        sp_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mlist.clear();
                MissionInadapter.notifyDataSetChanged();

                company_id = pcbcompanylist.get(pos).getId();
                SpinnerSupplier();
                supplieradapter.notifyDataSetChanged();
                sp_supplier.setSelection(0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_supplier.setAdapter(supplieradapter);
        sp_supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mlist.clear();
                MissionInadapter.notifyDataSetChanged();
                if (pos!=0){
                    supplier_id = supplierlist.get(pos).getId();
                    SpinnerMission();
                    mission_name.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mission_name.setAdapter(missionadapter);
        mission_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mlist.clear();
                MissionInadapter.notifyDataSetChanged();
                if (pos!=0){
                    mission_id = "";
                    mission_id = missionlist.get(pos).getId();
                    getMissionList();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        String intentUsr = intent.getStringExtra("usrname");
        user_name.setText(intentUsr);
        pcbcompanyadapter = new PCBSpinnerAdapter(PcbOutActivity.this, pcbcompanylist);
        supplieradapter = new PCBSpinnerAdapter(PcbOutActivity.this, supplierlist);
        missionadapter = new PCBSpinnerAdapter(PcbOutActivity.this, missionlist);
        etSamplingMaterial.setOnEditorActionListener(mEditorActionListener);
        linearLayoutManager = new LinearLayoutManager(this);
        myrecycle.setLayoutManager(linearLayoutManager);
        MissionInadapter = new PcbImpAdapter(mlist, PcbOutActivity.this,"out");
        myrecycle.setAdapter(MissionInadapter);
        myrecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = linearLayoutManager.getChildCount(); //子数
                int totalItemCount = linearLayoutManager.getItemCount(); // item总数
                int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition(); //当前屏幕 首个 可见的 Item 的position
                if (pastVisiblesItems + visibleItemCount + 1 >= zxing_number && zxing_number >= pastVisiblesItems - 1) {
                    takeItem();
                }

            }
        });
        finish_mession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postfinish();
            }
        });
        reflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerMission();
            }
        });
        finish_lack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_finish_lack();
            }
        });
    }

    //获取公司列表
    private void SpinnerCompany() {
        pcbcompanylist.clear();
        pcbcompanylist.add(new Pcbcompany_bean("", "", "", null));
        OkHttpUtils.sendOkHttpPostRequest(BuildConfig.BASE_URL + "pda/getCompanyVOs", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.obj = "获取公司列表"+e.toString();
                message.what=-1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                Log.d("sxmmm", "SpinnerCompany==" + body);
                try {
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result == 200) {
                        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("data"));
                        for (int i = 0; i < jsonArray.size(); i++) {
                            Pcbcompany_bean missionBean = JSON.parseObject(jsonArray.get(i).toString(), Pcbcompany_bean.class);
                            pcbcompanylist.add(missionBean);
                        }

                        Message message = new Message();
                        message.what=1;
                        handler.sendMessage(message);

                    } else {
                        Message message = new Message();
                        message.obj = "获取公司列表"+jsonObject.getString("data");
                        message.what=-1;
                        handler.sendMessage(message);

                    }
                }catch (Exception ex){
                    Message message = new Message();
                    message.obj = "获取公司列表"+ex.toString();
                    message.what=-1;
                    handler.sendMessage(message);
                }

            }
        }, "#TOKEN#", mApplication.getTOKEN());


    }

    //获取客户列表
    private void SpinnerSupplier() {
        supplierlist.clear();
        supplierlist.add(new supplier_bean("", null));
        if(company_id!=null){
            OkHttpUtils.sendOkHttpPostRequest(BuildConfig.BASE_URL + "pda/getSupplierVOs", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = new Message();
                            message.obj = "获取客户列表"+e.toString();
                            message.what=-1;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String body = response.body().string();
                                Log.d("sxmmm", "SpinnerSupplier==" + body);
                                JSONObject jsonObject = JSONObject.parseObject(body);
                                int result = Integer.parseInt(jsonObject.getString("result"));
                                if (result == 200) {
                                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("data"));
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        supplier_bean missionBean = JSON.parseObject(jsonArray.get(i).toString(), supplier_bean.class);
                                        supplierlist.add(missionBean);
                                    }

                                    Message message = new Message();
                                    message.what=2;
                                    handler.sendMessage(message);

                                } else {
                                    Message message = new Message();
                                    message.obj = jsonObject.getString("data");
                                    message.what=-1;
                                    handler.sendMessage(message);
                                }
                            }catch (Exception e){
                                Message message = new Message();
                                message.obj = "获取客户列表"+e.toString();
                                message.what=-1;
                                handler.sendMessage(message);
                            }


                        }
                    }, "#TOKEN#", mApplication.getTOKEN(),
                    "companyId",company_id);
        }
    }
    //获取任务列表
    private void SpinnerMission() {
        missionlist.clear();
        missionlist.add(new mission_bean("", "","","","",null,"","","","","",""));
        if(supplier_id!=null){
            OkHttpUtils.sendOkHttpPostRequest(BuildConfig.BASE_URL + "pda/getWorkingPcbOutTasks", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = new Message();
                            message.obj = "获取任务列表"+e.toString();
                            message.what=-1;
                            handler.sendMessage(message);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String body = response.body().string();
                                Log.d("sxmmm", "MissionList==" + body);
                                JSONObject jsonObject = JSONObject.parseObject(body);
                                int result = Integer.parseInt(jsonObject.getString("result"));
                                if (result == 200) {

                                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("data"));
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        mission_bean missionBean = JSON.parseObject(jsonArray.get(i).toString(), mission_bean.class);
                                        missionlist.add(missionBean);
                                    }
                                    Message message = new Message();
                                    message.what=3;
                                    handler.sendMessage(message);

                                } else {
                                    Message message = new Message();
                                    message.obj = jsonObject.getString("data");
                                    message.what=-1;
                                    handler.sendMessage(message);
                                }
                            }catch (Exception e){
                                Message message = new Message();
                                message.obj = "获取任务列表"+e.toString();
                                message.what=-1;
                                handler.sendMessage(message);
                            }


                        }
                    }, "#TOKEN#", mApplication.getTOKEN(),
                    "supplierId",supplier_id);
        }

    }
    //获取任务详情列表
    private void getMissionList() {
        hasxzing = false;
        mlist.clear();
        MissionInadapter.notifyDataSetChanged();
        OkHttpUtils.sendOkHttpPostRequest(BuildConfig.BASE_URL + "pda/getIOTaskInfos", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = new Message();
                        message.obj = "获取任务详情列表"+e.toString();
                        message.what=-1;
                        handler.sendMessage(message);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String body = response.body().string();
                            Log.d("sxmmm", "MissionDetailList==" + body);
                            JSONObject jsonObject = JSONObject.parseObject(body);
                            int result = Integer.parseInt(jsonObject.getString("result"));
                            if (result == 200) {
                                JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("data"));
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    mission_detail_bean missionBean = JSON.parseObject(jsonArray.get(i).toString(), mission_detail_bean.class);
                                    mlist.add(missionBean);
                                }
                                Message message = new Message();
                                message.what=4;
                                handler.sendMessage(message);
                            } else if(result == 412){

                            }else {
                                Message message = new Message();
                                message.obj = jsonObject.getString("data");
                                message.what=-1;
                                handler.sendMessage(message);
                            }
                        }catch (Exception e){
                            Message message = new Message();
                            message.obj = "获取任务详情列表"+e.toString();
                            message.what=-1;
                            handler.sendMessage(message);
                        }


                    }
                }, "#TOKEN#", mApplication.getTOKEN(),
                "taskId",mission_id);
    }
    //扫面后的post请求
    private void postzxing() {
        Log.d("sxmmm","#TOKEN#=="+ mApplication.getTOKEN()+
                "  packingListItemId=="+packingListItemId+
                "  materialId=="+materialValues[2]+
                "  quantity=="+materialValues[1]+
                "  supplierName=="+materialValues[4]
               );
        OkHttpUtils.sendOkHttpPostRequest(BuildConfig.BASE_URL + "pda/outPcb", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = new Message();
                        message.obj = "上传二维码"+e.toString();
                        message.what=-1;
                        handler.sendMessage(message);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String body = response.body().string();
                            Log.d("sxmmm", "postzxing==" + body);
                            JSONObject jsonObject = JSONObject.parseObject(body);
                            int result = Integer.parseInt(jsonObject.getString("result"));
                            if (result == 200) {
                                Message message = new Message();
                                message.what=0;
                                handler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = jsonObject.getString("data");
                                message.what=-1;
                                handler.sendMessage(message);
                            }
                        }catch (Exception e){
                            Message message = new Message();
                            message.obj = "上传二维码"+e.toString();
                            message.what=-1;
                            handler.sendMessage(message);
                        }


                    }
                }, "#TOKEN#", mApplication.getTOKEN(),
                "packingListItemId",packingListItemId,
                "materialId",materialValues[2],
                "quantity",materialValues[1],
                "supplierName",materialValues[4],
                "printTime",materialValues[11],
                "isForced","false");
    }
    //点击完成按钮后重新获取列表
    private void postfinish() {
        OkHttpUtils.sendOkHttpPostRequest(BuildConfig.BASE_URL + "pda/finishIOTask", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = response.body().string();
                        Log.d("sxmmm", "postfinish==" + body);
                        JSONObject jsonObject = JSONObject.parseObject(body);
                        int result = Integer.parseInt(jsonObject.getString("result"));
                        if (result == 200) {
                            Message message = new Message();
                            //("成功结束,重新获取任务列表");
                            message.what=-2;
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = jsonObject.getString("data");
                            message.what=-1;
                            handler.sendMessage(message);
                        }

                    }
                }, "#TOKEN#", mApplication.getTOKEN(),
                "taskId",mission_id);
    }
    //PCB仓：完成出入库任务缺料条目
    private void post_finish_lack() {
        OkHttpUtils.sendOkHttpPostRequest(BuildConfig.BASE_URL + "pda/finishIOTask", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = response.body().string();
                        Log.d("sxmmm", "postfinish==" + body);
                        JSONObject jsonObject = JSONObject.parseObject(body);
                        int result = Integer.parseInt(jsonObject.getString("result"));
                        if (result == 200) {
                            Message message = new Message();
                            //("成功结束,重新获取任务列表");
                            message.what=-2;
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = jsonObject.getString("data");
                            message.what=-1;
                            handler.sendMessage(message);
                        }

                    }
                }, "#TOKEN#", mApplication.getTOKEN(),
                "taskId",mission_id);
    }


    private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                switch (event.getAction()) {
                    //按下
                    case KeyEvent.ACTION_UP:
                        if (mission_id!=null && !mission_id.isEmpty()) {
                            if (!TextUtils.isEmpty(v.getText().toString().trim())) {
                                scanMaterial = String.valueOf(((EditText) v).getText());
                                //扫描的内容
                                // 料号@数量@时间戳(唯一码)@打印人员@供应商@位置@物料类型（标准/非标准）@生产日期@型号@产商@周期@打印日期@
                                scanMaterial = scanMaterial.replaceAll("\r", "");
                                materialValues = scanMaterial.split("@");
                                name = materialValues[0];
                                boolean haspipei = false;
                                for (int i = 0; i < mlist.size(); i++) {
                                    if (name.equals(mlist.get(i).getNo())) {
                                        if (materialValues[0].equals(mlist.get(i).getNo()) == true) {
                                            packingListItemId = mlist.get(i).getPackingListItemId();
                                            haspipei = true;
                                        }
                                    }
                                }
                                if (!haspipei) {
                                    hasxzing = false;
                                    showToast("此条码未找到适配物料");
                                }else {
                                    postzxing();
                                }
                            }
                            setScanMaterialRequestFocus();
                        } else {
                            setScanMaterialRequestFocus();
                            showToast("请选择任务单！");
                        }

                        return true;
                    default:
                        return true;
                }
            }
            return false;
        }
    };
    String lastExingId="";
    //扫面后把扫描的二维码信息展示在子列表
    private void takeItem() {
        if (hasxzing) {
            hasxzing = false;
            if (mlist.get(zxing_number).isSpread() == false) {
                View view = linearLayoutManager.findViewByPosition(zxing_number);
                LinearLayout layout = (LinearLayout) view; //获取布局中任意控件对象
                LinearLayout status = layout.findViewById(R.id.main_layout);
                status.performClick();
            }
            new_bean = new mission_detail_son_bean(materialValues[0], materialValues[2], materialValues[1],"出库",materialValues[10]);
            if (materialValues[2].equals(lastExingId)==false){
                MissionInadapter.addnewPcblmpDetail(new_bean, zxing_number);
                lastExingId = materialValues[2];
            }
        }

    }
    //处理扫描二维码
    public void GetZxingDate(){

        hasxzing = true;

        boolean hasclose = false;
        for (int i = 0; i < mlist.size(); i++) {
            if (name.equals(mlist.get(i).getNo()) == false) {
                if (mlist.get(i).isSpread()) {
                    try {
                        View view = linearLayoutManager.findViewByPosition(i);
                        LinearLayout layout = (LinearLayout) view; //获取布局中任意控件对象
                        ImageView status = layout.findViewById(R.id.open_close);
                        status.performClick();
                    } catch (Exception e) {
                        mlist.get(i).setSpread(false);
                        hasclose = true;
                    }
                }
            }
        }
        if (hasclose) {
            missionadapter.notifyDataSetChanged();
        }

        for (int i = 0; i < mlist.size(); i++) {
            if (name.equals(mlist.get(i).getNo())) {

                if (materialValues[0].equals(mlist.get(i).getNo()) == true) {
                    zxing_number = i;
                    myrecycle.scrollToPosition(i);
                    linearLayoutManager = (LinearLayoutManager) myrecycle.getLayoutManager();
                    linearLayoutManager.scrollToPositionWithOffset(i, 0);
                    try {
                        takeItem();
                    } catch (Exception ex) {
                        hasxzing = true;
                    }


                }
            }
        }

    }

    public void setScanMaterialRequestFocus() {
        etSamplingMaterial.setText("");
        etSamplingMaterial.requestFocus();
    }


}
