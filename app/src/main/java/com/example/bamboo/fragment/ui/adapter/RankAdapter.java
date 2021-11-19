package com.example.bamboo.fragment.ui.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.javaBean.ReadingRank;
import com.example.bamboo.view.CircleImageView;

import java.util.List;

public class RankAdapter extends BaseAdapter {

    private List<ReadingRank> mList;
    private Context mContext;

    public RankAdapter(Context pContext, List<ReadingRank> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_spinner, null);
        if (view != null) {
            TextView tv_order = view.findViewById(R.id.tv_order);
            CircleImageView iv_imageHead = view.findViewById(R.id.iv_imageHead);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_level = view.findViewById(R.id.tv_level);
            TextView tv_coin = view.findViewById(R.id.tv_coin);
            tv_order.setText(i+1+"");
            Glide.with(mContext).load(mList.get(i).getImageHead()).into(iv_imageHead);
            tv_name.setText(mList.get(i).getNickname());
            tv_level.setText("等级:"+mList.get(i).getLevel());
            tv_coin.setText("金币:"+mList.get(i).getCoin()+"");

        }
        return view;
    }

}
