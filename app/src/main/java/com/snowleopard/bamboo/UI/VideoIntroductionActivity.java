package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.UserLocal;
import com.snowleopard.bamboo.javaBean.VideoIntroduction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class VideoIntroductionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBook;
    private TextView tvBookTitle;
    private TextView tvDirector;
    private TextView tvLevel;
    private TextView tvSuitAge;
    private TextView tvTheme;
    private TextView tvBriefIntroduction;
    private TextView tvShowMore;
    private Button btnEnter;
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
        ivBook = findViewById(R.id.iv_book);
        tvBookTitle = findViewById(R.id.tv_book_title);
        tvDirector = findViewById(R.id.tv_director);
        tvLevel = findViewById(R.id.tv_level);
        tvSuitAge = findViewById(R.id.tv_suit_age);
        tvTheme = findViewById(R.id.tv_theme);
        tvBriefIntroduction = findViewById(R.id.tv_brief_introduction);
        tvShowMore = findViewById(R.id.tv_show_more);
        btnEnter = findViewById(R.id.btn_enter);
        btnEnter.setOnClickListener(this);
        tvShowMore.setOnClickListener(this);
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
        UserLocal userLocal = LitePal.findFirst(UserLocal.class);

        String cloudCodeName = "";
        if (userLocal.getLanguage().equals("English")){
            cloudCodeName = "playVideo";
        }else{
            cloudCodeName = "playVideo_Spanish";
        }

        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        JSONObject params = new JSONObject();
        params.put("videoID", videoID);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//???????????????????????????????????????????????????????????????????????????????????????????????????JSONObject cloudCodeParams???
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e("VideoIntroduction", "done: json???" + responseData);
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
        videoList.clear();
        for (VideoIntroduction videoIntroduction : dataResponseList) {
            videoList.add(videoIntroduction);

            tvBookTitle.setText("?????????" + videoIntroduction.getVideoName());
            tvDirector.setText("?????????" + videoIntroduction.getAuthor());
            tvLevel.setText("?????????" + videoIntroduction.getVideoLevel());
            tvSuitAge.setText("???????????????" + videoIntroduction.getVideoAge());
            tvTheme.setText("?????????" + videoIntroduction.getTopic());
            tvBriefIntroduction.setText("?????????" + videoIntroduction.getIntroduce());
            Glide.with(this).load(videoIntroduction.getPng()).into(ivBook);
//            Log.e(TAG, "parseJson: " + videoIntroduction.getSrc());

        }

    }


    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder((VideoIntroductionActivity.this));
        View dialogView = View.inflate(this, R.layout.dialog, null); // ???Dialog?????????????????????
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create(); // ???????????????
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView close = dialogView.findViewById(R.id.iv_close);
        TextView tv_content = dialogView.findViewById(R.id.tv_content);
        tv_content.setText("  " + videoList.get(0).getIntroduce());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCanceledOnTouchOutside(false); // ???????????????????????????????????????????????????????????????????????????????????????????????????
        alertDialog.show(); // ???????????????
    }


}