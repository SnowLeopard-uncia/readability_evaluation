package com.example.bamboo.fragment.ui.adapter;

import static android.content.ContentValues.TAG;
import static android.os.FileUtils.copy;

import android.content.Intent;
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
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.WordMenuList;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<BookHome> mBookList;
    String url = "http://119.91.130.240/test/page-1.jpg";

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


//        BmobFile bmobfile =new BmobFile(book.getColorcover().getFilename(),"",book.getColorcover().getUrl());
//        downloadFile(bmobfile);
//        Log.e(TAG, "图片地址: " +url);

        Glide.with(holder.bookView.getContext()).load(url).into(holder.iv_book_shape);

//        holder.iv_book_shape.setImageBitmap(BitmapFactory.decodeFile(url));

    }

    // 获取子项的个数
    @Override
    public int getItemCount() {
        return mBookList.size();
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
//                    url = savePath;
//                }else{
//                    Log.e(TAG, " " + e.getMessage());
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

//    private void passUserID(){
//        Bundle bundle =
//
//        String userID = getContext().getIntent().getExtras().getString("userID");
//        BookFragment  bookFragment = new BookFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("key_str",userID);
//        bookFragment.setArguments(bundle);
//    }


}
