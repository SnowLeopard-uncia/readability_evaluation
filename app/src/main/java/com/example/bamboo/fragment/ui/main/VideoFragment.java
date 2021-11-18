package com.example.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.BookAdapter;
import com.example.bamboo.fragment.ui.adapter.VideoAdapter;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.VideoHome;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getResponseData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_fragment_video, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        getResponseData();

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
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第三个参数是回调类
        ace.callEndpoint("IndexVideoInfo", null, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataWithGson(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        BaseResponse<List<VideoHome>> responseVideoHomeList = gson.fromJson(jsonData, new TypeToken<BaseResponse<List<VideoHome>>>() {
        }.getType());
        List<VideoHome> dataResponseList = responseVideoHomeList.getResults();
        videoList.clear();
        for (VideoHome videoHome : dataResponseList) {
            videoList.add(videoHome);
//            Log.e(TAG, "parseJson: " + videoHome.getVideoID());
//            Log.e(TAG, "parseJson: " + videoHome.getVideoPrice());
//            Log.e(TAG, "parseJson: " + videoHome.getVideoPic());
//            Log.e(TAG, "parseJson: " + videoHome.getVideoName());
//            Log.e(TAG, "parseJson: " + videoHome.getVideoLevel());

        }

        initRecyclerView();


    }


}
