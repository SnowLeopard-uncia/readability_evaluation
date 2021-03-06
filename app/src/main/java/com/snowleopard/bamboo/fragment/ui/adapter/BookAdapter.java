package com.snowleopard.bamboo.fragment.ui.adapter;

import static android.content.ContentValues.TAG;
import static android.os.FileUtils.copy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.UI.BookIntroductionActivity;
import com.snowleopard.bamboo.javaBean.BookHome;
import com.snowleopard.bamboo.javaBean.UserLocal;

import org.litepal.LitePal;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {


    private List<BookHome> mBookList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View bookView;
        ImageView iv_book_shape;
        ImageView iv_lock;
        TextView tv_level;
//        TextView tv_coin;

        // ViewHolder类的构造方法，View为RecyclerView子项的最外层布局
        public ViewHolder(View view) {
            super(view);
            bookView = view;
            iv_book_shape = (ImageView) view.findViewById(R.id.iv_book_shape);
//            iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
            tv_level = view.findViewById(R.id.tv_level);
//            tv_coin = view.findViewById(R.id.tv_coin);
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
        holder.iv_book_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();// 获取点击的书本在书列表中的位置
                BookHome book = mBookList.get(position);// 获取点击的书本
                UserLocal userLocal = LitePal.findFirst(UserLocal.class);
                if (userLocal == null){
                    userLocal=new UserLocal();
                    userLocal.setCoin(0);
                    userLocal.setLevel("A");
                    userLocal.setLanguage("English");
                    userLocal.save();
                }
                int userCoin = userLocal.getCoin();
                /**
                 *
              金币操作
                if ((!book.getLevel().equals(userLocal.getLevel())) && userCoin < book.getGoldCoin()) {
                    Toast.makeText(v.getContext(), "金币不足", Toast.LENGTH_SHORT).show();
                    return;
                }
                 */
                Intent intent = new Intent(v.getContext(), BookIntroductionActivity.class);
                intent.putExtra("book_id", book.getBookId());
                intent.putExtra("gold_coin", book.getGoldCoin());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    // 对子项的数据进行赋值
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookHome book = mBookList.get(position);
        holder.tv_level.setText(book.getLevel());
//        holder.tv_coin.setText("" + book.getGoldCoin());
        Glide.with(holder.bookView.getContext()).load(book.getCover_url()).into(holder.iv_book_shape);


        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        if (userLocal == null){
            userLocal=new UserLocal();
            userLocal.setCoin(0);
            userLocal.setLevel("A");
            userLocal.setLanguage("English");
            userLocal.save();
        }
        int userCoin = userLocal.getCoin();
        Log.e(TAG, "onBindViewHolder: "+userCoin);
//        Log.e(TAG, "后台返回的Level: " +book.getLevel());
//        Log.e(TAG, "Adapter里面的userLevel: " +userLocal.getLevel());
        /**
         * 金币锁操作

        if (book.getLevel().equals(userLocal.getLevel())) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
        }
        if ((!book.getLevel().equals(userLocal.getLevel())) && userCoin >= book.getGoldCoin()) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
        }
        if ((!book.getLevel().equals(userLocal.getLevel())) && userCoin < book.getGoldCoin()) {
            holder.iv_lock.setVisibility(View.VISIBLE);
        }
         */
    }

    // 获取子项的个数
    @Override
    public int getItemCount() {
        return mBookList.size();
    }


}
