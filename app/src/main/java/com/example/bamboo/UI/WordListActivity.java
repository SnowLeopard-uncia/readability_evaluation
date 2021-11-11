package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.WordListAdapter;
import com.example.bamboo.fragment.ui.adapter.WordMenuAdapter;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.WordList;
import com.example.bamboo.javaBean.WordMenuList;
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

public class WordListActivity extends BaseActivity {
    String level;
    private List<WordList> mWordLists=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        initNavBar(true,"分级词汇");
        try {
            getDataFromIntent();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        initView();

    }

    private void initView() {
       WordList wordList=new WordList("hi");
       mWordLists.add(wordList);
        WordList wordList1=new WordList("hii");
        mWordLists.add(wordList1);
        WordList wordList2=new WordList("hiii");
        mWordLists.add(wordList2);
    }

    private void getDataFromIntent() throws JSONException {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        level=data.getString("level");
        Log.e(TAG, "getDataFromIntent: "+level );
        getResponseData(level);
    }

    private void getResponseData(String level) throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        JSONObject params= new JSONObject();
        params.put("level",level);
        Log.e(TAG, "getResponseData: params"+params.toString());
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第三个参数是回调类
        ace.callEndpoint("wordBookInfo", params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    parseJsonDataWithGson(result);
                    Log.e(TAG, "获取单词成功"+result);
                } else {
                    Log.e(TAG, "执行错误" + e.getMessage());
                }
            }
        });

    }
    private void parseJsonDataWithGson(String responseData) {
        Gson gson = new Gson();
        BaseResponse<List<WordList>> response = gson.fromJson(responseData, new TypeToken<BaseResponse<List<WordList>>>() {
        }.getType());
        List<WordList> responseWordList = response.getResults();
        for (WordList wordList:responseWordList){
            mWordLists.add(wordList);
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_word_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        WordListAdapter adapter = new WordListAdapter(mWordLists);
        recyclerView.setAdapter(adapter);

    }

}