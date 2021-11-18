package com.example.bamboo.fragment.ui.adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.UI.AudioActivity;
import com.example.bamboo.javaBean.AudioList;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {
    private List<AudioList> mAudioList;
    private int mposition = -1;
    public AudioListAdapter(List<AudioList> mAudioList) {
        this.mAudioList = mAudioList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio_list,
                parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.audioItemView.setOnClickListener((v -> {
            mposition = holder.getAdapterPosition();
            AudioList audioList = mAudioList.get(mposition);
            holder.audioItemView.setVisibility(View.VISIBLE);
            //在点击监听里最好写入setVisibility(View.VISIBLE);这样可以避免效果会闪
            notifyDataSetChanged();
            //刷新界面 notify 通知Data 数据set设置Changed变化
            //在这里运行notifyDataSetChanged 会导致下面的onBindViewHolder 重新加载一遍
            Context context = v.getContext();
            Intent intent = new Intent(context, AudioActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("musicSelectedID",String.valueOf(audioList.getId()));
            bundle.putString("musicName",audioList.getName());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }));
        return holder;
    }


    //    public final int[] colors = new int[]{R.color.transparent,R.color.transparent};
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    AudioList audioList =mAudioList.get(position);
    holder.iv_playing.setVisibility(View.INVISIBLE);
    holder.tv_audioId.setText(""+audioList.getId());
    holder.tv_audioName.setText(audioList.getName());
    holder.tv_audioLevel.setText(""+audioList.getLevel());

    int itemBackGround = position % 2;
    if (itemBackGround==0){
        holder.audioItemView.setBackgroundColor(0x40EDEDED);
    }
//        onBindViewHolder 方法可能是在class里for添加了其他视图
//        引入mposition与当前的position判断，判断在点击的位置上显示打勾图片，在其他位置上不显示打勾
        if (position == mposition){
            holder.iv_playing.setVisibility(View.VISIBLE);
        }else{
            holder.iv_playing.setVisibility(View.INVISIBLE);
        }

    }
        //解决滑动隔行颜色错乱
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mAudioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View audioItemView;
        ImageView iv_playing;
        TextView tv_audioId;
        TextView tv_audioName;
        TextView tv_audioLevel;
        ImageView iv_lock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            audioItemView=itemView;
            iv_playing=itemView.findViewById(R.id.iv_playing);
            tv_audioId=itemView.findViewById(R.id.tv_audio_id);
            tv_audioName=itemView.findViewById(R.id.tv_audio_name);
            tv_audioLevel=itemView.findViewById(R.id.tv_audio_level);
            iv_lock=itemView.findViewById(R.id.iv_lock);
        }
    }



}
