package com.snowleopard.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.UI.PathActivity;
import com.snowleopard.bamboo.fragment.ui.adapter.RankAdapter;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.Personal;
import com.snowleopard.bamboo.javaBean.ReadingRank;
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

public class ReportsFragment extends Fragment implements View.OnClickListener{

    private TextView tvName;
    private TextView tvLevel;
    private TextView tvBookNum;
    private TextView tvWordNum;
    private TextView tvCoinNum;
    private TextView tvRank;
    private Spinner rankSpinner;
    private TextView tvPersonalGrade;
    private TextView tvNextGrade;
    private TextView tvCoinNeed;
    private Button btnTopath;
    private LinearLayout llOpenSpinner;


    String objectId;
    private List<Personal> personList = new ArrayList<>();
    private List<ReadingRank> rankList = new ArrayList<>();
    private int[] updateCoinsNeed = {1, 2, 2, 6, 4, 4, 4, 8, 4, 4, 8, 4, 0};


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
        tvName = getView().findViewById(R.id.tv_name);
        tvLevel = getView().findViewById(R.id.tv_level);
        tvBookNum = getView().findViewById(R.id.tv_book_num);
        tvWordNum = getView().findViewById(R.id.tv_word_num);
        tvCoinNum = getView().findViewById(R.id.tv_coin_num);
        tvRank = getView().findViewById(R.id.tv_rank);
        rankSpinner = getView().findViewById(R.id.rank_spinner);
        tvPersonalGrade = getView().findViewById(R.id.tv_personal_grade);
        tvNextGrade = getView().findViewById(R.id.tv_next_grade);
        tvCoinNeed = getView().findViewById(R.id.tv_coin_need);
        btnTopath = getView().findViewById(R.id.btn_toPath);
        llOpenSpinner=getView().findViewById(R.id.ll_open_spinner);
        btnTopath.setOnClickListener(this);

        RankAdapter rankAdapter = new RankAdapter(getActivity(), rankList);
        rankSpinner.setAdapter(rankAdapter);

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
                    Log.e(TAG, "getUserPageResponseData: json：" + responseData);
                    parseJsonDataWithGson1(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void parseJsonDataWithGson1(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<Personal>> responsePersonalList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<Personal>>>() {
                }.getType());
        List<Personal> dataResponseList = responsePersonalList.getResults();
        for (Personal personal : dataResponseList) {
            personList.add(personal);

            tvName.setText(personal.getNickname());
            tvLevel.setText("等级" + personal.getLevel());
            tvBookNum.setText(personal.getBooknum() + "");
            tvWordNum.setText(personal.getWordnum() + "");
            tvCoinNum.setText(personal.getCoin() + "");
            tvPersonalGrade.setText(personal.getLevel());
            if (personal.getLevel().equals("M")) {
                tvNextGrade.setText("M");
            }else{
                tvNextGrade.setText((char)(personal.getLevel().charAt(0) + 1 )+"");
                Log.e(TAG, "parseJsonDataWithGson1: "+( personal.getLevel().charAt(0)-65));
            }
            int need = updateCoinsNeed[personal.getLevel().charAt(0)-65];

            tvCoinNeed.setText("距离升级还需：" + need + "金币");

            UserLocal userLocal = LitePal.findFirst(UserLocal.class);
            if (userLocal!=null){
                userLocal.setCoin(personal.getCoin());
                userLocal.setLevel(personal.getLevel());
                userLocal.setLanguage(personal.getLanguage());
                userLocal.updateAll();
//                userLocal.update(1);
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
                Log.e(TAG, "个人中心: "+userLocal1.getCoin());
                Log.e(TAG, "个人中心: "+userLocal1.getLevel());
                Log.e(TAG, "个人中心: "+userLocal1.getLanguage());
            }


        }

    }

    private void getUserID() {
        SharedPreferences pref = getActivity().getSharedPreferences("userinfo", MODE_PRIVATE);
        objectId = pref.getString("userId", "");
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
//                    Log.e(TAG, "done: json：" + responseData);
                    tvRank.setText(responseData);
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
//                    Log.e(TAG, "done: json：" + responseData);
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
        rankList.clear();
        rankList.addAll(dataResponseList);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toPath:
                Intent intent = new Intent(getActivity(), PathActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
