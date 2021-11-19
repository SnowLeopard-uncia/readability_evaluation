package com.example.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.WordMenuAdapter;
import com.example.bamboo.javaBean.BaseResponse;
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

public class WordFragment extends Fragment {

    private List<WordMenuList> wordMenuLists = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        try {
            getResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        init();
//        initRecyclerView(); 放在这里不行
    }
//test
    private void init() {
        WordMenuList wordMenuList = new WordMenuList("12","A","什么标题都系"
        ,"200","word101");
        wordMenuLists.add(wordMenuList);
        WordMenuList wordMenuList2 = new WordMenuList("13","B","什么标题"
                ,"20","word102");
        wordMenuLists.add(wordMenuList2);
    }


    private void getResponseData() throws JSONException {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        JSONObject params= new JSONObject();
        params.put(null, null);
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第三个参数是回调类
        ace.callEndpoint("selectVocabulary", params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
//                    Log.e(TAG, "done: json："+responseData);
                    parseJsonDataWithGson(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        BaseResponse<List<WordMenuList>> responseWordMenuList = gson.fromJson(jsonData,new TypeToken<BaseResponse<List<WordMenuList>>>(){}.getType());
        List<WordMenuList> dataResponseList= responseWordMenuList.getResults();
        wordMenuLists.clear();
        for (WordMenuList wordMenuList:dataResponseList){
            wordMenuLists.add(wordMenuList);
//            Log.e(TAG, "parseJsonDataWithGson: "+wordMenuList.getCreatedAt());
//            Log.e(TAG, "parseJsonDataWithGson: "+wordMenuList.getVocLevel());
//            Log.e(TAG, "parseJsonDataWithGson: "+wordMenuList.getNew_tableName());
        }
    initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.word_menu_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(layoutManager);
        WordMenuAdapter adapter = new WordMenuAdapter(wordMenuLists);
        recyclerView.setAdapter(adapter);
    }


}
