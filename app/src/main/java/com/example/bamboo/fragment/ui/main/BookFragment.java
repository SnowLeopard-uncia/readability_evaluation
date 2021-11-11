package com.example.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.adapter.BookAdapter;
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


public class BookFragment extends Fragment {

    private List<BookHome> bookList = new ArrayList<>();
    private TextView tv_grade;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_grade = getView().findViewById(R.id.tv_grade);

        getResponseData();


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
