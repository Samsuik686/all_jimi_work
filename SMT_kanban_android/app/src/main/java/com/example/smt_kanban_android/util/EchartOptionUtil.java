package com.example.smt_kanban_android.util;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;

import java.util.Arrays;
import java.util.List;


public class EchartOptionUtil {
    public static GsonOption getLineChartOptions(Object[] xAxisdata, Object[] yAxisdata,String standline,double alielie) {
        GsonOption option = new GsonOption();
        option.title().x(X.center).text("时段产量 标产:"+standline).textStyle().fontSize(11);

//        option.dataZoomNew().type(DataZoomType.inside).realtime(true).start(0).end(100);
//        option.legend("销量");
//        option.tooltip().trigger(Trigger.axis);
        option.grid().x(50).y(30).y2(30);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().textStyle().fontSize(9);
        valueAxis.max(alielie);
//        valueAxis.name("标产160").nameTextStyle().color("red");
        option.yAxis(valueAxis);


        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.axisLabel().textStyle().fontSize(9);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxisdata);
        option.xAxis(categorxAxis);
        Bar bar = new Bar();
        bar.data(yAxisdata);
        //这个160到时候后台传数据

        bar.markLine().data(new PointData().yAxis(Integer.valueOf(standline)).symbolSize(2));
        bar.markLine().itemStyle().normal().lineStyle().width(3);
        bar.markLine().itemStyle().normal().color("#c23531");
        bar.itemStyle().normal().color("#456b94").label().show(true);
        bar.label().normal().textStyle().fontSize(7);
        option.series(bar);
        return option;
    }

    public static GsonOption getLineChartOptions2(Object[] xAxisdata, Object[] yAxisdata,String standline,double alielie) {
        GsonOption option = new GsonOption();
        option.title().x(X.center).text("产品不良率 标准不良率:"+standline+"%").textStyle().fontSize(11);
//        option.legend("销量");
//        option.tooltip().trigger(Trigger.axis);
//        option.dataZoomNew().show(true).realtime(true).start(0).end(100);
        option.grid().x(50).y(30).y2(30);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().textStyle().fontSize(9);
        valueAxis.axisLabel().formatter("{value} %");
//        valueAxis.name("标产160").nameTextStyle().color("red");
        valueAxis.max(alielie);
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.axisLabel().textStyle().fontSize(9);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxisdata);
        option.xAxis(categorxAxis);
        Bar bar = new Bar();
        bar.data(yAxisdata);
        //这个160到时候后台传数据
        bar.markLine().data(new PointData().yAxis(Integer.valueOf(standline)).symbolSize(2));
        bar.markLine().itemStyle().normal().lineStyle().width(3);
        bar.markLine().itemStyle().normal().color("#c23531");
        bar.itemStyle().normal().color("#456b94").label().show(true);
//        bar.itemStyle().normal().formatter("(function(){return params.value + %;})()");
        bar.label().normal().textStyle().fontSize(9);
        option.series(bar);
        return option;
    }
}
