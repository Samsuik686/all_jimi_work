package com.example.xinhaoxuan.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyTextView  extends View {
    /**
     * 需要绘制的文字
     */
    private String mText;
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private int mTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public MyTextView(Context context) {
        this(context, null);
    }
    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        mText = "装载";
        mTextColor = Color.parseColor("#4C7808");
        mTextSize = 200;

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //获得绘制文本的宽和高
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }
    //API21
//    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 创建画笔
        Paint p = new Paint();
        //设置实心
        p.setStyle(Paint.Style.FILL);
        // 设置红色
        p.setColor(Color.parseColor("#5ECEFE"));
        Path path = new Path();
        path.moveTo(0, 40);// 此点为多边形的起点
        path.lineTo(40 ,0);
        path.lineTo((mBound.width()-40)*2, 0);
        path.lineTo(mBound.width()*2, 40);
        path.lineTo(mBound.width()*2, (mBound.height()-40)*2);
        path.lineTo((mBound.width()-40)*2, mBound.height()*2);
        path.lineTo(40, mBound.height()*2);
        path.lineTo(0, (mBound.height()-40)*2);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);

        //绘制文字
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);

    }
}