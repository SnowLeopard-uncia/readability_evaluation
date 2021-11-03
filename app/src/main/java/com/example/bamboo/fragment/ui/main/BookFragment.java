package com.example.bamboo.fragment.ui.main;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookFragment extends Fragment {

    private List<Book> bookList = new ArrayList<>();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        String address ="http://cloud.bmob.cn/3c1ff0d0d4a411b4/getBookCoverInfo";
        showResponse(address);
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
                        Log.e("tag",responseData);
                    }
                });
            }
        });
    }

    private void initView(){

    }
}
