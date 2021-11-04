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
import com.example.bamboo.javaBean.Book;
import com.example.bamboo.okhttp.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookFragment extends Fragment {

    private List<Book> bookList = new ArrayList<>();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBookList();

        initBmob();

        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        String address ="http://cloud.bmob.cn/3c1ff0d0d4a411b4/getBookCoverInfo";
        showResponse(address);
    }


    private void initBmob() {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第三个参数是回调类
        ace.callEndpoint("selectVocabulary", null, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    Log.e(TAG, "done: "+result );
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }

    private void initBookList() {
        Book book = new Book(123,'1',1234);
        bookList.add(book);
        Book book1 = new Book(1,'2',12);
        bookList.add(book);
        Book book2 = new Book(23,'3',23);
        bookList.add(book);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fragment_book, container, false);
        return view;
    }


    private void showResponse(String address){
        HttpUtils.bookMainRequest(address, new Callback() {
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到后台返回的数据
                String responseData = response.body().string();
//                Log.e("tag",responseData);

                new Thread(new Runnable() { // 另开一个线程
                    @Override
                    public void run() {
                        Log.e("TAG", "run: "+responseData);
                    }
                });
            }
        });
    }

    private void initView(){

    }
}
