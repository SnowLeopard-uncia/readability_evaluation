package com.snowleopard.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.fragment.ui.adapter.WordMenuAdapter;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.UserLocal;
import com.snowleopard.bamboo.javaBean.WordMenuList;
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

public class WordFragment extends Fragment {

    private List<WordMenuList> wordMenuLists = new ArrayList<>();
    private String name;

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
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeLanguage");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String key = intent.getStringExtra("language");
                try {
                    getResponseData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
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
        UserLocal userLocal = LitePal.findFirst(UserLocal.class);

        if (userLocal!=null){
            userLocal=new UserLocal();
            userLocal.setLevel("A");
            userLocal.setLanguage("English");
            userLocal.setCoin(0);
            userLocal.updateAll();
        }else{
            userLocal=new UserLocal();
            userLocal.setLevel("A");
            userLocal.setLanguage("English");
            userLocal.setCoin(0);
            userLocal.save();
        }
        name = userLocal.getLanguage();
            if (userLocal.getLanguage().equals("English")){
                name ="selectVocabulary";
                Log.e("WordFragment", "selectVocabulary: "+"英语接口单词");
            }else{
                name ="selectVocabulary_Spanish";
            }
        ace.callEndpoint("selectVocabulary", params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e("WordFragment", "selectVocabulary: "+responseData );
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
        wordMenuLists.addAll(dataResponseList);
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
