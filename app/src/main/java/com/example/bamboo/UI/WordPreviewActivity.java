package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookWord;
import com.example.bamboo.javaBean.QuizList;
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

public class WordPreviewActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_word;
    private ImageView iv_toLeft;
    private ImageView iv_toRight;

    private List<BookWord> mBookWord = new ArrayList<>();
    private int word_num = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_preview);

        initNavBar(true, getResources().getString(R.string.word_preview));
        init();

        try {
            getResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void init() {
        tv_word = findViewById(R.id.tv_word);
        iv_toLeft = findViewById(R.id.iv_toLeft);
        iv_toRight = findViewById(R.id.iv_toRight);
        iv_toLeft.setOnClickListener(this);
        iv_toRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toLeft:
                if (word_num > 0) {
                    word_num--;
                    tv_word.setText(mBookWord.get(0).getWord_list().get(word_num));
                } else {
                    Toast.makeText(this, "这是第一个单词", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_toRight:
                if (word_num < mBookWord.get(0).getWord_list().size() - 1) {
                    word_num++;
                    tv_word.setText(mBookWord.get(0).getWord_list().get(word_num));
                } else {
                    Toast.makeText(this, "这是最后一个单词", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    private void getResponseData() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "selectWordList_byBookId";
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

        BaseResponse<List<BookWord>> responseQuizList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<BookWord>>>() {
                }.getType());
        List<BookWord> dataResponseList = responseQuizList.getResults();
        for (BookWord bookWord : dataResponseList) {
            mBookWord.add(bookWord);
        }
        tv_word.setText(mBookWord.get(0).getWord_list().get(word_num));

    }

}