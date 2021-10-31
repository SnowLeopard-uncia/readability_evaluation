package com.example.bamboo.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamboo.fragment.ui.adapter.FragmentFactory;
import com.example.bamboo.fragment.ui.adapter.PagerAdapter;
import com.example.bamboo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    Resources resources=getResources();
//    private String[] tabs =resources.getStringArray(R.array.tabNames);

private String[] tabs=  {"知识广场", "分级词汇", "可读性测评","学习报告"};
private int[] tabIcons={R.drawable.square,R.drawable.word,
        R.drawable.text,R.drawable.report
,};
    private int[] tabIconsSelected={
            R.drawable.square_select,R.drawable.word_select,
            R.drawable.text_select,R.drawable.report_select
    };
    private PagerAdapter mPagerAdapter;
    private List<Fragment> FragmentList = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initViewPagerFragment();
        initEvent();
    }

    private void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormals(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeTabSelect(TabLayout.Tab tab) {

        View view = tab.getCustomView();
//        View view = LayoutInflater.from(this).inflate(R.layout.tab_style,null);
        ImageView tabImage = (ImageView) view.findViewById(R.id.tab_content_image);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setTextColor(getResources().getColor(R.color.select_tab));
        if (tab.getPosition()==0) {
            tabImage.setImageResource(R.drawable.square_select);
//            viewPager.setCurrentItem(0);
        } else if (tab.getPosition()==1) {
            tabImage.setImageResource(R.drawable.word_select);
//            viewPager.setCurrentItem(1);
        } else if(tab.getPosition()==2){
            tabImage.setImageResource(R.drawable.text_select);
//            viewPager.setCurrentItem(2);
        }else{
            tabImage.setImageResource(R.drawable.report_select);
        }
    }

    private void changeTabNormals(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView tabImage = (ImageView) view.findViewById(R.id.tab_content_image);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setTextColor(Color.WHITE);
        if (tab.getPosition()==0) {
            tabImage.setImageResource(R.drawable.square);
        } else if (tab.getPosition()==1) {
            tabImage.setImageResource(R.drawable.word);
//            viewPager.setCurrentItem(1);
        } else if(tab.getPosition()==2){
            tabImage.setImageResource(R.drawable.text);
//            viewPager.setCurrentItem(2);
        }else{
            tabImage.setImageResource(R.drawable.report);
        }

    }

    private void init() {
        tabLayout = findViewById(R.id.btn_TabLayout);
        viewPager = findViewById(R.id.viewPager);

    }

    private void initViewPagerFragment() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);
        for (int i = 0; i < tabs.length; i++) {
            FragmentList.add(FragmentFactory.createFragment(i));
            mPagerAdapter.setTabTitles(tabs);
            mPagerAdapter.setFragments(FragmentList);
            viewPager.setAdapter(mPagerAdapter);

            //将TabLayout和ViewPager绑定
            tabLayout.setupWithViewPager(viewPager);
//            tabLayout.getTabAt(i).setCustomView(getTabView(i));  放出来就没事了
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            //mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        }
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
        tabLayout.getTabAt(3).setCustomView(getTabView(3));
    }



    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_style,null);
        TextView tabTitle = (TextView) view.findViewById(R.id.tab_content_text);
        ImageView tabImage=view.findViewById(R.id.tab_content_image);
        tabImage.setImageResource(tabIcons[position]);
        tabTitle.setText(tabs[position]);
        return view;
    }


}