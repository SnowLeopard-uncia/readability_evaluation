package com.example.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.BookAdapter;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.DownloadFileListener;


public class BookFragment extends Fragment {

    private List<BookHome> bookList = new ArrayList<>();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
            Log.e(TAG, "parseJson: " + bookHome.getColorcover().getType());
            Log.e(TAG, "parseJson: " + bookHome.getColorcover().getFilename());
            Log.e(TAG, "parseJson: " + bookHome.getColorcover().getCdn());
            Log.e(TAG, "parseJson: " + bookHome.getColorcover().getUrl());
//            BmobFile bmobfile =new BmobFile(bookHome.getColorcover().getFilename(),"",bookHome.getColorcover().getUrl());
//            downloadFile(bmobfile);

        }

        initRecyclerView();
    }


//    private void downloadFile(BmobFile file){
//        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
//        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
//        file.download(saveFile, new DownloadFileListener() {
//
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void done(String savePath, BmobException e) {
//                if(e==null){
//                    Log.e(TAG, "图片路径为： " + savePath);
//                }else{
//                    Log.e(TAG, " 图片下载不了：" + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onProgress(Integer value, long newworkSpeed) {
//                Log.e("bmob","下载进度："+value+","+newworkSpeed);
//            }
//
//        });
//    }



}
