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

    private VideoView videoView;
    private String url;
    private Button btnPlay;
    private Button btnPause;
    private Button btnReplay;
    private ImageView ivBack;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        url = getIntent().getExtras().getString("src");
        Log.e(TAG, "视频播放页面视频路径： " + url);
        videoView = findViewById(R.id.video_view);
        btnPlay = findViewById(R.id.btn_play);
        btnPause = findViewById(R.id.btn_pause);
        btnReplay = findViewById(R.id.btn_replay);
        ivBack = findViewById(R.id.iv_back);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnReplay.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        videoView.setVideoURI(Uri.parse(url));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                if (!videoView.isPlaying()) {
                    videoView.start();
                    btnPlay.setBackgroundResource(R.drawable.video_play_select);
                    btnPause.setBackgroundResource(R.drawable.video_pause);
                    btnReplay.setBackgroundResource(R.drawable.video_replay);
                }
                break;
            case R.id.btn_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    btnPause.setBackgroundResource(R.drawable.video_pause_select);
                    btnPlay.setBackgroundResource(R.drawable.video_play);
                    btnReplay.setBackgroundResource(R.drawable.video_replay);
                }
                break;
            case R.id.btn_replay:
                if (videoView.isPlaying()) {
                    videoView.resume();
                    btnReplay.setBackgroundResource(R.drawable.bg_btn_video_replay);
                    btnPlay.setBackgroundResource(R.drawable.video_play_select);
                }
            case R.id.iv_back:
                if (videoView != null) {
                    videoView.suspend();
                }
                onBackPressed();
                break;
            default:
                break;
        }


    }


}