package com.example.bamboo.Util;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static List<ImageView> getViewpagerInfo(Context context, int image[]){
        List<ImageView> data =new ArrayList<>();
        for (int i = 0;i<image.length;i++) //添加数据
        {
            ImageView imageView =new ImageView(context);
            imageView.setImageResource(image[i]);//图标id，
            data.add(imageView);//数组添加数据，数据就是在main fragment那里
        }

        return data;//返回

    }
}
