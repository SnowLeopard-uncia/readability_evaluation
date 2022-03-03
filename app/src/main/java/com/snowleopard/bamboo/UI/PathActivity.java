package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.javaBean.UserLocal;
import com.snowleopard.bamboo.view.FloatingManager;

import org.litepal.LitePal;

import java.util.List;

public class PathActivity extends BaseActivity {

private ImageView currentLevel;
private ImageView nextLevel;

private int[] imgId={
    R.id.iv_a, R.id.iv_b, R.id.iv_c, R.id.iv_d, R.id.iv_e, R.id.iv_f, R.id.iv_g,
        R.id.iv_h, R.id.iv_i, R.id.iv_j, R.id.iv_k, R.id.iv_l, R.id.iv_m,
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_path);
        initNavBar(true, "成长轨迹");

//        initView();

    }

    private void initView() {
        List<UserLocal> userLocalList = LitePal.findAll(UserLocal.class);
        UserLocal userLocal = userLocalList.get(0);
        int index = userLocal.getLevel().charAt(0)-65;
        Log.e(TAG, "initView: index"+index );
        currentLevel = findViewById(imgId[index]);
        currentLevel.setVisibility(View.VISIBLE);
        if(index==imgId.length-1){
            currentLevel.setImageResource(R.drawable.current_level);
        }else{
      nextLevel=findViewById(imgId[index+1]);
      nextLevel.setVisibility(View.VISIBLE);

      currentLevel.setImageResource(R.drawable.current_level);
      nextLevel.setImageResource(R.drawable.next_level);
        }

        currentLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果写在oncreate 视图还没完全加载出来，获取的坐标都是0

                if (index ==imgId.length){
                    showContentView(currentLevel);
                }else{
                    showContentView(currentLevel);
                    showNextView(nextLevel);
                }

            }
        });

    }

    private void showNextView(View view) {
        FloatingManager.Builder builder = FloatingManager.getBuilder();
        builder.setAnchorView(view);
        FloatingManager manager = builder.build();
        manager.showNextView();
    }

    private void showContentView(View view) {
        FloatingManager.Builder builder = FloatingManager.getBuilder();
        builder.setAnchorView(view);
        FloatingManager manager = builder.build();
        manager.showCenterView();
    }
}