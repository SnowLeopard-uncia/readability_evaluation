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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.fragment.ui.adapter.AudioListAdapter;
import com.snowleopard.bamboo.javaBean.AudioList;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.Personal;
import com.snowleopard.bamboo.javaBean.UserLocal;
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

public class AudioFragment extends Fragment {
private List<AudioList> mAudioLists = new ArrayList<>();
    String objectId;
    private Personal personal = new Personal();
    private List<Personal> personList = new ArrayList<>();
    private String key;
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            getUserData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
       大概因为是需要时间的操作，所以.....不能跟获取用户数据分开，一开始我是用随时通过后端获取用户的language来改变，
       但是这个方法还是有个缺点，就是好像我切换语言的时候,把数据传到了后台，但是同时发送的广播，又同时在后台请求个人数据，导致请求到
       的个人数据是改变前的个人数据

       然而初始化还是需要获取个人的数据来初始化
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeLanguage");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {



            @Override
            public void onReceive(Context context, Intent intent) {
                key = intent.getStringExtra("language");
//                Toast.makeText(getActivity(),"接受到了广播"+ key,Toast.LENGTH_SHORT).show();
                getResponseData();
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }




    private void getUserID() {
        SharedPreferences pref = getActivity().getSharedPreferences("userInformation", MODE_PRIVATE);
        objectId = pref.getString("userID", "");
    }
    private void getUserData() throws JSONException {
            Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
            String cloudCodeName = "userPage";
            JSONObject params = new JSONObject();
            getUserID();
            params.put("objectId", objectId);
            AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
            ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                @Override
                public void done(Object object, BmobException e) {
                    if (e == null) {
                        String responseData = object.toString();
                        Log.e(TAG, "done: json：音频里面的请求个人数据" + responseData);
                        parseJsonDataWithGson1(responseData);
                    } else {
                        Log.e(TAG, " " + e.getMessage());
                    }
                }
            });


    }

    private void parseJsonDataWithGson1(String responseData) {
        Gson gson = new Gson();
        BaseResponse<List<Personal>> responsePersonalList = gson.fromJson(responseData,
                new TypeToken<BaseResponse<List<Personal>>>() {
                }.getType());
        List<Personal> dataResponseList = responsePersonalList.getResults();
        personList.clear(); //新加的
        personList.addAll(dataResponseList);

            UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        personal=personList.get(0);
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

            List<UserLocal> personalList = LitePal.findAll(UserLocal.class);
            for (UserLocal userLocal1:personalList){
                Log.e(TAG, "音频fragment接口: "+userLocal1.getLanguage());
            }
            key=personal.getLanguage();
        getResponseData();

    }


    private void getResponseData() {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        Log.e(TAG, "getResponseData: 个人音频里面"+personal.getLanguage() );
        if(key.equals("English")){
        ace.callEndpoint("indexEngMusicInfo", null, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    parseJsonDataWithGson(result);
                    Log.e(TAG, "done: 英语音");
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });
        }else{
            ace.callEndpoint("indexSpanishMusicInfo", null, new CloudCodeListener() {
                @Override
                public void done(Object object, BmobException e) {
                    if (e == null) {
                        String result = object.toString();
                        parseJsonDataWithGson(result);
                        Log.e(TAG, "done: 西语音");
                    } else {
                        Log.e(TAG, " " + e.getMessage());
                    }
                }

            });
        }

    }

    private void parseJsonDataWithGson(String result) {
        Gson gson = new Gson();
        BaseResponse<List<AudioList>> response = gson.fromJson(result, new TypeToken<BaseResponse<List<AudioList>>>() {
        }.getType());
        List<AudioList> audioLists = response.getResults();
        mAudioLists.clear();
        mAudioLists.addAll(audioLists);
        initRecyclerView();
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.rv_audio_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        AudioListAdapter adapter = new AudioListAdapter(mAudioLists);
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_fragment_audio, container, false);
    }


}
