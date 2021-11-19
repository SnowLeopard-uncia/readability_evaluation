package com.example.bamboo.fragment.ui.adapter;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static android.os.FileUtils.copy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.UI.BookIntroductionActivity;
import com.example.bamboo.UI.MainActivity;
import com.example.bamboo.fragment.ui.main.BookFragment;
import com.example.bamboo.fragment.ui.main.SquareFragment;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.Personal;
import com.example.bamboo.javaBean.UserLocal;
import com.example.bamboo.javaBean.WordMenuList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.DownloadFileListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {


    private List<BookHome> mBookList;
//    String url = "http://119.91.130.240/test/page-1.jpg";

    static class ViewHolder extends RecyclerView.ViewHolder {
        View bookView;
        //        ImageView iv_book_bg;//用于测试
        ImageView iv_book_shape;
        ImageView iv_lock;
        TextView tv_level;
        TextView tv_coin;

        // ViewHolder类的构造方法，View为RecyclerView子项的最外层布局
        public ViewHolder(View view) {
            super(view);
            bookView = view;
            iv_book_shape = (ImageView) view.findViewById(R.id.iv_book_shape);
//            iv_book_bg = (ImageView) view.findViewById(R.id.iv_book_bg);//用于测试
            iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
            tv_level = view.findViewById(R.id.tv_level);
            tv_coin = view.findViewById(R.id.tv_coin);
        }
    }


    public BookAdapter(List<BookHome> bookList) {
        mBookList = bookList;
    }

    // 创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false); // 将子项布局加载进来
        final ViewHolder holder = new ViewHolder(view);
//        Bmob.initialize(holder.bookView.getContext(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        holder.iv_book_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();// 获取点击的书本在书列表中的位置
                BookHome book = mBookList.get(position);// 获取点击的书本

                Intent intent = new Intent(v.getContext(), BookIntroductionActivity.class);
                intent.putExtra("book_id", book.getBookId());
                intent.putExtra("gold_coin", book.getGoldCoin());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    // 对子项的数据进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookHome book = mBookList.get(position);
        holder.tv_level.setText(book.getLevel());
        holder.tv_coin.setText("" + book.getGoldCoin());
        Glide.with(holder.bookView.getContext()).load(book.getCover_url()).into(holder.iv_book_shape);


        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        if (userLocal == null){
            userLocal=new UserLocal();
            userLocal.setCoin(0);
            userLocal.setLevel("A");
            userLocal.save();
        }
        int userCoin = userLocal.getCoin();
        Log.e(TAG, "onBindViewHolder: "+userCoin);
//        Log.e(TAG, "后台返回的Level: " +book.getLevel());
//        Log.e(TAG, "Adapter里面的userLevel: " +userLocal.getLevel());
        if (book.getLevel().equals(userLocal.getLevel())) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
            holder.iv_book_shape.setClickable(true);
        }

        if ((!book.getLevel().equals(userLocal.getLevel())) && userCoin >= book.getGoldCoin()) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
            holder.iv_book_shape.setClickable(true);
        }
        if ((!book.getLevel().equals(userLocal.getLevel())) && userCoin < book.getGoldCoin()) {
            holder.iv_lock.setVisibility(View.VISIBLE);
            holder.iv_book_shape.setClickable(false);
        }

    }

    // 获取子项的个数
    @Override
    public int getItemCount() {
        return mBookList.size();
    }


}
