package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.snowleopard.bamboo.R;

public class VideoPlayActivity extends BaseActivity implements View.OnClickListener {

    private VideoView video_view;
    private String url;
    private Button btn_play;
    private Button btn_pause;
    private Button btn_replay;
    private ImageView iv_back;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        url = getIntent().getExtras().getString("src");
        Log.e(TAG, "视频播放页面视频路径： " + url);
        video_view = findViewById(R.id.video_view);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_replay = findViewById(R.id.btn_replay);
        iv_back = findViewById(R.id.iv_back);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_replay.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        video_view.setVideoURI(Uri.parse(url));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                if (!video_view.isPlaying()) {
                    video_view.start();
                    btn_play.setBackgroundResource(R.drawable.video_play_select);
                    btn_pause.setBackgroundResource(R.drawable.video_pause);
                    btn_replay.setBackgroundResource(R.drawable.video_replay);
                }
                break;
            case R.id.btn_pause:
                if (video_view.isPlaying()) {
                    video_view.pause();
                    btn_pause.setBackgroundResource(R.drawable.video_pause_select);
                    btn_play.setBackgroundResource(R.drawable.video_play);
                    btn_replay.setBackgroundResource(R.drawable.video_replay);
                }
                break;
            case R.id.btn_replay:
                if (video_view.isPlaying()) {
                    video_view.resume();
                    btn_replay.setBackgroundResource(R.drawable.bg_btn_video_replay);
                    btn_play.setBackgroundResource(R.drawable.video_play_select);
                }
            case R.id.iv_back:
                if (video_view != null) {
                    video_view.suspend();
                }
                onBackPressed();
                break;
            default:
                break;
        }


    }


}