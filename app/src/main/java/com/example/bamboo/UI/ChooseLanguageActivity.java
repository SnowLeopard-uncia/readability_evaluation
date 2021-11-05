package com.example.bamboo.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.bamboo.R;
import com.example.bamboo.Util.DataUtil;
import com.example.bamboo.fragment.ui.adapter.ViewPaperAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.util.V;

public class ChooseLanguageActivity extends BaseActivity {
    private ViewPager chooseViewPager;
    private int imgArray[]={
            R.drawable.bg_choose_english,R.drawable.bg_choose_spanish
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        init();
    }

    private void init() {
        chooseViewPager=findViewById(R.id.vp_choose);
        ViewPaperAdapter adapter = new ViewPaperAdapter(this, DataUtil.getViewpagerInfo(this,imgArray));
       chooseViewPager.setAdapter(adapter);
    }

}