package com.example.bamboo.fragment.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
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

    @SuppressLint("ClickableViewAccessibility")
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

        //点击与滑动事件冲突解决
        final int[] lastY = new int[1];
        final int[] lastX = new int[1];
        holder.sv_word.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int y = (int) motionEvent.getY();
                int x = (int) motionEvent.getX();
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            lastX[0] =x;
                            lastY[0] =y;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            if (Math.abs(y - lastY[0]) < 25 && Math.abs(x - lastX[0]) < 25) {
                                //如果横纵坐标的偏移量都小于五个像素，那么就把它当做点击事件触发，后面改成了25，比较好认定，毕竟人手比较大
                                holder.tv_word.setOnClickListener(view1 -> {
                                    wordList.setIsClick(wordList.getIsClick()+1);
                                    if ((wordList.getIsClick()%2)==1){
                                        holder.tv_word.setText(wordList.getZh());
                                    }else{
                                        holder.tv_word.setText(wordList.getWord());
                                    }
                                });
                            }
                            break;
                    }
                return false;
            }
        });

        /*

        holder.wordItemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //当用户按下的时候，我们告诉父组件，不要拦截我的事件（这个时候子组件是可以正常响应事件的），拿起之后就会告诉父组件可以阻止。
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

         */
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return mWordListList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View wordItemView;
        TextView tv_word;
        ScrollView sv_word;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView=itemView;
            tv_word=itemView.findViewById(R.id.tv_word);
            sv_word=itemView.findViewById(R.id.sv_word);

        }

        /**
         *
         * @param x event的rowX
         * @param y event的rowY
         * @return 这个点在不在sv的范围内.
         */
        public boolean isTouchNsv(float x,float y) {
            int[] pos = new int[2];
            //获取sv在屏幕上的位置
            sv_word.getLocationOnScreen(pos);
            int width = sv_word.getMeasuredWidth();
            int height = sv_word.getMeasuredHeight();
            return x >= pos[0] && x <= pos[0] + width && y >= pos[1] && y <= pos[1] + height;
        }
    }
}
