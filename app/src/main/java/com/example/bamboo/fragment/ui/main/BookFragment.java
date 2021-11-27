package com.example.bamboo.fragment.ui.main;

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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.BookAdapter;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.Personal;
import com.example.bamboo.javaBean.UserLocal;
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


public class BookFragment extends Fragment {

    private List<BookHome> bookList = new ArrayList<>();
//    private TextView tv_grade;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    String objectId;
    private List<Personal> personList = new ArrayList<>();
    private String key;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        tv_grade = getView().findViewById(R.id.tv_grade);

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

            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);




//        int position = gridLayoutManager.findFirstVisibleItemPosition();
//        Log.e(TAG, "屏幕第一个item位置: " + position);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                if (layoutManager instanceof GridLayoutManager) {
//                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
//                    //获取最后一个可见view的位置
//                    int lastItemPosition = gridLayoutManager.findLastVisibleItemPosition();
//                    //获取第一个可见view的位置
//                    int firstItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
//                    Log.e(TAG, "屏幕第一个item位置：" + firstItemPosition);
//    }
//
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fragment_book, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
// 注意注意注意
//        getResponseData();
//        try {
//            getUserPageResponseData();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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

            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

    }


    private void initRecyclerView() {
        recyclerView = getView().findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }


    private void getResponseData() {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        if(key.equals("English")){
            ace.callEndpoint("getBookCoverInfo", null, new CloudCodeListener() {
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
            ace.callEndpoint("getSpanishBookCoverInfo", null, new CloudCodeListener() {
                @Override
                public void done(Object object, BmobException e) {
                    if (e == null) {
                        String responseData = object.toString();
                        Log.e(TAG, "西语图书首页done: "+responseData);
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
        BaseResponse<List<BookHome>> responseBookHomeList = gson.fromJson(jsonData, new TypeToken<BaseResponse<List<BookHome>>>() {
        }.getType());
        List<BookHome> dataResponseList = responseBookHomeList.getResults();
        bookList.clear();
        for (BookHome bookHome : dataResponseList) {
            bookList.add(bookHome);
        }

        initRecyclerView();

//        int position = gridLayoutManager.findFirstVisibleItemPosition();
//        Log.e(TAG, "屏幕第一个item位置: " + position);
    }


//    public void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
//
//
//        int firstItem = manager.findFirstVisibleItemPosition();
//
//        int lastItem = manager.findLastVisibleItemPosition();
//        if (n <= firstItem) {
//            mRecyclerView.scrollToPosition(n);
//        } else if (n <= lastItem) {
//            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
//            mRecyclerView.scrollBy(0, top);
//        } else {
//            mRecyclerView.scrollToPosition(n);
//        }
//
//    }

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
                Log.e(TAG, "图书碎片个人接口: "+userLocal1.getLanguage());
            }
        }

    }


}
