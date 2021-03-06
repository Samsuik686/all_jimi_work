package com.jimilab.uwclient.view.impl.PCB;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jimilab.uwclient.R;
import com.jimilab.uwclient.adapter.Pcb_Pandian_Choujian_adapter;
import com.jimilab.uwclient.bean.pcb_bean.PcbPandianChoujianBean;
import com.jimilab.uwclient.model.PcbCallBack;
import com.jimilab.uwclient.presenter.ComeAreaPresenter;
import com.jimilab.uwclient.presenter.PcbPresent;
import com.jimilab.uwclient.utils.Log;
import com.jimilab.uwclient.view.custom.LoadingDialog;
import com.jimilab.uwclient.view.impl.BaseActivity;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PcbPandianActivity extends BaseActivity implements PcbCallBack {
    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case 0:
                    tv_check_material_unique.setText("");
                    materialId.setText("");
                    tv_check_num.setText("");
                    showToast((String) message.obj);
                    break;
                case 1:
                    showToast((String) message.obj);
                    break;
                case 2:
                    check_number=0;
                    dataList.addAll((Collection<? extends PcbPandianChoujianBean>) message.obj);
                    adapter.notifyDataSetChanged();
                    checkSpinner.setSelection(0);
                    checkSpinner.setEnabled(true);
                    break;
                case 3:
                    not_saomiao_number_check.setText((String) message.obj);
                    break;
            }
        }
    };
    @BindView(R.id.iv_type_icon)
    ImageView ivTypeIcon;
    @BindView(R.id.tv_operator)
    TextView tv_operator;
    @BindView(R.id.check_spinner)
    Spinner checkSpinner;
    @BindView(R.id.btn_fresh_check)
    Button btn_fresh_check;
    @BindView(R.id.btn_lock_task)
    Button btnLockTask;
    @BindView(R.id.et_check_material)
    EditText et_check_material;
    @BindView(R.id.tv_scan_check)
    TextView tvScanCheck;
    @BindView(R.id.tv_check_material_unique)
    TextView tv_check_material_unique;
    @BindView(R.id.tv_check_num)
    TextView tv_check_num;
    @BindView(R.id.materialId)
    TextView materialId;
    @BindView(R.id.tatle)
    TextView tatle;
    @BindView(R.id.not_saomiao_number_check)
    TextView not_saomiao_number_check;
    Pcb_Pandian_Choujian_adapter adapter;
    com.jimilab.uwclient.presenter.PcbPresent pcbPresent;
    ArrayList<PcbPandianChoujianBean> dataList= new ArrayList<>();
    //选择的第几个
    int check_number=0;
    //任务id
    String mession_id;
    boolean taskLocked = false;
    String[] temp;
    PcbPandianChoujianBean defaultbean = new PcbPandianChoujianBean("","请选择盘点任务");
    //客户id
    String  supplierID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcb_pandian);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        ivTypeIcon.setImageResource(R.mipmap.check);
        tatle.setText("Pcb盘点任务");
        dataList.clear();
        Intent intent = getIntent();
        String intentUsr = intent.getStringExtra("usrname");
        tv_operator.setText(intentUsr);
        pcbPresent = new PcbPresent(this,mApplication);
        dataList.add(defaultbean);
        adapter = new Pcb_Pandian_Choujian_adapter(PcbPandianActivity.this,dataList);
        checkSpinner.setAdapter(adapter);
        checkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.d("sxmmm","id=="+dataList.get(pos).getId());
                check_number = pos;
                mession_id = dataList.get(pos).getId();
                supplierID = dataList.get(pos).getSupplier();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        pcbPresent.getListData("pda/getWorkingPcbInventoryTasks");
        et_check_material.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (check_number > 0 && taskLocked) {
                        if (event.getAction() == KeyEvent.ACTION_UP) {
                            clearResult();
                            String scanValue = String.valueOf(v.getText()).replace("\r", "");
                            Log.d("sxmmm", "scanValue = " + scanValue);
                            tvScanCheck.setText(scanValue);
                            if (scanValue.contains("@")) {
                                temp=scanValue.split("@");
                                try {
                                    tv_check_material_unique.setText(temp[0]);
                                    materialId.setText(temp[2]);
                                    tv_check_num.setText(temp[1]);
                                    pcbPresent.SendMessage("pda/inventoryPcbUWMaterial",temp[2], mession_id,temp[1]);
                                    setScanMaterialRequestFocus();
                                }catch (ArrayIndexOutOfBoundsException ex){
                                    showToast("PAD端报错：请扫码正确的二维码");
                                }
                            } else {
                                showToast("二维码格式不正确");
                                setScanMaterialRequestFocus();
                            }

                            return true;
                        }
                        return true;
                    } else {
                        showToast("未锁定任务单");
                        setScanMaterialRequestFocus();
                    }
                }
                return false;
            }
        });
    }
    @OnClick({R.id.iv_back, R.id.btn_fresh_check,R.id.btn_lock_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                exit();
                break;
            case R.id.btn_lock_task:
                lockSelectTask();
                break;
            case R.id.btn_fresh_check:
                tv_check_material_unique.setText("");
                materialId.setText("");
                tv_check_num.setText("");
                not_saomiao_number_check.setText("");
                dataList.clear();
                dataList.add(defaultbean);
                pcbPresent.getListData("pda/getWorkingPcbInventoryTasks");
                break;
        }
    }
    private void lockSelectTask() {
        Log.d("sxmmm","check");
        if (!taskLocked) {//锁定
            if (check_number <= 0) {
                showToast("请选择任务单");
            } else {
                checkSpinner.setEnabled(false);
                btnLockTask.setText("解锁");
                taskLocked = true;
                showToast("已锁定");
                btn_fresh_check.setEnabled(false);
                setScanMaterialRequestFocus();
            }
        } else {//解锁
            btn_fresh_check.setEnabled(true);
            checkSpinner.setEnabled(true);
            btnLockTask.setText("锁定");
            taskLocked = false;
            showToast("已解锁");
        }
    }
    public  void setScanMaterialRequestFocus(){
        et_check_material.setText("");
        et_check_material.requestFocus();
    }
    public void clearResult() {

    }
    @Override
    public void showMessage(ArrayList<PcbPandianChoujianBean> newdataList) {
        Message message = new Message();
        message.what=2;
        message.obj = newdataList;
        handler.sendMessage(message);
    }

    @Override
    public void Success(String  string) {
        Message message = new Message();
        message.what=1;
        message.obj = "上传成功";
        handler.sendMessage(message);
        pcbPresent.getHasNot("pda/getPcbUnScanInventoryTaskMaterial",mession_id,temp[0],supplierID);
    }

    @Override
    public void getHasNot(String string) {
        Message message = new Message();
        message.what=3;
        message.obj = string;
        handler.sendMessage(message);
    }

    @Override
    public void error_haNot(String string) {
        Message message = new Message();
        message.what=1;
        message.obj = string;
        handler.sendMessage(message);
    }

    @Override
    public void error(String string) {
        Message message = new Message();
        message.what=0;
        message.obj = string;
        handler.sendMessage(message);
    }
}
