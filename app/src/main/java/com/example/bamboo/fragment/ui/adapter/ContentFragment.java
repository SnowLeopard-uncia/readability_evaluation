package com.example.bamboo.fragment.ui.adapter;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.main.BookFragment;
import com.example.bamboo.fragment.ui.main.ReportsFragment;
import com.example.bamboo.fragment.ui.main.SquareFragment;
import com.example.bamboo.fragment.ui.main.TextFragment;
import com.example.bamboo.fragment.ui.main.WordFragment;

import java.util.HashMap;
import java.util.Map;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class ContentFragment {

    public static final String CLOSE = "Close";
    public static final String BOOK = "Book";
    public static final String VIDEO = "Video";
    public static final String AUDIO = "Audio";
    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;


//    public static ContentFragment newInstance(int resId) {
//        ContentFragment contentFragment = new ContentFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(Integer.class.getName(), resId);
//        contentFragment.setArguments(bundle);
//        return contentFragment;
//    }
//
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        this.containerView = view.findViewById(R.id.container);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        res = getArguments().getInt(Integer.class.getName());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);//框架中只绑定了这一个布局
//        mImageView = (ImageView) rootView.findViewById(R.id.image_content);// 碎片布局中的图片
//        mImageView.setClickable(true);
//        mImageView.setFocusable(true);
//        mImageView.setImageResource(res);
//        return rootView;
//    }
//
//    @Override
//    public void takeScreenShot() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                ContentFragment.this.bitmap = bitmap;
//            }
//        };
//
//        thread.start();
//
//    }
//
//    @Override
//    public Bitmap getBitmap() {
//        return bitmap;
//    }
}

