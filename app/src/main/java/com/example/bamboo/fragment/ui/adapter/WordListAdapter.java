package com.example.bamboo.fragment.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.WordList;

import java.time.temporal.Temporal;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private List<WordList> mWordListList;

    public WordListAdapter(List<WordList> mWordListList) {
        this.mWordListList = mWordListList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word,parent,false); // 将子项布局加载进来
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordList wordList = mWordListList.get(position);
        holder.tv_word.setText(wordList.getWord());
        holder.wordItemView.setOnClickListener(view -> {
            wordList.setIsClick(wordList.getIsClick()+1);
            if ((wordList.getIsClick()%2)==1){
                holder.tv_word.setText(wordList.getZh());
            }else{
                holder.tv_word.setText(wordList.getWord());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWordListList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View wordItemView;
        TextView tv_word;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView=itemView;
            tv_word=itemView.findViewById(R.id.tv_word);

        }
    }
}
