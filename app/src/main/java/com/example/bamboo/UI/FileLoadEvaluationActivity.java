package com.example.bamboo.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.example.bamboo.R;

public class FileLoadEvaluationActivity extends BaseActivity {

    private Button btn_result;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_load);
        initNavBar(true,getResources().getString(R.string.text_title));
        initView();
    }

    private void initView() {
        btn_result=findViewById(R.id.btn_file_score);
        btn_result.setOnClickListener((view)->{
            Intent intent = new Intent(FileLoadEvaluationActivity.this,ResultEvaluationActivity.class);
            startActivity(intent);
        });
    }
}