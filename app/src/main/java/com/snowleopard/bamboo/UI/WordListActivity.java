package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.fragment.ui.adapter.WordListAdapter;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.UserLocal;
import com.snowleopard.bamboo.javaBean.WordList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class WordListActivity extends BaseActivity {
    private String level;
    private String wordNum;
    private TextView tvLevel;
    private TextView tvWordNum;
    private List<WordList> mWordLists=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        initNavBar(true,"分级词汇");
        initView();
//        textRecyclerView();
        try {
            getDataFromIntent();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        initView();

    }

    private void textRecyclerView() {
        for (int i = 0; i < 3; i++) {
            WordList wordList = new WordList("hello","/asd/","/sdf/","你好");
            WordList wordList1 = new WordList("hi","/asd/","/sdf/","你好");
            WordList wordList2 = new WordList("bye","/asd/","/sdf/","你好");
            mWordLists.add(wordList);
            mWordLists.add(wordList1);
            mWordLists.add(wordList2);
        }

    }

    private void initView() {
        tvLevel=findViewById(R.id.tv_word_level);
        tvWordNum=findViewById(R.id.tv_word_num);
    }

    private void getDataFromIntent() throws JSONException {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        level=data.getString("level");
        wordNum=data.getString("wordNum");
        tvWordNum.setText(wordNum);
        tvLevel.setText(level);
        Log.e(TAG, "getDataFromIntent: "+level );
        getResponseData(level);
    }

    private void getResponseData(String level) throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        JSONObject params= new JSONObject();
        params.put("level",level);
//        Log.e(TAG, "getResponseData: params"+params.toString());
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第三个参数是回调类
        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        if(userLocal.getLanguage().equals("English")){
            ace.callEndpoint("wordBookInfo", params, new CloudCodeListener() {
                @Override
                public void done(Object object, BmobException e) {
                    if (e == null) {
                        String result = object.toString();
                        parseJsonDataWithGson(result);
                        Log.e("WordListActivity", "wordBookInfo: "+result );
                        Log.e(TAG, "获取单词成功英语");

                    } else {
                        Log.e(TAG, "执行错误" + e.getMessage());
                    }
                }
            });
        }else{
            ace.callEndpoint("wordBookInfo_spanish", params, new CloudCodeListener() {
                @Override
                public void done(Object object, BmobException e) {
                    if (e == null) {
                        String result = object.toString();
                        parseJsonDataWithGson(result);
                        Log.e(TAG, "获取单词成功西语");
                    } else {
                        Log.e(TAG, "执行错误" + e.getMessage());
                    }
                }
            });
        }


    }
    private void parseJsonDataWithGson(String responseData) {
        Gson gson = new Gson();
        BaseResponse<List<WordList>> response = gson.fromJson(responseData, new TypeToken<BaseResponse<List<WordList>>>() {
        }.getType());
        List<WordList> responseWordList = response.getResults();

        mWordLists.addAll(responseWordList);
        initRecyclerView();
//        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_word_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        WordListAdapter adapter = new WordListAdapter(mWordLists);
        recyclerView.setAdapter(adapter);
    }

    /*
    嵌套滑动部分

    private void initRecyclerView_pre() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_word_list);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e!=null){
                    //找到被点击位置的item的rootView
                    View view = rv.findChildViewUnder(e.getX(),e.getY());
                    if (view!=null){
                        //通过rootView找到对应的ViewHolder
                        WordListAdapter.ViewHolder holder = (WordListAdapter.ViewHolder) rv.getChildViewHolder(view);
                        //由ViewHolder决定要不要请求不拦截,如果不拦截的话event就回一路传到rootView中.否则被rv消费.
                        rv.requestDisallowInterceptTouchEvent(holder.isTouchNsv(e.getRawX(),e.getRawY()));
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        WordListAdapter adapter = new WordListAdapter(mWordLists);
        recyclerView.setAdapter(adapter);

    }  */

}