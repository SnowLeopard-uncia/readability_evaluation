package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.BookWord;
import com.snowleopard.bamboo.javaBean.UserLocal;
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

public class WordPreviewActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_word;
    private TextView tv_explain;
    private TextView tv_pronunciation;
    private ImageView iv_toLeft;
    private ImageView iv_toRight;
    private ImageView iv_speaker;

    private List<BookWord> mBookWord = new ArrayList<>();
    private int word_num = 0;
    Integer book_id;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    String url = "";

    private String[] EnglishUrl = {"http://119.91.130.240/Edemo/my.mp3",
            "http://119.91.130.240/Edemo/mother.mp3",
            "http://119.91.130.240/Edemo/father.mp3",
            "http://119.91.130.240/Edemo/brother.mp3",
            "http://119.91.130.240/Edemo/sister.mp3",
            "http://119.91.130.240/Edemo/grandfather.mp3",
            "http://119.91.130.240/Edemo/grandmother.mp3",
            "http://119.91.130.240/Edemo/me.mp3",
            "http://119.91.130.240/Edemo/family.mp3"};

    private String[] EnglishExplain = {"adj. 我的", "n. 妈妈", "n. 爸爸", "n. 兄弟",
            "n. 姐妹", "n. (外)祖父", "n. (外)祖母", "pron. 我", "n. 家庭"};

    private String[] pronunciation = {"[maɪ]", "[ˈmʌðə(r)]", "[ˈfɑːðə(r)]", "[ˈbrʌðə(r)]",
            "[ˈsɪstə(r)]", "[ˈɡrænfɑːðə(r)]", "[ˈɡrænmʌðə(r)]", "[mi]", "[ˈfæməli]"};

    private String[] SpanishUrl = {"http://119.91.130.240/Edemo/agua.mp3",
            "http://119.91.130.240/Edemo/Oceano.mp3"};

    private String[] SpanishExplain = {"n. 水", "n. 海洋"};


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_preview);

        initNavBar(true, getResources().getString(R.string.word_preview));
        init();

        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        if (userLocal.getLanguage().equals("English")){
            try {
                getResponseDataAboutEnglish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                getResponseDataAboutSpanish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }


    private void init() {
        tv_word = findViewById(R.id.tv_word);
        tv_explain = findViewById(R.id.tv_explain);
        tv_pronunciation = findViewById(R.id.tv_pronunciation);
        iv_toLeft = findViewById(R.id.iv_toLeft);
        iv_toRight = findViewById(R.id.iv_toRight);
        iv_speaker = findViewById(R.id.iv_speaker);
        iv_toLeft.setOnClickListener(this);
        iv_toRight.setOnClickListener(this);
        iv_speaker.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toLeft:
                if (word_num > 0) {
                    word_num--;
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    UserLocal userLocal = LitePal.findFirst(UserLocal.class);
                    if (userLocal.getLanguage().equals("English")){
                        tv_word.setText(mBookWord.get(0).getWord_list().get(word_num));
                        if(book_id == 1){
                            tv_pronunciation.setText(pronunciation[word_num]);
                            tv_explain.setText(EnglishExplain[word_num]);
                            url = EnglishUrl[word_num];
                            initMediaPlayer();
                        }
                    }else{
                        tv_word.setText(mBookWord.get(0).getWord().get(word_num));
                        if(book_id == 1){
                            tv_explain.setText(SpanishExplain[word_num]);
                            url = SpanishUrl[word_num];
                            initMediaPlayer();
                        }
                    }
                } else {
                    Toast.makeText(this, "这是第一个单词", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_toRight:
                UserLocal userLocal = LitePal.findFirst(UserLocal.class);
                if (userLocal.getLanguage().equals("English")){
                    if (word_num < mBookWord.get(0).getWord_list().size() - 1) {
                        word_num++;
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                        tv_word.setText(mBookWord.get(0).getWord_list().get(word_num));
                        if(book_id == 1){
                            tv_pronunciation.setText(pronunciation[word_num]);
                            tv_explain.setText(EnglishExplain[word_num]);
                            url = EnglishUrl[word_num];
                            initMediaPlayer();
                        }
                    } else {
                        Toast.makeText(this, "这是最后一个单词", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if (word_num < mBookWord.get(0).getWord().size() - 1) {
                        word_num++;
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                        tv_word.setText(mBookWord.get(0).getWord().get(word_num));
                        if(book_id == 1){
                            tv_explain.setText(SpanishExplain[word_num]);
                            url = SpanishUrl[word_num];
                            initMediaPlayer();
                        }
                    } else {
                        Toast.makeText(this, "这是最后一个单词", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.iv_speaker:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
                break;
            default:
                break;
        }
    }

    private void getResponseDataAboutEnglish() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "selectWordList_byBookId";
        JSONObject params = new JSONObject();
        book_id = getIntent().getExtras().getInt("book_id");
        params.put("book_id", book_id);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataAboutEnglish(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataAboutEnglish(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<BookWord>> responseQuizList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<BookWord>>>() {
                }.getType());
        List<BookWord> dataResponseList = responseQuizList.getResults();
        for (BookWord bookWord : dataResponseList) {
            mBookWord.add(bookWord);
        }
        tv_word.setText(mBookWord.get(0).getWord_list().get(word_num));

        if(book_id == 1){
            tv_pronunciation.setText(pronunciation[word_num]);
            tv_explain.setText(EnglishExplain[word_num]);
            url = EnglishUrl[word_num];
            initMediaPlayer();
        }

    }

    private void getResponseDataAboutSpanish() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "selectSpanishWordList_byBookId";
        JSONObject params = new JSONObject();
        book_id = getIntent().getExtras().getInt("book_id");
        params.put("book_id", book_id);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataAboutSpanish(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataAboutSpanish(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<BookWord>> responseQuizList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<BookWord>>>() {
                }.getType());
        List<BookWord> dataResponseList = responseQuizList.getResults();
        for (BookWord bookWord : dataResponseList) {
            mBookWord.add(bookWord);
        }
        tv_word.setText(mBookWord.get(0).getWord().get(word_num));

        if(book_id == 1){
            tv_explain.setText(SpanishExplain[word_num]);
            url = SpanishUrl[word_num];
            initMediaPlayer();
        }
    }



    private void initMediaPlayer(){
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


}