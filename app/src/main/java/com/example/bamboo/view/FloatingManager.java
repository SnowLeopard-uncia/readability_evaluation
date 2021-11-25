package com.example.bamboo.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.UserLocal;

import org.litepal.LitePal;

public class FloatingManager {
    private View mAnchorView;

    private String mTitle;

    private ViewGroup mRootView;

    private int[] updateCoinsNeed = {1, 2, 2, 6, 4, 4, 4, 8, 4, 4, 8, 4, 0};

    public static Builder getBuilder() {
        return new Builder();
    }

   public static class Builder {
        private FloatingManager mManager;

        public FloatingManager build() {
            return mManager;
        }

        public Builder() {
            mManager = new FloatingManager();
        }

        public Builder setAnchorView(View view) {
            mManager.setAnchorView(view);
            return this;
        }

        public Builder setTitle(String title) {
            mManager.setTitle(title);
            return this;
        }

    }

    public void setAnchorView(View view) {
        mAnchorView = view;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void showCenterView() {
        if (mAnchorView == null) {
            return;
        }
        Activity activity = (Activity) mAnchorView.getContext();
        mRootView = activity.findViewById(android.R.id.content);

        Rect anchorRect = new Rect();
        Rect rootViewRect = new Rect();

        //获得其在屏幕当中的位置
        mAnchorView.getGlobalVisibleRect(anchorRect);
        mRootView.getGlobalVisibleRect(rootViewRect);
        Log.e(TAG, "showCenterView: "+anchorRect.top+"   "+anchorRect.left+"  " +anchorRect.right
        +"   "+anchorRect.bottom + "    :" + rootViewRect.top+"   "+ rootViewRect.left+"   "
        +rootViewRect.right
        );

        // 创建 imageView
//        ImageView imageView = new ImageView(activity);
//        imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg_book_item));
//        mRootView.addView(imageView);

        //创建TextView
        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        TextView tvCurrent = new TextView(activity);
//                tvCurrent.setBackground(activity.getResources().getDrawable(R.drawable.bg_tv_currentlevel));
                tvCurrent.setBackground(activity.getResources().getDrawable(R.drawable.bg_current_level));
        tvCurrent.setTextSize(15);
        tvCurrent.setText("当前：等级"+userLocal.getLevel());
        tvCurrent.setTextColor(activity.getResources().getColor(R.color.btn_book_reading));
        mRootView.addView(tvCurrent);
/*
//         调整显示区域大小 如果没有这个，会大得离谱
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tvCurrent.getLayoutParams();
        params.width = 300;
        params.height = 100;
        tvCurrent.setLayoutParams(params);

 */
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tvCurrent.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // tv的父容器是LinearLayout
        tvCurrent.setLayoutParams(params);
        tvCurrent.setGravity(Gravity.CENTER);


        // 设置居中显示
//        imageView.setY(anchorRect.top - rootViewRect.top + (mAnchorView.getHeight() - 100) / 2);
//        imageView.setX(anchorRect.left + (mAnchorView.getWidth()  - 100) / 2);

        tvCurrent.setY((float) (anchorRect.top-(anchorRect.bottom-anchorRect.top)));
        tvCurrent.setX(anchorRect.right-(anchorRect.right-anchorRect.left)*4);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void showNextView(){
        if (mAnchorView == null) {
            return;
        }
        Activity activity = (Activity) mAnchorView.getContext();
        mRootView = activity.findViewById(android.R.id.content);

        Rect anchorRect = new Rect();
        Rect rootViewRect = new Rect();

        //获得其在屏幕当中的位置
        mAnchorView.getGlobalVisibleRect(anchorRect);
        mRootView.getGlobalVisibleRect(rootViewRect);


        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        int need = updateCoinsNeed[userLocal.getLevel().charAt(0)-65];
        //创建TextView
        TextView tvNext = new TextView(activity);
        tvNext.setBackground(activity.getResources().getDrawable(R.drawable.bg_next_levle));
        tvNext.setTextSize(9);

        tvNext.setText("距离升级还需"+need+"金币");
        tvNext.setTextColor(activity.getResources().getColor(R.color.white));
        mRootView.addView(tvNext);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tvNext.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // tv的父容器是LinearLayout
        tvNext.setLayoutParams(params);
        tvNext.setGravity(Gravity.CENTER);


        tvNext.setY((float) (anchorRect.top-(anchorRect.bottom-anchorRect.top)*2));
        tvNext.setX((float) ((anchorRect.right+anchorRect.left)/2.0));
    }
}
