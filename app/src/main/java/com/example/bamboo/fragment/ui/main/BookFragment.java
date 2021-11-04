package com.example.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.BookAdapter;
import com.example.bamboo.fragment.ui.adapter.WordMenuAdapter;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.WordMenuList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;


public class BookFragment extends Fragment {

    private List<BookHome> bookList = new ArrayList<>();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        initRecyclerView();
//        init();
        getResponseData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fragment_book, container, false);
        return view;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }

//    private void init() {
//        BookHome book1 = new BookHome(1,"A");
//        bookList.add(book1);
//        BookHome book2 = new BookHome(5,"B");
//        bookList.add(book2);
//    }

    private void getResponseData() {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第三个参数是回调类
        ace.callEndpoint("getBookCoverInfo", null, new CloudCodeListener() {
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
        BaseResponse<List<BookHome>> responseBookHomeList = gson.fromJson(jsonData, new TypeToken<BaseResponse<List<BookHome>>>() {
        }.getType());
        List<BookHome> dataResponseList = responseBookHomeList.getResults();
        for (BookHome bookHome : dataResponseList) {
            bookList.add(bookHome);
            Log.e(TAG, "parseJson: " + bookHome.getLevel());
            Log.e(TAG, "parseJson: " + bookHome.getGoldCoin());
            Log.e(TAG, "parseJson: " + bookHome.getColorcover());
        }

        initRecyclerView();
    }


}
