package com.example.bamboo.fragment.ui.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.UI.WordListActivity;
import com.example.bamboo.javaBean.WordMenuList;

import java.util.List;

public class WordMenuAdapter extends RecyclerView.Adapter<WordMenuAdapter.ViewHolder> {


    private List<WordMenuList> mWordMenuLists;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word_menu_list,parent,false); // 将子项布局加载进来
        final ViewHolder holder = new ViewHolder(view);

        //holder.wordItemView.setClickable(false);
        holder.wordItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int position=holder.getAdapterPosition();
                WordMenuList wordMenuList = mWordMenuLists.get(position);
                Context context = view.getContext();
                Intent intent = new Intent(context, WordListActivity.class);
                Log.e(TAG, "onClick: "+wordMenuList.getNew_tableName());
                Bundle bundle = new Bundle();
                bundle.putString("level",wordMenuList.getVocLevel());
                bundle.putString("wordNum",wordMenuList.getVocWordnum());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordMenuList wordMenuList = mWordMenuLists.get(position);
        holder.tv_wordLevel.setText(wordMenuList.getVocLevel());
//        holder.tv_wordTitle.setText(wordMenuList.getVocTitle());
        holder.tv_wordAge.setText(wordMenuList.getVocAge());
        holder.tv_wordAccount.setText(wordMenuList.getVocWordnum());
        int itemBackGround = position % 2;
        if (itemBackGround==0){
            holder.crWordLevel.setBackgroundResource(R.drawable.bg_word_menu);
        }else{
            holder.crWordLevel.setBackgroundResource(R.drawable.bg_word_menu_x);
        }
    }

    @Override
    public int getItemCount() {
        return mWordMenuLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View wordItemView;
        TextView tv_wordLevel;
        TextView tv_wordTitle;
        TextView tv_wordAccount;
        TextView tv_wordAge;
        com.example.bamboo.view.CircleRelativeLayout crWordLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView=itemView;
            tv_wordAccount=itemView.findViewById(R.id.tv_word_account);
            tv_wordLevel=itemView.findViewById(R.id.tv_word_menu_level);
//            tv_wordTitle=itemView.findViewById(R.id.tv_word_list_title);
            crWordLevel=itemView.findViewById(R.id.cr_word_level);
            tv_wordAge=itemView.findViewById(R.id.tv_suit_age_word);
        }
    }

    public WordMenuAdapter(List<WordMenuList> wordMenuLists) {
        this.mWordMenuLists = wordMenuLists;
    }
}
