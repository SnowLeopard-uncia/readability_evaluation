package com.snowleopard.bamboo.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snowleopard.bamboo.R;

import org.litepal.LitePal;

import cn.bmob.v3.Bmob;

public class BaseActivity extends AppCompatActivity {
    private ImageView iv_back;
    private TextView tv_title;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
//        ActionBar actionBar;
//        actionBar=getSupportActionBar();
//        actionBar.hide();
        getWindow().setStatusBarColor(getColor(R.color.status_background));
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        LitePal.getDatabase();
    }

    public void initNavBar(boolean isShowBack, String titles) {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);

        iv_back.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        //如果isShowBack是true就显示，如果false就不显示
        tv_title.setText(titles);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}