package com.snowleopard.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.fragment.ui.adapter.VideoAdapter;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.Personal;
import com.snowleopard.bamboo.javaBean.UserLocal;
import com.snowleopard.bamboo.javaBean.VideoHome;
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

public class VideoFragment extends Fragment {

    private List<VideoHome> videoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    String objectId;
    private List<Personal> personList = new ArrayList<>();
    private String key;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getUserID();
        try {
            getUserPageResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeLanguage");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                key = intent.getStringExtra("language");
                getResponseData();

//                Toast.makeText(getActivity(),"接受到了广播"+key,Toast.LENGTH_SHORT).show();
//                if(key.equals("English")) {
//                    getResponseData1();
//                    Toast.makeText(getActivity(),"英语的广播",Toast.LENGTH_SHORT).show();
//                }else {
//                    getResponseData2();
//                    Toast.makeText(getActivity(),"西语的广播",Toast.LENGTH_SHORT).show();
//                }

            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_fragment_video, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            getUserPageResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initRecyclerView() {
        recyclerView = getView().findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        VideoAdapter adapter = new VideoAdapter(videoList);
        recyclerView.setAdapter(adapter);
    }


    private void getResponseData() {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        if(key.equals("English")){
            ace.callEndpoint("IndexVideoInfo", null, new CloudCodeListener() {
                @Override
                public void done(Object object, BmobException e) {
                    if (e == null) {
                        String responseData = object.toString();
                        parseJsonDataWithGson(responseData);
                    } else {
                        Log.e(TAG, " " + e.getMessage());
                    }
                }
            });
        }else{
            ace.callEndpoint("IndexVideoInfo_Spanish", null, new CloudCodeListener() {
                @Override
                public void done(Object object, BmobException e) {
                    if (e == null) {
                        String responseData = object.toString();
                        parseJsonDataWithGson(responseData);
                    } else {
                        Log.e(TAG, " " + e.getMessage());
                    }
                }
            });
        }

    }


    private void parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        BaseResponse<List<VideoHome>> responseVideoHomeList = gson.fromJson(jsonData, new TypeToken<BaseResponse<List<VideoHome>>>() {
        }.getType());
        List<VideoHome> dataResponseList = responseVideoHomeList.getResults();
        videoList.clear();
        videoList.addAll(dataResponseList);

        initRecyclerView();

    }

    private void getUserID() {
        SharedPreferences pref = getActivity().getSharedPreferences("userInformation", MODE_PRIVATE);
        objectId = pref.getString("userID", "");
    }

    private void getUserPageResponseData() throws JSONException {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "userPage";
        JSONObject params = new JSONObject();
        params.put("objectId", objectId);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    parseUserJsonDataWithGson(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseUserJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        BaseResponse<List<Personal>> responsePersonalList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<Personal>>>() {
                }.getType());
        List<Personal> dataResponseList = responsePersonalList.getResults();
        personList.clear(); //新加的
        for (Personal personal : dataResponseList) {
            personList.add(personal);

            UserLocal userLocal = LitePal.findFirst(UserLocal.class);
            if (userLocal!=null){
                userLocal.setCoin(personal.getCoin());
                userLocal.setLevel(personal.getLevel());
                userLocal.setLanguage(personal.getLanguage());
                userLocal.updateAll();
            }
            else{
                userLocal=new UserLocal();
                userLocal.setCoin(personal.getCoin());
                userLocal.setLevel(personal.getLevel());
                userLocal.setLanguage(personal.getLanguage());
                userLocal.save(); //用save可以，初次
            }

            key = userLocal.getLanguage();
            getResponseData();

            List<UserLocal> personalList = LitePal.findAll(UserLocal.class);
            for (UserLocal userLocal1:personalList){
                Log.e(TAG, "视频碎片个人接口: "+userLocal1.getLanguage());
            }
        }

    }

}
