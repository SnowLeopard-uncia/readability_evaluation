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
            //??????????????????????????????setVisibility(View.VISIBLE);??????????????????????????????
            notifyDataSetChanged();
            //???????????? notify ??????Data ??????set??????Changed??????
            //???????????????notifyDataSetChanged ??????????????????onBindViewHolder ??????????????????
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
//        onBindViewHolder ??????????????????class???for?????????????????????
//        ??????mposition????????????position??????????????????????????????????????????????????????????????????????????????????????????
        if (position == mPosition){
            holder.iv_playing.setVisibility(View.VISIBLE);
        }else{
            holder.iv_playing.setVisibility(View.INVISIBLE);
        }
        List<UserLocal> userLocalList = LitePal.findAll(UserLocal.class);

//        List<UserLocal> userLocalList = LitePal.findAll(UserLocal.class);

/**
 *???????????????

        int userCoin = userLocalList.get(0).getCoin();
        //??????????????????????????????????????????
        if (audioList.getLevel().equals(userLocalList.get(0).getLevel())) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
        }
        //????????????????????????????????????????????????????????????????????????
        if ((!audioList.getLevel().equals(userLocalList.get(0).getLevel())) && userCoin >= audioList.getResourceCoin()) {
            holder.iv_lock.setVisibility(View.INVISIBLE);
        }
        //??????????????????????????????????????????????????????????????????????????????
        if ((!audioList.getLevel().equals(userLocalList.get(0).getLevel())) && userCoin < audioList.getResourceCoin()) {
            holder.iv_lock.setVisibility(View.VISIBLE);
        }
 */
    }
        //??????????????????????????????
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
