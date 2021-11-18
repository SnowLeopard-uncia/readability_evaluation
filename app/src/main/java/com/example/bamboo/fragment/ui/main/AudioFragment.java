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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.AudioListAdapter;
import com.example.bamboo.javaBean.AudioList;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class AudioFragment extends Fragment {
private List<AudioList> mAudioLists = new ArrayList<>();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getResponseData();
    }

    private void getResponseData() {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        ace.callEndpoint("indexEngMusicInfo", null, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    parseJsonDataWithGson(result);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }

        });

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
