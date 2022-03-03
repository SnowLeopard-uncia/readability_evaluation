package com.snowleopard.bamboo.fragment.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPaperAdapter extends PagerAdapter {//写了一个适配器

    //创建构造方法，传输对象context和集合，集合里面有图片
    protected Context context;
    protected List<ImageView> imageViews;//声明
    public ViewPaperAdapter(Context context, List<ImageView>imageViews){//context+集合
        this.context = context;//布局
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        if(imageViews!=null)
        return imageViews.size();
        else return 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {//初始化方法
        container.addView(imageViews.get(position));//添加
        return imageViews.get(position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {//是否来自object对象
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {//销毁的方法
       container.removeView(imageViews.get(position));//移除
    }
}
