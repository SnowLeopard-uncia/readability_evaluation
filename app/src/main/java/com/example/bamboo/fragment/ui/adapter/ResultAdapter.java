package com.example.bamboo.fragment.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.ResultData;
import com.example.bamboo.view.BarChartView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_BAR_CHART=0;
    public static final int TYPE_SHOW_GRADE=1;
    int[] img={ R.drawable.zero,
      R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,
            R.drawable.five, R.drawable.six,R.drawable.seven,
            R.drawable.eight,R.drawable.nine
    };
    private List<ResultData> mResultList;

    public ResultAdapter(List<ResultData> mResultList) {
        this.mResultList = mResultList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_BAR_CHART:
                return new BarChartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bar_chart,
                        parent, false));
            case TYPE_SHOW_GRADE:
                return new GradeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_grade,
                        parent, false));
        }
        return null;
    }
    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return TYPE_SHOW_GRADE;
        }else{
            return TYPE_BAR_CHART;
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ResultData resultData = mResultList.get(position);
       if (holder instanceof BarChartViewHolder) {
       ((BarChartViewHolder) holder).barChartView.setmResultData(resultData);
       }
       if (holder instanceof GradeViewHolder) {
           ((GradeViewHolder) holder).ivGrade.setImageResource(img[Integer.parseInt(resultData.getGrade())]);
       }
        /*
         int type =getItemViewType(position);
        switch (type){
            case TYPE_BAR_CHART:
                break;
            case TYPE_SHOW_GRADE:
                break;
         */
        }
    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public static class BarChartViewHolder extends RecyclerView.ViewHolder {

        View barChartItemView;
        BarChartView barChartView;
        public BarChartViewHolder(@NonNull View itemView) {
            super(itemView);
            barChartItemView = itemView;
            barChartView =itemView.findViewById(R.id.bar_chart_view);

        }
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {

        View gradeShowItemView;
        ImageView ivGrade;
        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeShowItemView = itemView;
            ivGrade =itemView.findViewById(R.id.img_grade);

        }
    }
}
