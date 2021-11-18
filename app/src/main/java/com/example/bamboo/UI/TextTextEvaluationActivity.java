package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.Util.HttpUtils;
import com.example.bamboo.javaBean.LevelText;
import com.example.bamboo.javaBean.TextResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TextTextEvaluationActivity extends BaseActivity {

    private Button btn_result;
    private EditText et_text;
    private String TextUrl ="http://8.134.49.78:5000";
    private String levelUrl="http://8.134.49.78:8081";
    private int temp =0;
    private String results="";
   private Bundle bundle = new Bundle();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_text_evaluation);
        initView();
        initNavBar(true,"可读性测评");
    }

    private void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//防止EditText弹出键盘后顶起布局
        initNavBar(true,getResources().getString(R.string.text_title));
        btn_result=findViewById(R.id.btn_txt_score);
        et_text=findViewById(R.id.tv_text);

        btn_result.setOnClickListener((view)->{
            String mText=et_text.getText().toString();
            Log.e(TAG, "initView: "+mText );
            if (!mText.equals("")){
                startToEvaluate(mText, TextUrl,levelUrl);

                Toast.makeText(this,"测评中",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"文本为空，请重新输入",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void startToEvaluate(String mText,String url,String levelUrl) {
        Log.e(TAG, "startToEvaluate: "+mText);
        HttpUtils.readabilityWithOkhttp(url, mText, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody=response.body().string();
                new Thread(()->{
                    String result = parseJsonDataWithGson(responseBody);
                    results=results+result;
                    bundle.putString("text",mText);
                    temp++;
                    Message msg =Message.obtain();
                    msg.what=temp;
                    handler.sendMessage(msg);
                }).start();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        Log.e(TAG, "run: "+responseBody);
//                       String result = parseJsonDataWithGson(responseBody);
//                       Intent intent = new Intent(TextTextEvaluationActivity.this,ResultEvaluationActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("result",result);
//                        bundle.putString("text",mText);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
            }
        });

        HttpUtils.readabilityWithOkhttp(levelUrl, mText, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody=response.body().string();
                new Thread(()->{
                    Gson gson = new Gson();
                    LevelText levelText = gson.fromJson(responseBody, new TypeToken<LevelText>() {
                    }.getType());
                    results=results+levelText.toString();
                    temp++;
                    Message msg =Message.obtain();
                    msg.what=temp;
                    handler.sendMessage(msg);
                }).start();
            }
        });


    }
    private  String parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        TextResponse textResponse = gson.fromJson(jsonData, new TypeToken<TextResponse>() {
        }.getType());

        String result =textResponse.getEnDF().toString()+
                textResponse.getEnGF().toString()+
                textResponse.getOSKF().toString()+
                textResponse.getPhrF().toString()+
                textResponse.getPOSF().toString()+
                textResponse.getPsyF().toString()+
                textResponse.getShaF().toString()+
                textResponse.getTraF().toString()+
                textResponse.getTrSF().toString()+
                textResponse.getTTRF().toString()+
                textResponse.getVarF().toString()+
                textResponse.getWBKF().toString()+
                textResponse.getWoKF().toString()+
                textResponse.getWoLF().toString()
                ;
        Log.e(TAG, "parseJsonDataWithGson: "+result );
        return result;

    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 2:
                    Intent intent = new Intent(TextTextEvaluationActivity.this,ResultEvaluationActivity.class);
                    bundle.putString("result",results);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    };
}