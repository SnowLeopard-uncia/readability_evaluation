package com.example.bamboo.fragment.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bamboo.R;
import com.example.bamboo.javaBean.BookItem;
import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<BookItem> mBookItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;

        // ViewHolder类的构造方法，View为RecyclerView子项的最外层布局
        public ViewHolder(View view) {
            super(view);
            bookImage = (ImageView) view.findViewById(R.id.iv_book_shape);
        }
    }


    public BookAdapter(List<BookItem> bookItemList) {
        mBookItemList = bookItemList;
    }

    // 创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book,parent,false); // 将子项布局加载进来
        final ViewHolder holder = new ViewHolder(view);

//        holder.bookImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();// 获取点击的星球卡片在recyclerview中的位置
//                // 因为只做了一个星球的日记列表，所以点击其它星球卡片没有反应
//                if(position==0){
//                    Intent intent = new Intent(v.getContext(),DiaryList.class);
//                    v.getContext().startActivity(intent);
//                }
//            }
//        });
        return holder;
    }

    // 对子项的数据进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookItem book = mBookItemList.get(position);
        holder.bookImage.setImageResource(book.getImageId());
    }

    // 获取子项的个数
    @Override
    public int getItemCount() {
        return mBookItemList.size();
    }


}
