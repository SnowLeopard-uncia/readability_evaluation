package com.example.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.BookIntroduction;
import com.example.bamboo.javaBean.Personal;
import com.example.bamboo.javaBean.ReadingRank;
import com.example.bamboo.javaBean.UserLocal;
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

public class ReportsFragment extends Fragment {

    private TextView tv_name;
    private TextView tv_level;
    private TextView tv_book_num;
    private TextView tv_word_num;
    private TextView tv_coin_num;
    private TextView tv_rank;
    private Spinner rank_spinner;

    UserLocal userLocal = new UserLocal();

    String objectId;
    private List<Personal> personList = new ArrayList<>();
    private List<ReadingRank> rankList = new ArrayList<>();


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        getUserID();

        try {
            getUserPageResponseData();
            getUserRankResponseData();
            getRankPageResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            getUserPageResponseData();
            getUserRankResponseData();
            getRankPageResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void init() {
        tv_name = getView().findViewById(R.id.tv_name);
        tv_level = getView().findViewById(R.id.tv_level);
        tv_book_num = getView().findViewById(R.id.tv_book_num);
        tv_word_num = getView().findViewById(R.id.tv_word_num);
        tv_coin_num = getView().findViewById(R.id.tv_coin_num);
        tv_rank = getView().findViewById(R.id.tv_rank);
        rank_spinner = getView().findViewById(R.id.rank_spinner);
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
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataWithGson1(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataWithGson1(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<Personal>> responsePersonalList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<Personal>>>() {
                }.getType());
        List<Personal> dataResponseList = responsePersonalList.getResults();
        for (Personal personal : dataResponseList) {
            personList.add(personal);

            tv_name.setText(personal.getUsername());
            tv_level.setText("等级" + personal.getLevel());
            tv_book_num.setText(personal.getBooknum() + "");
            tv_word_num.setText(personal.getWordnum() + "");
            tv_coin_num.setText(personal.getCoin() + "");


            userLocal.setCoin(personal.getCoin());
            userLocal.setLevel(personal.getLevel());
            userLocal.update(1);
//            userLocal.save();


        }

    }

    private void getUserID() {
        SharedPreferences pref = getActivity().getSharedPreferences("userInformation", MODE_PRIVATE);
        objectId = pref.getString("userID", "");
    }

    private void getUserRankResponseData() throws JSONException {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "userRank";
        JSONObject params = new JSONObject();
        params.put("objectId", objectId);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    tv_rank.setText(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }

    private void getRankPageResponseData() {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第三个参数是回调类
        ace.callEndpoint("rankPage", null, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataWithGson2(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }

    private void parseJsonDataWithGson2(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<ReadingRank>> responseReadingRankList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<ReadingRank>>>() {
                }.getType());
        List<ReadingRank> dataResponseList = responseReadingRankList.getResults();
        for (ReadingRank readingRank : dataResponseList) {
            rankList.add(readingRank);

        }

    }


}
