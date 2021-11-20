package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bamboo.fragment.ui.adapter.FragmentFactory;
import com.example.bamboo.fragment.ui.adapter.PagerAdapter;
import com.example.bamboo.R;
import com.example.bamboo.fragment.ui.main.BookFragment;
import com.example.bamboo.fragment.ui.main.SquareFragment;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.Personal;
import com.example.bamboo.javaBean.UserLocal;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends BaseActivity {
//    Resources resources=getResources();
//    private String[] tabs =resources.getStringArray(R.array.tabNames);

    private String[] tabs = {"知识广场", "分级词汇", "可读性测评", "个人中心"};
    private int[] tabIcons = {R.drawable.square_select, R.drawable.word,
            R.drawable.text, R.drawable.personal
            ,};
    private int[] tabIconsSelected = {
            R.drawable.square, R.drawable.word_select,
            R.drawable.text_select, R.drawable.personal_select
    };
    private PagerAdapter mPagerAdapter;
    private List<Fragment> FragmentList = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private List<Personal> personList = new ArrayList<>();
    String objectId;
//    UserLocal userLocal = new UserLocal();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserID();
        try {
            getUserPageResponseData();


        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        if (tab.getPosition() == 0) {
            tabImage.setImageResource(R.drawable.square_select);
        } else if (tab.getPosition() == 1) {
            tabImage.setImageResource(R.drawable.word_select);
        } else if (tab.getPosition() == 2) {
            tabImage.setImageResource(R.drawable.text_select);
        } else {
            tabImage.setImageResource(R.drawable.personal_select);
        }
    }

    private void changeTabNormals(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView tabImage = (ImageView) view.findViewById(R.id.tab_content_image);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setTextColor(Color.WHITE);
        if (tab.getPosition() == 0) {
            tabImage.setImageResource(R.drawable.square);
        } else if (tab.getPosition() == 1) {
            tabImage.setImageResource(R.drawable.word);
//            viewPager.setCurrentItem(1);
        } else if (tab.getPosition() == 2) {
            tabImage.setImageResource(R.drawable.text);
//            viewPager.setCurrentItem(2);
        } else {
            tabImage.setImageResource(R.drawable.personal);
        }

    }

    private void init() {
        tabLayout = findViewById(R.id.btn_TabLayout);
        viewPager = findViewById(R.id.viewPagerMain);
        viewPager.setOffscreenPageLimit(4);
    }

    private void initViewPagerFragment() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);
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
        View view = LayoutInflater.from(this).inflate(R.layout.tab_style, null);
        TextView tabTitle = (TextView) view.findViewById(R.id.tab_content_text);
        ImageView tabImage = view.findViewById(R.id.tab_content_image);
        tabImage.setImageResource(tabIcons[position]);
        tabTitle.setText(tabs[position]);
        return view;
    }


    private void getUserPageResponseData() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "userPage";
        JSONObject params = new JSONObject();
        params.put("objectId", objectId);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataWithGson1(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }

    private void parseJsonDataWithGson1(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<Personal>> responsePersonalList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<Personal>>>() {
                }.getType());
        List<Personal> dataResponseList = responsePersonalList.getResults();
        for (Personal personal : dataResponseList) {
            personList.add(personal);

            UserLocal userLocal = LitePal.findFirst(UserLocal.class);
            if (userLocal!=null){
                userLocal.setCoin(personal.getCoin());
                userLocal.setLevel(personal.getLevel());
                userLocal.setLanguage(personal.getLanguage());
                userLocal.updateAll();
//                userLocal.update(1);
            }
            else{
                userLocal=new UserLocal();
                userLocal.setCoin(personal.getCoin());
                userLocal.setLevel(personal.getLevel());
                userLocal.setLanguage(personal.getLanguage());
                userLocal.save(); //用save可以，初次
            }
        }

    }

    private void getUserID() {
        SharedPreferences pref = this.getSharedPreferences("userInformation", MODE_PRIVATE);
        objectId = pref.getString("userID", "");
    }

    @Override
    protected void onDestroy() {
        //其实最好还是退出登录的时候执行这个方法
        super.onDestroy();
        LitePal.deleteAll(UserLocal.class);
    }
}