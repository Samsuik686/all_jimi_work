package com.example.smt_kanban_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.smt_kanban_android.R;
import com.example.smt_kanban_android.util.EchartOptionUtil;
import com.example.smt_kanban_android.view.EchartView;

public class EcahartYieldActivity extends BaseActivity {
    private EchartView lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_echarts);
        Intent intent = getIntent() ;
        String type = intent.getStringExtra("type") ;
        Object[] x = intent.getStringArrayExtra("dataX") ;
        Object[] y = intent.getStringArrayExtra("dataY") ;
        lineChart = findViewById(R.id.lineChart);
        lineChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y,"0",0));
            }
        });
    }
}
