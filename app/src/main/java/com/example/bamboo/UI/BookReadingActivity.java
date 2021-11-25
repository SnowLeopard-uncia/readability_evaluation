package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookContent;
import com.example.bamboo.javaBean.BookWord;
import com.example.bamboo.javaBean.UserLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class BookReadingActivity extends BaseActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ImageView iv_book;
    private ImageView iv_back;
    private ImageView iv_toLeft;
    private ImageView iv_toRight;
    private ImageView iv_speaker;

    private List<BookContent> mBookContent = new ArrayList<>();
    private int page_num = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reading);

        iv_book = findViewById(R.id.iv_book);
        iv_back = findViewById(R.id.iv_back);
        iv_toLeft = findViewById(R.id.iv_toLeft);
        iv_toRight = findViewById(R.id.iv_toRight);
        iv_speaker = findViewById(R.id.iv_speaker);
        iv_back.setOnClickListener(this);
        iv_toLeft.setOnClickListener(this);
        iv_toRight.setOnClickListener(this);
        iv_speaker.setOnClickListener(this);

        Log.e(TAG, "page_num:" + page_num);


        try {
            getResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        if (page_num == 0) {
//            iv_toRight.setBackgroundResource(R.drawable.next_page);
//
//        } else {
//            iv_toLeft.setBackgroundResource(R.drawable.last_page);
//            iv_toRight.setBackgroundResource(R.drawable.next_page);
//            iv_speaker.setBackgroundResource(R.drawable.book_reading_speaker);
//        }


//        initMediaPlayer();

//        Button btn_play = findViewById(R.id.btn_play);
//        Button btn_pause = findViewById(R.id.btn_pause);
//        btn_play.setOnClickListener(this);
//        btn_pause.setOnClickListener(this);

    }

//    private void initMediaPlayer(){
//        try {
//            mediaPlayer.setDataSource(this, uri);
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toLeft:
                if (page_num > 1) {
                    page_num--;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    initPage();
                    iv_toLeft.setBackgroundResource(R.drawable.last_page);
                    iv_toRight.setBackgroundResource(R.drawable.next_page);
                    iv_speaker.setBackgroundResource(R.drawable.book_reading_speaker);
                }
                if (page_num == 1) {
                    page_num--;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    Glide.with(this).load(mBookContent.get(page_num).getP()).into(iv_book);
                    iv_toLeft.setBackgroundResource(0);
                    iv_speaker.setBackgroundResource(0);
                }
                break;
            case R.id.iv_toRight:
                if (page_num < mBookContent.size() - 2) {
                    page_num++;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    initPage();
                    iv_toLeft.setBackgroundResource(R.drawable.last_page);
                    iv_toRight.setBackgroundResource(R.drawable.next_page);
                    iv_speaker.setBackgroundResource(R.drawable.book_reading_speaker);
                }
                if (page_num == mBookContent.size() - 2) {
                    page_num++;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    Glide.with(this).load(mBookContent.get(page_num).getP()).into(iv_book);
                    iv_toRight.setBackgroundResource(0);
                    iv_speaker.setBackgroundResource(0);
                }

                break;
            case R.id.iv_speaker:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
                break;
            case R.id.iv_back:
                mediaPlayer.stop();
                mediaPlayer.release();
                onBackPressed();
                break;
            default:
                break;
        }

    }

    private void getResponseData() throws JSONException {
        UserLocal userLocal = LitePal.findFirst(UserLocal.class);

        String cloudCodeName = "";
        if (userLocal.getLanguage().equals("English")){
            cloudCodeName = "selectBookContent_byId";
        }else{
            cloudCodeName = "selectSpanishBookContent_byId";
        }

        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        JSONObject params = new JSONObject();
        Integer book_id = getIntent().getExtras().getInt("book_id");
        params.put("book_id", book_id);
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

        BaseResponse<List<BookContent>> responsePageList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<BookContent>>>() {
                }.getType());
        List<BookContent> dataResponseList = responsePageList.getResults();
        mBookContent.clear();
        mBookContent.addAll(dataResponseList);
        Glide.with(this).load(mBookContent.get(page_num).getP()).into(iv_book);
        iv_toRight.setBackgroundResource(R.drawable.next_page);

    }

    private void initPage() {
        Glide.with(this).load(mBookContent.get(page_num).getP()).into(iv_book);
//        Uri uri = Uri.parse(mBookContent.get(page_num).getV());
        String url = mBookContent.get(page_num).getV();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//    }

}