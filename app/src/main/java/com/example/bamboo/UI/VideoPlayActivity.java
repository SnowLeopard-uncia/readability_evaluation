package com.example.bamboo.UI;

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
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.bamboo.R;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;

public class VideoPlayActivity extends BaseActivity implements View.OnClickListener {

    private VideoView video_view;
    private String url;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

//        initNavBar(true, "");
        url = getIntent().getExtras().getString("src");
        video_view = findViewById(R.id.video_view);
        Button btn_play = findViewById(R.id.btn_play);
        Button btn_pause = findViewById(R.id.btn_pause);
        Button btn_replay = findViewById(R.id.btn_replay);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_replay.setOnClickListener(this);
        video_view.setVideoURI(Uri.parse(url));
//        if (ContextCompat.checkSelfPermission(VideoPlayActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(VideoPlayActivity.this, new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        } else {
//            video_view.setVideoURI(Uri.parse(url));
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                if(!video_view.isPlaying()) {
                    video_view.start();
                }
                break;
            case R.id.btn_pause:
                if(video_view.isPlaying()) {
                    video_view.pause();
                }
                break;
            case R.id.btn_replay:
                if(video_view.isPlaying()) {
                    video_view.resume();
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