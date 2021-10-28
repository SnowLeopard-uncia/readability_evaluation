package com.example.bamboo.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.bamboo.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseLanguageActivity extends BaseActivity {
   private ViewPager chooseViewPager;
   private List<View> mPages;
   private int[] imgArray;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        init();
    }

    private void init() {
        chooseViewPager=findViewById(R.id.vp_choose);
        imgArray= new int[]{R.drawable.bg_choose_english, R.drawable.bg_choose_spanish};
        mPages=new ArrayList<View>();
        for (int i = 0; i < imgArray.length; i++) {
            ImageView imageView =new ImageView(this);
            mPages.add(imageView);
            imageView.setBackgroundResource(imgArray[i]);
        }
    }


}