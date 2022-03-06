package com.snowleopard.bamboo.UI;

import androidx.annotation.RequiresApi;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.snowleopard.bamboo.R;

public class PhotoLoadEvaluationActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_result;
    private Button btn_pop_load;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_load);
        initNavBar(true,getResources().getString(R.string.text_title));
        initView();
    }




    private void initView() {
        btn_result=findViewById(R.id.btn_photo_score);
        btn_pop_load=findViewById(R.id.btn_pop_upload);
        btn_pop_load.setOnClickListener(this);
        btn_result.setOnClickListener((view)->{
            Intent intent = new Intent(PhotoLoadEvaluationActivity.this,ResultEvaluationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_pop_upload) {
            showBottomDialog();
        }
    }

    private void showBottomDialog() {
        //1使用dialog 设置style
        final Dialog dialog = new Dialog(this,R.style.PopStyleTheme);
        //设置布局
        View view = View.inflate(this,R.layout.dialog_pop_layout,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置 这里为底部
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.pop_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.tv_take_photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

    }
}