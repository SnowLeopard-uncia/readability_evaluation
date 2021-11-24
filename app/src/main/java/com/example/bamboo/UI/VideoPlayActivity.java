package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.bamboo.R;

import org.reactivestreams.Subscriber;

import java.io.IOException;

import io.reactivex.Observable;

public class VideoPlayActivity extends BaseActivity implements View.OnClickListener {

    private VideoView video_view;
    private String url;
    Button btn_play;
    Button btn_pause;
    Button btn_replay;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        initNavBar(true, "");
        url = getIntent().getExtras().getString("src");
        Log.e(TAG, "视频播放页面视频路径： " + url);
        video_view = findViewById(R.id.video_view);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_replay = findViewById(R.id.btn_replay);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_replay.setOnClickListener(this);

        video_view.setVideoURI(Uri.parse(url));
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                if(!video_view.isPlaying()) {
                    video_view.start();
                    btn_play.setBackgroundResource(R.drawable.video_play_select);
                    btn_pause.setBackgroundResource(R.drawable.video_pause);
                    btn_replay.setBackgroundResource(R.drawable.video_replay);
                }
                break;
            case R.id.btn_pause:
                if(video_view.isPlaying()) {
                    video_view.pause();
                    btn_pause.setBackgroundResource(R.drawable.video_pause_select);
                    btn_play.setBackgroundResource(R.drawable.video_play);
                    btn_replay.setBackgroundResource(R.drawable.video_replay);
                }
                break;
            case R.id.btn_replay:
                if(video_view.isPlaying()) {
                    video_view.resume();
                    btn_replay.setBackgroundResource(R.drawable.bg_btn_video_replay);
                    btn_play.setBackgroundResource(R.drawable.video_play_select);
                }
                break;
        }
//        @Override
//        protected void onDestroy(){
//            super.onDestroy();
//            if(video_view != null) {
//                video_view.suspend();
//            }
//        }

    }




}