package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.bamboo.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class AudioActivity extends BaseActivity {

    private String audioId;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        init();
        try {
            dataFromAudioList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void dataFromAudioList() throws JSONException {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        audioId=data.getString("musicSelectedID");
        getDataFromResponse();
    }

    private void init() {
        initNavBar(true,"");
    }
    private void getDataFromResponse() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        JSONObject params= new JSONObject();
        params.put("musicSelectedID", audioId);
        ace.callEndpoint("playMusic", params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    parseJsonDataWithGson(result);
                    Log.e(TAG, "audio done: "+result );
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }

    private void parseJsonDataWithGson(String result) {

    }

}