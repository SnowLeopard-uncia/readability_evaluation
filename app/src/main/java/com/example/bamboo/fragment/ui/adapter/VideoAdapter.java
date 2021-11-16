package com.example.bamboo.fragment.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.UI.VideoIntroductionActivity;
import com.example.bamboo.javaBean.VideoHome;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoHome> mVideoList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View videoView;
//        ImageView iv_book_bg;//用于测试
        ImageView iv_book_shape;
        ImageView iv_lock;
        TextView tv_level;
        TextView tv_coin;

        // ViewHolder类的构造方法，View为RecyclerView子项的最外层布局
        public ViewHolder(View view) {
            super(view);
            videoView = view;
            iv_book_shape = (ImageView) view.findViewById(R.id.iv_book_shape);
//            iv_book_bg = (ImageView) view.findViewById(R.id.iv_book_bg);//用于测试
            iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
            tv_level = view.findViewById(R.id.tv_level);
            tv_coin = view.findViewById(R.id.tv_coin);
        }

    }


    public VideoAdapter(List<VideoHome> videoList) {
        mVideoList = videoList;
    }

    // 创建ViewHolder实例
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false); // 将子项布局加载进来

        final VideoAdapter.ViewHolder holder = new VideoAdapter.ViewHolder(view);

        holder.iv_book_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                VideoHome video = mVideoList.get(position);
                Intent intent = new Intent(v.getContext(), VideoIntroductionActivity.class);
                intent.putExtra("videoID", video.getVideoID());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    // 对子项的数据进行赋值
    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        VideoHome video = mVideoList.get(position);
        holder.tv_level.setText(video.getVideoLevel());
        holder.tv_coin.setText("" + video.getVideoPrice());
        Glide.with(holder.videoView.getContext()).load(video.getPng()).into(holder.iv_book_shape);

    }

    // 获取子项的个数
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

}
