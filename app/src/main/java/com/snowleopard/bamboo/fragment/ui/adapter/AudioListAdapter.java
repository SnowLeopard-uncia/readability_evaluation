package com.snowleopard.bamboo.fragment.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.UI.AudioActivity;
import com.snowleopard.bamboo.UI.AudioDialogActivity;
import com.snowleopard.bamboo.javaBean.AudioList;
import com.snowleopard.bamboo.javaBean.UserLocal;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {
    private List<AudioList> mAudioList;
    private ArrayList<Integer> mIsLockList=new ArrayList<>();
    private int mPosition = -1;
    public AudioListAdapter(List<AudioList> mAudioList) {
        this.mAudioList = mAudioList;
    }

    /**
     *

    public void lockList(){
        mIsLockList.clear();
        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        int userCoin = userLocal.getCoin();
        for (int i = 0; i < mAudioList.size(); i++) {
            int isLock=0;
            AudioList audioList = mAudioList.get(i);
            if ((!audioList.getLevel().equals(userLocal.getLevel())) && userCoin < audioList.getResourceCoin()) {
                isLock=1;
            }
            mIsLockList.add(isLock);
        }
    }
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio_list,
                parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.audioItemView.setOnClickListener((v -> {
            UserLocal userLocal = LitePal.findFirst(UserLocal.class);
      //      int userCoin = userLocal.getCoin();

            mPosition = holder.getAdapterPosition();
            AudioList audioList = mAudioList.get(mPosition);
            holder.audioItemView.setVisibility(View.VISIBLE);
            //在点击监听里最好写入setVisibility(View.VISIBLE);这样可以避免效果会闪
            notifyDataSetChanged();
            //刷新界面 notify 通知Data 数据set设置Changed变化
            //在这里运行notifyDataSetChanged 会导致下面的onBindViewHolder 重新加载一遍
            Context context = v.getContext();
            Intent intent;
//            int visibility = holder.iv_lock.getVisibility();
       //     if ((!audioList.getLevel().equals(userLocal.getLevel())) && userCoin < audioList.getResourceCoin()) {
        //        intent = new Intent(context, AudioDialogActivity.class);
       //     }
      //      else{
        //    lockList();
            intent = new Intent(context, AudioActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putInt("visibility",visibility);
        //    bundle.putIntegerArrayList("isLockList",mIsLockList);
            bundle.putString("musicSelectedID",String.valueOf(audioList.getId()));
            bundle.putString("musicName",audioList.getName());
            bundle.putInt("position", mPosition);
            bundle.putInt("maxPosition",getItemCount());
            intent.putExtras(bundle);
       //     }
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
 //   holder.tvAudioCoin.setText(""+audioList.getResourceCoin());

    int itemBackGround = position % 2;
    if (itemBackGround==0){
        holder.audioItemView.setBackgroundColor(0x40EDEDED);
    }
//        onBindViewHolder 方法可能是在class里for添加了其他视图
//        引入mposition与当前的position判断，判断在点击的位置上显示打勾图片，在其他位置上不显示打勾
        if (position == mPosition){
            holder.iv_playing.setVisibility(View.VISIBLE);
        }else{
            holder.iv_playing.setVisibility(View.INVISIBLE);
        }
        List<UserLocal> userLocalList = LitePal.findAll(UserLocal.class);

//        List<UserLocal> userLocalList = LitePal.findAll(UserLocal.class);

/**
 *金币锁操作

        int userCoin = userLocalList.get(0).getCoin();
        //如果等级一样，就设置锁不可见
        if (audioList.getLevel().equals(userLocalList.get(0).getLevel())) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
        }
        //如果等级不一样，用户金币比列表的金币多，锁不可见
        if ((!audioList.getLevel().equals(userLocalList.get(0).getLevel())) && userCoin >= audioList.getResourceCoin()) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
        }
        //如果用户等级和列表等级不一样，金币数比资源小，锁可见
        if ((!audioList.getLevel().equals(userLocalList.get(0).getLevel())) && userCoin < audioList.getResourceCoin()) {
            holder.iv_lock.setVisibility(View.VISIBLE);
        }
 */
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
        TextView tvAudioCoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            audioItemView=itemView;
            iv_playing=itemView.findViewById(R.id.iv_playing);
            tv_audioId=itemView.findViewById(R.id.tv_audio_id);
            tv_audioName=itemView.findViewById(R.id.tv_audio_name);
            tv_audioLevel=itemView.findViewById(R.id.tv_audio_level);
//            iv_lock=itemView.findViewById(R.id.iv_lock);
//            tvAudioCoin=itemView.findViewById(R.id.tv_audio_coin);
        }
    }



}
