package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookIntroduction;
import com.example.bamboo.javaBean.VideoIntroduction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class VideoIntroductionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_book;
    private TextView tv_book_title;
    private TextView tv_director;
    private TextView tv_level;
    private TextView tv_suit_age;
    private TextView tv_theme;
    private TextView tv_brief_introduction;
    private TextView tv_show_more;
    private Button btn_enter;

    private Integer videoID;

    private List<VideoIntroduction> videoList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_introduction);

        init();
        initNavBar(true, "");

        videoID = getIntent().getExtras().getInt("videoID");

        try {
            getResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void init() {
        iv_book = findViewById(R.id.iv_book);
        tv_book_title = findViewById(R.id.tv_book_title);
        tv_director = findViewById(R.id.tv_director);
        tv_level = findViewById(R.id.tv_level);
        tv_suit_age = findViewById(R.id.tv_suit_age);
        tv_theme = findViewById(R.id.tv_theme);
        tv_brief_introduction = findViewById(R.id.tv_brief_introduction);
        tv_show_more = findViewById(R.id.tv_show_more);
        btn_enter = findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);
        tv_show_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enter:
                Intent intent = new Intent(VideoIntroductionActivity.this, VideoPlayActivity.class);
                intent.putExtra("src", videoList.get(0).getSrc());
                startActivity(intent);
                break;
            case R.id.tv_show_more:
                createAlertDialog();
            default:
                break;
        }
    }

    private void getResponseData() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "playVideo";
        JSONObject params = new JSONObject();
        params.put("videoID", videoID);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataWithGson(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<VideoIntroduction>> responseVideoIntroductionList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<VideoIntroduction>>>() {
                }.getType());
        List<VideoIntroduction> dataResponseList = responseVideoIntroductionList.getResults();
        for (VideoIntroduction videoIntroduction : dataResponseList) {
            videoList.add(videoIntroduction);

            tv_book_title.setText("标题：" + videoIntroduction.getVideoName());
            tv_director.setText("导演：" + videoIntroduction.getAuthor());
            tv_level.setText("等级：" + videoIntroduction.getVideoLevel());
            tv_suit_age.setText("适合年级：" + videoIntroduction.getVideoAge());
            tv_theme.setText("主题：" + videoIntroduction.getTopic());
            tv_brief_introduction.setText("简介：" + videoIntroduction.getIntroduce());
            Log.e(TAG, "parseJson: " + videoIntroduction.getSrc());

        }

    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder((VideoIntroductionActivity.this));
        View dialogView = View.inflate(this, R.layout.dialog, null); // 将Dialog的布局加载进来
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create(); // 创建对话框

        ImageView close = dialogView.findViewById(R.id.iv_close);
        TextView tv_content = dialogView.findViewById(R.id.tv_content);
        tv_content.setText("  " + videoList.get(0).getIntroduce());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

//        Window dialogWindow = alertDialog.getWindow();
//        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.30); // 高度设置为屏幕的
//        p.width = (int) (d.getWidth() * 0.83); // 宽度设置为屏幕的
//        dialogWindow.setAttributes(p);

        alertDialog.setCanceledOnTouchOutside(false); // 点击对话框外部区域不允许对话框消失，点击手机返回按键允许对话框消失
        alertDialog.show(); // 显示对话框
    }


}