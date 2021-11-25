package com.example.bamboo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.PathData;
import com.example.bamboo.javaBean.UserLocal;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class PathView extends View {
    //线条画笔
    private Paint mLinePaint;
    //圆点画笔
    private Paint mCirclePaint;
    //文字画笔
    private Paint mTextPaint;
    //线的颜色
    private String normalColor = "#ffffff";

    //字母大小 是以px为单位的
    private int textSize = 60;
    //点的图片的宽度
    private int imageWidth;

    private int[] updateCoinsNeed = {1, 2, 2, 6, 4, 4, 4, 8, 4, 4, 8, 4, 0};
    //坐标点
    private List<PointEntity> mPointEntities=new ArrayList<>();

    private List<PathData> mPathDataList=new ArrayList<>();
    private float textWidth;
    private Paint mCurrentTextPaint;
    private Paint mNextTextPaint;
    private int currentImageWidth;
    private int nextImageHeight;


    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

//绘制方法
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(R.color.person_text_color);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);

//        canvas.drawLine();
        drawData(canvas);
    }
    private void drawData(Canvas canvas) {
        setDataList();
        List<UserLocal> userLocalList = LitePal.findAll(UserLocal.class);
        UserLocal userLocal = userLocalList.get(0);
        int index = userLocal.getLevel().charAt(0)-65;
        int need = updateCoinsNeed[userLocal.getLevel().charAt(0)-65];
//画线，画圈，写字
        for (int i = 0; i < mPointEntities.size() - 1; i++) {
            PointEntity pointEntity = mPointEntities.get(i);
            PointEntity pointEntityNext = mPointEntities.get(i + 1);
            canvas.drawLine(pointEntity.getX(), pointEntity.getY(), pointEntityNext.getX(), pointEntityNext.getY(), mLinePaint);
        }
        for (int i = 0; i < mPointEntities.size(); i++) {
            PointEntity pointEntity = mPointEntities.get(i);
            if (i==index){
                imageWidth= BitmapFactory.decodeResource(getResources(), R.drawable.current_level).getWidth();
                currentImageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.bg_current_level).getWidth();

                canvas.drawBitmap(getBitmap(R.drawable.current_level),pointEntity.getX()-(imageWidth/2),pointEntity.getY()-(imageWidth/2),mCirclePaint);
                canvas.drawBitmap(getBitmap(R.drawable.bg_current_level),pointEntity.getX()-currentImageWidth,pointEntity.getY()-imageWidth,mCirclePaint);
                canvas.drawText("当前：等级"+userLocal.getLevel(),pointEntity.getX()-currentImageWidth+textWidth*2,pointEntity.getY()-textWidth*2,mCurrentTextPaint);

            }else if (i == index+1){
                nextImageHeight =BitmapFactory.decodeResource(getResources(), R.drawable.bg_next_levle).getHeight();
                imageWidth= BitmapFactory.decodeResource(getResources(), R.drawable.next_level).getWidth();
                canvas.drawBitmap(getBitmap(R.drawable.next_level),pointEntity.getX()-(imageWidth/2),pointEntity.getY()-(imageWidth/2),mCirclePaint);
                canvas.drawBitmap(getBitmap(R.drawable.bg_next_levle),pointEntity.getX()+textWidth*3,pointEntity.getY()-nextImageHeight*2+textWidth,mCirclePaint);
                canvas.drawText("距离升级还需"+need,pointEntity.getX()+textWidth*4,pointEntity.getY()-nextImageHeight,mNextTextPaint);
            }else{
            imageWidth= BitmapFactory.decodeResource(getResources(), R.drawable.d).getWidth();
            canvas.drawBitmap(getBitmap(R.drawable.d),pointEntity.getX()-(imageWidth/2),pointEntity.getY()-(imageWidth/2),mCirclePaint);

            }
        }

        for (int i = 0; i < mPointEntities.size(); i++) {
           PointEntity pointEntity= mPointEntities.get(i);
            PathData pathData = mPathDataList.get(i);
            if ((i/4)%2==0){
                if (pathData.getWord().equals("K") || pathData.getWord().equals("L")){
                    canvas.drawText(pathData.getWord(),pointEntity.getX()-textWidth,pointEntity.getY()-textWidth*2,mTextPaint);
                }else{
                    canvas.drawText(pathData.getWord(),pointEntity.getX()+textWidth*2,pointEntity.getY()+textWidth*2,mTextPaint);
                }
            }else{

                canvas.drawText(pathData.getWord(),pointEntity.getX()-textWidth*2,pointEntity.getY()-textWidth*2,mTextPaint);
            }

        }

    }

    private Bitmap getBitmap(int bitmapId){
        Bitmap bitmap= null;
        bitmap= BitmapFactory.decodeResource(getResources(), bitmapId);
        return bitmap;
    }

    private void setDataList(){
        initData();
        for (int i=0;i<mPathDataList.size();i++){
            float x = mPathDataList.get(i).getxPath();
            float y = mPathDataList.get(i).getyPath();
            PointEntity pointentity = new PointEntity();
            pointentity.setX(x);
            pointentity.setY(y);
            mPointEntities.add(pointentity);
        }
    }
    private void initData(){
//被我试出来是1920*1080 但是不行，于是用比例
        int weight = getScreenHeight();
        int height = getScreenWidth();
        PathData A = new PathData((float) (height*0.337), (float) (weight*0.7818),"A");
        PathData B = new PathData((float) (height*0.534),(float) (weight*0.7359),"B");
        PathData C = new PathData((float) (height*0.626),(float) (weight*0.674),"C");
        PathData D = new PathData((float) (height*0.568),(float) (weight*0.5948),"D");
        PathData E = new PathData((float) (height*0.407),(float) (weight*0.5438),"E");
        PathData F = new PathData((float) (height*0.483),(float) (weight*0.4755),"F");
        PathData G = new PathData((float) (height*0.326),(float) (weight*0.426),"G");
        PathData H = new PathData((float) (height*0.4417),(float) (weight*0.3708),"H");
        PathData I = new PathData((float) (height*0.6213),(float) (weight*0.3474),"I");
        PathData J = new PathData((float) (height*0.712),(float) (weight*0.2964),"J");
        PathData K = new PathData((float) (height*0.527),(float) (weight*0.2411),"K");
        PathData L = new PathData((float) (height*0.3648),(float) (weight*0.2177),"L");
        PathData M = new PathData((float) (height*0.26),(float) (weight*0.1443),"M");

        mPathDataList.add(A);
        mPathDataList.add(B);
        mPathDataList.add(C);
        mPathDataList.add(D);
        mPathDataList.add(E);
        mPathDataList.add(F);
        mPathDataList.add(G);
        mPathDataList.add(H);
        mPathDataList.add(I);
        mPathDataList.add(J);
        mPathDataList.add(K);
        mPathDataList.add(L);
        mPathDataList.add(M);
    }

    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor(normalColor));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.parseColor(normalColor));
        mCirclePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor(normalColor));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        textWidth = mTextPaint.measureText("A");

        mCurrentTextPaint = new Paint();
        mCurrentTextPaint.setTextSize(textSize);
        mCurrentTextPaint.setColor(Color.parseColor("#154181"));
        mCurrentTextPaint.setAntiAlias(true);

        mNextTextPaint = new Paint();
        mNextTextPaint.setTextSize(40);
        mNextTextPaint.setColor(Color.parseColor(normalColor));
        mNextTextPaint.setAntiAlias(true);

    }

    //获得屏幕宽度
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    //获得屏幕高度
    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    static class PointEntity {
        private Float x;
        private Float y;

        public Float getX() {
            return x;
        }

        public void setX(Float x) {
            this.x = x;
        }

        public Float getY() {
            return y;
        }

        public void setY(Float y) {
            this.y = y;
        }
    }
}




