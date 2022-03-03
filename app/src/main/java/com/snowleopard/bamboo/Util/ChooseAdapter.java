package com.snowleopard.bamboo.Util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.snowleopard.bamboo.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChooseAdapter extends PagerAdapter {
    private Context context; //上下文
    private List<String> stringList;
    private List<String> chineseList;
    private int[] imgList; //数据源

    @Override
    public int getCount() {
        return stringList.size();
    }

    //判断一个页面(View)是否与instantiateItem方法返回的Object一致
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    //添加视图


    @NonNull
    @Override
    public @NotNull Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        //加载vp的布局
        View inflate = View.inflate(context, R.layout.item_choose_language, null);
        //给布局控件赋值
        TextView tvChinese = inflate.findViewById(R.id.tv_language_chinese);
        tvChinese.setText(chineseList.get(position));
        TextView tvLanguage = inflate.findViewById(R.id.tv_language);
        tvLanguage.setText(stringList.get(position));

        ImageView imageView = inflate.findViewById(R.id.iv_language);
        imageView.setImageResource(imgList[position]);

        //添加一个布局（不添加无效果）
        container.addView(inflate);
        return inflate;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return getItemPosition(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        //移除视图
        container.removeView((View) object);
    }


    public ChooseAdapter(Context context, List<String> stringList, List<String> chineseList, int[] imgList) {
        this.context = context;
        this.stringList = stringList;
        this.chineseList = chineseList;
        this.imgList = imgList;
    }

}
