package com.example.bamboo.fragment.ui.adapter;

import androidx.fragment.app.Fragment;

import com.example.bamboo.fragment.ui.main.ReportsFragment;
import com.example.bamboo.fragment.ui.main.SquareFragment;
import com.example.bamboo.fragment.ui.main.TextFragment;
import com.example.bamboo.fragment.ui.main.WordFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int TAB_SQUARE =0;
    public static final int TAB_WORD =1;
    public static final int TAB_TEXT =2;
    public static final int TAB_REPORT =3;

    private static Map<Integer,Fragment> mFragmentMap = new HashMap<>();
    public static Fragment createFragment(int index) {
        Fragment fragment = mFragmentMap.get(index);
        if (fragment == null) {
            switch (index) {
                case TAB_SQUARE:
                    fragment = new SquareFragment();
                    break;
                case TAB_WORD:
                    fragment = new WordFragment();
                    break;
                case TAB_TEXT:
                    fragment = new TextFragment();
                    break;
                case TAB_REPORT:
                    fragment = new ReportsFragment();
                    break;


            }
            //把创建的fragment存起来
            mFragmentMap.put(index,fragment);
        }
        return fragment;
    }
}