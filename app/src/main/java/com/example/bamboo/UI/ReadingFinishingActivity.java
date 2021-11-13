package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.QuizList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class ReadingFinishingActivity extends BaseActivity {

    String book_objectId;
    String objectId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_finishing);

        initNavBar(true, "");
        TextView tv_coin = findViewById(R.id.tv_coin);
        int coin = getIntent().getExtras().getInt("gold_coin");
        book_objectId = getIntent().getExtras().getString("book_objectId");
        tv_coin.setText("" + coin);
        getUserID();

        try {
            updateCoin();
            updateNum();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updateCoin() throws JSONException {

        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "coinUpdate";
        JSONObject params = new JSONObject();
        params.put("book_objectId", book_objectId);
        Log.e(TAG, "金币更新请求参数book_objectId：" + book_objectId);
        params.put("objectId", objectId);
        Log.e(TAG, "金币更新请求参数objectId：" + objectId);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    Log.e(TAG, "金币更新done: json：" + result);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });
    }

    public void updateNum() throws JSONException {

        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "bookAndwordNumUpdate";
        JSONObject params = new JSONObject();
        params.put("objectId", book_objectId);
        Log.e(TAG, "书本量、词汇量更新请求参数objectId：" + book_objectId);
        params.put("UserObjectId", objectId);
        Log.e(TAG, "书本量、词汇量更新请求参数UserObjectId：" + objectId);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    Log.e(TAG, "书本量、词汇量更新done: json：" + result);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });
    }

    private void getUserID() {
        SharedPreferences pref = getSharedPreferences("userInformation", MODE_PRIVATE);
        objectId = pref.getString("userID", "");
    }
}


