package com.example.bamboo.fragment.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.main.BookFragment;
import com.example.bamboo.javaBean.BookHome;
import com.example.bamboo.javaBean.WordMenuList;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<BookHome> mBookList;
    private BookFragment bookfragment;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View bookView;
        ImageView iv_book_shape;
        ImageView iv_lock;
        TextView tv_level;
        TextView tv_coin;

        // ViewHolder类的构造方法，View为RecyclerView子项的最外层布局
        public ViewHolder(View view) {
            super(view);
            bookView = view;
            iv_book_shape = (ImageView) view.findViewById(R.id.iv_book_shape);
            iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
            tv_level =  view.findViewById(R.id.tv_level);
            tv_coin =  view.findViewById(R.id.tv_coin);
        }
    }


    public BookAdapter(List<BookHome> bookList) {
        mBookList = bookList;
    }

    // 创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book,parent,false); // 将子项布局加载进来
        final ViewHolder holder = new ViewHolder(view);

//        holder.iv_book_shape.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), BookIntroductionActivity.class);
//                    v.getContext().startActivity(intent);
//            }
//        });
        return holder;
    }

    // 对子项的数据进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookHome book = mBookList.get(position);
        holder.tv_level.setText(book.getLevel());
        holder.tv_coin.setText(""+book.getGoldCoin());
//        String url = book.getColorcover().getUrl();
//        Glide.with(bookfragment).load(url).into(holder.iv_book_shape);

    }

    // 获取子项的个数
    @Override
    public int getItemCount() {
        return mBookList.size();
    }


}
