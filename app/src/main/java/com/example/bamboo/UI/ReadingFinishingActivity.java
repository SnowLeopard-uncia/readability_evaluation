package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamboo.R;

public class ReadingFinishingActivity extends BaseActivity {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_finishing);

        initNavBar(true, "");
        TextView tv_coin = findViewById(R.id.tv_coin);
        int coin = getIntent().getExtras().getInt("gold_coin");
        tv_coin.setText(""+coin);

    }
}


