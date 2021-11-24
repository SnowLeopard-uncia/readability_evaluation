package com.example.bamboo.UI;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.Util.HttpUtils;
import com.example.bamboo.javaBean.LevelText;
import com.example.bamboo.javaBean.TextResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultEvaluationActivity extends BaseActivity {

    private TextView tv_result;
    private EditText et_text;
    private String text;
    private String result;
    private Button btn_evaluation_again;
    private final String url="http://8.134.49.78:5000";
    private final String levelUrl="http://8.134.49.78:8081";
    private int temp =0;
    private String results="";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_evaluation);
        getDataFromIntent();
        initView();
        initNavBar(true,"可读性测评");

        btn_evaluation_again.setOnClickListener((view -> {
            String mText = et_text.getText().toString();
            if (!mText.equals("")){
                new Thread(()->{
//                    startToEvaluate(mText,url);
                    goToEvaluate(mText,url,levelUrl);
                }).start();
                Toast.makeText(this,"测评中",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"文本为空，请重新输入",Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void getDataFromIntent() {
        Intent intent =getIntent();
        Bundle bundle =intent.getExtras();
         text = bundle.getString("text");
         result = bundle.getString("result");
         new Thread(()->{
             goToEvaluate(text,url,levelUrl);
         }).start();

    }

    private void goToEvaluate(String text, String url, String levelUrl) {
        HttpUtils.readabilityWithOkhttp(url, text, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody=response.body().string();
                    String result = parseJsonDataWithGson(responseBody);
                    results=results+result;
                runOnUiThread(()->{
                    tv_result.setText(results);
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                });
            }
        });

        HttpUtils.readabilityWithOkhttp(levelUrl, text, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody=response.body().string();

                    Gson gson = new Gson();
                    LevelText levelText = gson.fromJson(responseBody, new TypeToken<LevelText>() {
                    }.getType());
                    results=results+levelText.toString();
                runOnUiThread(()->{
                    tv_result.setText(results);
                    Toast.makeText(getApplicationContext(),"等级显示",Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    private void initView() {
        initNavBar(true,getResources().getString(R.string.result_title));
        tv_result=findViewById(R.id.tv_result);
        et_text=findViewById(R.id.et_text);
        btn_evaluation_again=findViewById(R.id.btn_evaluation_again);
        et_text.setText(text);
        tv_result.setText(result);

    }

    public void startToEvaluate(String mText,String url){
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
                    temp++;
                    Message msg =Message.obtain();//从全局池中返回一个message实例，避免多次创建message（如new Message
                    msg.what=temp;
                    handler.sendMessage(msg);
                }).start();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String result = parseJsonDataWithGson(responseBody);
//
////                        tv_result.setText(result);
////                        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
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
    private String parseJsonDataWithGson(String jsonData) {
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
//        String result ="BClar05_S: "+textResponse.getOSKF().getBClar05_S()+
//                "\nAs_ContW_C: "+textResponse.getPOSF().getAs_ContW_C()+
//                "\nAs_AABiL_C: "+textResponse.getPsyF().getAs_AABiL_C()+
//                "\nTokSenL_S: "+textResponse.getShaF().getTokSenL_S();
        return result;

    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 2:
                    tv_result.setText(results);
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}