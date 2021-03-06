package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.Util.HttpUtils;
import com.snowleopard.bamboo.fragment.ui.adapter.ResultAdapter;
import com.snowleopard.bamboo.javaBean.LevelText;
import com.snowleopard.bamboo.javaBean.ResultData;
import com.snowleopard.bamboo.javaBean.TextResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/*
测评结果页面
一开始我是在前一个Activity测评完再把结果带过来跳转显示
后面改成了把数据带过来跳转再测评
我觉得还是后面的好，因为用前面的方法的话有三个Activity要写测评代码，后面这个只用一个Activity
 */
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
    private List<ResultData> mResultList = new ArrayList<>();

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

    private void initRecyclerView() {
        /*
            ResultData resultData = new ResultData(1.21,5.88,1.5,"深度语义",null);
        ResultData resultData1 = new ResultData(0.57,1.77,0.7,"树结构",null);
            ResultData resultData2 = new ResultData(0.94,0.96,0.95,"实体数量",null);
        ResultData resultData3 = new ResultData(0.95,4.85,1.0,"词汇语义",null);
            ResultData resultData4 = new ResultData(4.17,9.43,5.0,"浅层特征",null);
        ResultData resultData5 = new ResultData(null,null,null,null,"1");
            mResultList.add(resultData);
        mResultList.add(resultData1);
            mResultList.add(resultData2);
        mResultList.add(resultData3);
            mResultList.add(resultData4);
        mResultList.add(resultData5);

         */

        RecyclerView recyclerView = findViewById(R.id.rv_result);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        ResultAdapter adapter = new ResultAdapter(mResultList);
        recyclerView.setAdapter(adapter);
        //实现viewpager效果
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
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
//                    results=results+result;
//                runOnUiThread(()->{
////                    tv_result.setText(results);
//                    Toast.makeText(getApplicationContext(),"测评成功",Toast.LENGTH_SHORT).show();
//                });
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
//                    results=results+levelText.toString();
                mResultList.set(mResultList.size()-1,new ResultData(null,null,null,null,levelText.getGrade()));
                temp++;
                Message msg =Message.obtain();
                msg.what=temp;
                handler.sendMessage(msg);
                temp--;
                runOnUiThread(()->{
//                    tv_result.setText(results);
                    Log.e(TAG, "onResponse: "+"等级"+levelText.getGrade() );
                    Toast.makeText(getApplicationContext(),"等级显示",Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    private void initView() {
        initNavBar(true,getResources().getString(R.string.result_title));
//        tv_result=findViewById(R.id.tv_result);  因为改变了展示方式
        et_text=findViewById(R.id.et_text);
        btn_evaluation_again=findViewById(R.id.btn_evaluation_again);
        et_text.setText(text);
//        tv_result.setText(result);

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
                    temp--;
                }).start();
            }
        });

    }
    private String parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        TextResponse textResponse = gson.fromJson(jsonData, new TypeToken<TextResponse>() {
        }.getType());

        mResultList.add(new ResultData(1.21,5.88,Double.parseDouble(textResponse.getWoKF().getWRich05_S()),"深度语义",null));
        mResultList.add(new ResultData(0.57,1.77,Double.parseDouble(textResponse.getTrSF().getAt_FTree_C()),"树结构",null));
        mResultList.add(new ResultData(0.625,1.0,Double.parseDouble(textResponse.getEnGF().getRa_NNTo_C()),"实体数量",null));
        mResultList.add(new ResultData(0.95,4.85,Double.parseDouble(textResponse.getPsyF().getAt_AABiL_C()),"词汇语义",null));
        mResultList.add( new ResultData(4.17,9.43,Double.parseDouble(textResponse.getShaF().getAt_Chara_C()),"浅层特征",null));
        mResultList.add(new ResultData(null,null,null,null,"1"));
        //等级不能是null，不然会报错

        temp++;
        Message msg =Message.obtain();
        msg.what=temp;
        handler.sendMessage(msg);
        /*
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

         */
//        String result ="BClar05_S: "+textResponse.getOSKF().getBClar05_S()+
//                "\nAs_ContW_C: "+textResponse.getPOSF().getAs_ContW_C()+
//                "\nAs_AABiL_C: "+textResponse.getPsyF().getAs_AABiL_C()+
//                "\nTokSenL_S: "+textResponse.getShaF().getTokSenL_S();
//        return result;
        return null;
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
//                    tv_result.setText(results);
                    initRecyclerView();
                    Toast.makeText(getApplicationContext(),"测评成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}