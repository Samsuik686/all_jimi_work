package com.example.smt_kanban_android.learndemo;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
//流式布局demo
public class FlowLayout extends ViewGroup {
    private static final String TAG = "Flowlayout";
    int mHorizontalSpacing = dp2px(16);
    int mVerticalSpacing = dp2px(8);
    private List<List<View>> allLine;//所有view的信息
    List<Integer> lineHeights = new ArrayList<>();//记录每一行的高度

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initMeasureParams(){
        allLine = new ArrayList<>();
        lineHeights = new ArrayList<>();
    }
    /*
    测量，买房子先看多大平，有那些地方
    UNSPECIFIED在系统层 ，几乎用不到

     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //onMeasure多次调用，初始化不能写在构造函数里
        initMeasureParams();

        /*
        度量是从底部递归，先度量子类，然后递归到上面
        */

        List<View> lineView =  new ArrayList<>();//一行多少个
        int lineWidthused = 0;//记录这行使用了多宽
        int lineHeight = 0;//一行的行高

        int parentNeededWidth =0;//子view对父类的要求
        int parentNeededHeigh =0;

        int childcount = getChildCount();
        int paddingleft = getPaddingLeft();
        int paddingright = getPaddingRight();
        int paddingtop = getPaddingTop();
        int paddingbottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);//viewgroup解析的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);//viewgroup解析的高度

        for (int i = 0;i<childcount;i++){
            View childView = getChildAt(i);
            LayoutParams childlP = childView.getLayoutParams();
            int childWidthMeasuerSpec = getChildMeasureSpec(widthMeasureSpec,paddingleft + paddingright,childlP.width);
            int childHeightMeasuerSpec = getChildMeasureSpec(heightMeasureSpec,paddingtop+paddingbottom,childlP.height);
            childView.measure(childWidthMeasuerSpec,childHeightMeasuerSpec);
            //获取子view的宽高
            int childMesauredWidth = childView.getMeasuredWidth();
            int childMeasureHeight = childView.getMeasuredHeight();
            if(childMesauredWidth + lineWidthused +mHorizontalSpacing>selfHeight){
                allLine.add(lineView);
                lineHeights.add(lineHeight);

                //一旦换行，我们就判断宽高，记录下来
                parentNeededHeigh = parentNeededHeigh + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth,lineWidthused + mHorizontalSpacing);

                lineView = new ArrayList<>();
                lineWidthused = 0;
                lineHeight = 0;
            }
            //view 是分行的layout，记录行内元素，方便布局
            lineView.add(childView);
            lineWidthused = lineWidthused + childMesauredWidth +mHorizontalSpacing;
            lineHeight = Math.max(lineHeight,childMeasureHeight);
        }
        //根据子view的度量结果。重新度量自己
        // 作为一个veiwgroup，自己也是一个view，也需要父亲的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY)?selfWidth:parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY)?selfHeight:parentNeededHeigh;

        setMeasuredDimension(realWidth,realHeight);

    }
    //viewgrop必须要实现onlayout，布局，哪里放啥东西，view而非viewgrop很少需要布局，viewgrop则不需要ondrow，因为小房间都装修完了
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
         int lineCount = allLine.size();

         int curL = getPaddingLeft();
         int curT = getPaddingTop();

         for(int i=0;i<lineCount;i++){
             List<View> lineviews = allLine.get(i);
             int lineHeight = lineHeights.get(i);
             for(int j=0;j<lineviews.size();j++){
                 View view = lineviews.get(i);
                 int left = curL;
                 int top = curT;
                 int right = left + view.getMeasuredWidth();
                 int bottom = top + view.getMeasuredHeight();
                 view.layout(left,top,right,bottom);
                 curL = right + mHorizontalSpacing;
             }
             curL = getPaddingLeft();
             curT = curT + lineHeight +mVerticalSpacing;
         }
    }
    public static int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
    }
}
