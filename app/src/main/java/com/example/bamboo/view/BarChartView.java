package com.example.bamboo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.bamboo.javaBean.ResultData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BarChartView extends View {

    //X轴数据
    private List<String> mDescription;
    //Y轴数据
    private List<Double> mYDataList;

    private List<ResultData> mResultDataList;



    private int defaultLineColor = Color.parseColor("#7C7C7C");
    private int descriptionColor= Color.parseColor("#888888");;
    private int dataColor= Color.parseColor("#434343");
    private int maxBarColor= Color.parseColor("#4FABBA");
    private int minBarColor= Color.parseColor("#BAAF4F");
    private int dataBarColor= Color.parseColor("#89BA4F");

    //线的画笔
    private Paint mLinePaint;
    //柱状图画笔
    private Paint mDataBarPaint;
    private Paint mMaxBarPaint;
    private Paint mMinBarPaint;

    //x轴描述文字画笔
    private Paint mTextPaint;
    private int descriptionTextSize;
    private int dataTextSize;
    //y轴最大值

    private int y_max = 0;
    //y轴最小值
    private int y_min = 0;
    //y轴间距
    private int y_Space = 20;
    private int textSize = 40;
    private int mWidth;
    private int mHeight;
    private float XLength;
    private float barWidth=110;

    //控件宽
    int width;
    //控件高
    int height;

    /* 辅助计算柱宽，表示一个条目的宽度，包括柱子和空余部分 */
    private float mItemBarWidth = 0;
    //柱状图数量
    private int mShowNumber = 3;
    private int mMaxScrollx;
    private int perBarW;
    private int mParentWidth;
    private int mParentHeight;
    private float mTextWidth;
    private ResultData mResultData;

    public ResultData getmResultData() {
        return mResultData;
    }

    public void setmResultData(ResultData mResultData) {
        this.mResultData = mResultData;
        invalidate();
    }

    private void init() {
        mDataBarPaint = new Paint();
        mDataBarPaint.setColor(dataBarColor);
        mDataBarPaint.setAntiAlias(true);
        mDataBarPaint.setStyle(Paint.Style.STROKE);
        mDataBarPaint.setStrokeWidth(barWidth);

        mMaxBarPaint = new Paint();
        mMaxBarPaint.setColor(maxBarColor);
        mMaxBarPaint.setAntiAlias(true);
        mMaxBarPaint.setStyle(Paint.Style.STROKE);
        mMaxBarPaint.setStrokeWidth(barWidth);

        mMinBarPaint = new Paint();
        mMinBarPaint.setColor(minBarColor);
        mMinBarPaint.setAntiAlias(true);
        mMinBarPaint.setStyle(Paint.Style.STROKE);
        mMinBarPaint.setStrokeWidth(barWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(descriptionColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(descriptionColor);
        mTextWidth = mTextPaint.measureText("O");

        mLinePaint = new Paint();
        mLinePaint.setColor(defaultLineColor);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5);


    }
    public BarChartView(Context context) {
        super(context);
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //测量View的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //重写onMeasure方法，获取宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
//        setDataLineWidth();
    /*


        //获取该view所在父布局的中心点
        ViewGroup mViewGroup = (ViewGroup) getParent();
        if(null != mViewGroup){
            mParentWidth = mViewGroup.getWidth();
            mParentHeight = mViewGroup.getHeight();
        }

     */
/*
一开始设置为wrap content 后面改成Match parent
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //若宽高为warp_content，则设控件宽高
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, 600);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 600);
        }
 */
    }

//    private void setDataLineWidth() {
//        if (mYDataList.size() > 0) {
//            //设置柱状图宽度
//            mMaxScrollx = (mWidth / mShowNumber) * mYDataList.size() - mWidth;
//            //计算ITEM宽度
//            mItemBarWidth = mWidth / mYDataList.size();
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBar(canvas);
        drawXY(canvas);
//        canvas.drawLine(0,0,mMaxScrollx + mWidth, 0, mDataBarPaint);

    }

    private void drawBar(Canvas canvas) {
        XLength = mWidth-6*mTextWidth;
        mItemBarWidth = XLength/mShowNumber;
        perBarW = (int) (mItemBarWidth/2);
        float startY = mHeight-getPaddingBottom()-mTextWidth*2;
        float startX =getPaddingLeft()+mTextWidth;
        //数据都是按三条平分算的
        float move = 30;
        float textMove = (getTextWidth(mResultData.getName(),mTextPaint)-barWidth)/2;
        float maxMinTextMove =(getTextWidth("Max",mTextPaint)-barWidth)/2;
        float firstX = startX+XLength/6;
        float secondX = startX+XLength/2;
        float thirdX =startX+XLength*5/6;

        double totalLength =1.0;
        if (mResultData.getMax()>mResultData.getData()){
            totalLength = mResultData.getMax();
        }else{
            totalLength=mResultData.getData();
        }
        float realLength = (float) (mHeight-mTextWidth*3.5);
        float temp = (float) ((realLength-mTextWidth*3)/totalLength);
        BigDecimal bigDecimal = new BigDecimal(mResultData.getData());
        Double data = bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();

        canvas.drawText(String.valueOf(data),firstX-barWidth/2-maxMinTextMove,(float) (startY-temp*mResultData.getData()-10),mTextPaint);
       canvas.drawLine(firstX,startY,firstX, (float) (startY-temp*mResultData.getData()),mDataBarPaint);
       canvas.drawText(mResultData.getName(),firstX-barWidth/2-textMove, (float) (startY+mTextWidth*1.5),mTextPaint);

        canvas.drawText(String.valueOf(mResultData.getMax()),secondX-barWidth/2-maxMinTextMove,(float) (startY-temp*mResultData.getMax()-10),mTextPaint);
        canvas.drawLine(secondX,startY,secondX, (float) (startY-temp*mResultData.getMax()),mMaxBarPaint);
        canvas.drawText("Max",secondX-barWidth/2-maxMinTextMove, (float) (startY+mTextWidth*1.5),mTextPaint);

        canvas.drawText(String.valueOf(mResultData.getMin()),thirdX-barWidth/2-maxMinTextMove,(float) (startY-temp*mResultData.getMin()-10),mTextPaint);
        canvas.drawLine(thirdX,startY,thirdX, (float) (startY-temp*mResultData.getMin()),mMinBarPaint);
        canvas.drawText("Min",thirdX-barWidth/2-maxMinTextMove, (float) (startY+mTextWidth*1.5),mTextPaint);
    }

    /**
     * @param text  绘制的文字
     * @param paint 画笔
     * @return 文字的宽度
     */
    public int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }


    /**
     * 初始化坐标轴
     *
     * @param canvas
     */
    private void drawXY(Canvas canvas) {
        width = getWidth() - getPaddingLeft() - getPaddingRight();
        height = getHeight() - getPaddingTop() - getPaddingBottom();

        drawAxis(canvas);
    }

    /**
     * 绘制坐标轴
     * @param canvas
     */
    private void drawAxis(Canvas canvas){
        canvas.drawText("数值",getPaddingLeft()*2,getPaddingTop()*2+mTextWidth*2,mTextPaint);
        canvas.drawLine(getPaddingLeft()+mTextWidth,
                (float) (getPaddingTop()+mTextWidth*2.5),
                getPaddingLeft()+mTextWidth,
                mHeight-getPaddingBottom()-mTextWidth*2,
                mLinePaint
                );
//勾股定理，345
        canvas.drawLine(getPaddingLeft()+mTextWidth,
                (float) (getPaddingTop()+mTextWidth*2.5),
                (float) (getPaddingLeft()+mTextWidth-mTextWidth*0.75),
                (float) (getPaddingTop()+mTextWidth*2.5+mTextWidth),mLinePaint
                );
        canvas.drawLine(getPaddingLeft()+mTextWidth,
                (float) (getPaddingTop()+mTextWidth*2.5),
                (float) (getPaddingLeft()+mTextWidth+mTextWidth*0.75),
                (float) (getPaddingTop()+mTextWidth*2.5+mTextWidth)
,mLinePaint
        );

        canvas.drawLine(getPaddingLeft()+mTextWidth,
                mHeight-getPaddingBottom()-mTextWidth*2,
                mWidth-getPaddingRight()-mTextWidth*5,
                mHeight-getPaddingBottom()-mTextWidth*2,
                mLinePaint
        );
//
        canvas.drawLine(mWidth-getPaddingRight()-mTextWidth*5,
                mHeight-getPaddingBottom()-mTextWidth*2,
                mWidth-getPaddingRight()-mTextWidth*5-mTextWidth,
                (float) (mHeight-getPaddingBottom()-mTextWidth*2-mTextWidth*0.75),
                mLinePaint
        );

        canvas.drawLine(mWidth-getPaddingRight()-mTextWidth*5,
                mHeight-getPaddingBottom()-mTextWidth*2,
                mWidth-getPaddingRight()-mTextWidth*5-mTextWidth,
                (float) (mHeight-getPaddingBottom()-mTextWidth*2+mTextWidth*0.75),
                mLinePaint
        );

        canvas.drawText("特征",mWidth-getPaddingRight()-mTextWidth*4,
                mHeight-getPaddingBottom()-mTextWidth*2,mTextPaint);

    }
    private void setEntityList(List<ResultData> list) {
        mResultDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGrade()==null){
                mResultDataList.add(list.get(i));
            }else {
//                grade = list.get(i).getGrade();
            }
        }

    }

}
