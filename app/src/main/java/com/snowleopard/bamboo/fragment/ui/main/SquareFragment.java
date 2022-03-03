package com.snowleopard.bamboo.fragment.ui.main;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.Personal;
import com.snowleopard.bamboo.javaBean.UserLocal;
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


public class SquareFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
//    private List<SlideMenuItem> slideMenuItems = new ArrayList<>();
    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageView imgToBookFragment;
    private ImageView imgToAudioFragment;
    private ImageView imgToVideoFragment;
//    private ImageView imgChangeLanguage;
    private ImageView img_to_English;
    private ImageView img_to_Spanish;
    private AudioFragment audioFragment = new AudioFragment();
    private VideoFragment videoFragment = new VideoFragment();
    private BookFragment bookFragment = new BookFragment();

    String objectId;
    private List<Personal> personList = new ArrayList<>();


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            getUserPageResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initView();
        setActionBar();
//        createMenuList();


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.in_square_fragment_layout, bookFragment)
                .add(R.id.in_square_fragment_layout, audioFragment)
                .hide(audioFragment)
                .add(R.id.in_square_fragment_layout, videoFragment)
                .hide(videoFragment)
                .commit();
        toolbarTitle.setText(getResources().getString(R.string.book));
        imgToBookFragment.setBackgroundResource(R.drawable.menu_item_selected);
    }

    private void initView() {
        drawerLayout = getView().findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = getView().findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        imgToBookFragment = getView().findViewById(R.id.img_to_book_fragment);
        imgToVideoFragment = getView().findViewById(R.id.img_to_video_fragment);
        imgToAudioFragment = getView().findViewById(R.id.img_to_audio_fragment);
        img_to_English = getView().findViewById(R.id.img_to_English);
        img_to_Spanish = getView().findViewById(R.id.img_to_Spanish);
        toolbar = getView().findViewById(R.id.toolbar);
        toolbarTitle = getView().findViewById(R.id.toolbar_title);


        imgToAudioFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                restartButton();
                imgToAudioFragment.setBackgroundResource(R.drawable.menu_item_selected);
                drawerLayout.closeDrawers();
                if (!audioFragment.isVisible()) {
                    fragmentTransaction.show(audioFragment)
                            .hide(bookFragment)
                            .hide(videoFragment)
                            .commit();
                }
                toolbarTitle.setText(getResources().getString(R.string.audio));
            }
        });

        imgToBookFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                restartButton();
                imgToBookFragment.setBackgroundResource(R.drawable.menu_item_selected);
                drawerLayout.closeDrawers();
                if (!bookFragment.isVisible()) {
                    fragmentTransaction.show(bookFragment)
                            .hide(videoFragment)
                            .hide(audioFragment)
                            .commit();
                }
                toolbarTitle.setText(getResources().getString(R.string.book));

            }
        });

        imgToVideoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                restartButton();
                imgToVideoFragment.setBackgroundResource(R.drawable.menu_item_selected);
                drawerLayout.closeDrawers();
                if (!videoFragment.isVisible()) {
                    fragmentTransaction.show(videoFragment)
                            .hide(bookFragment)
                            .hide(audioFragment)
                            .commit();
                }
                toolbarTitle.setText(getResources().getString(R.string.video));
            }
        });

        img_to_English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    updateLanguage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent("changeLanguage");
                intent.putExtra("language","English");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                drawerLayout.closeDrawers();

            }
        });

        img_to_Spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateLanguage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent("changeLanguage");
                intent.putExtra("language","Spanish");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                drawerLayout.closeDrawers();

            }
        });


    }

    private void restartButton() {
        imgToBookFragment.setBackgroundResource(R.drawable.menu_item_unselect);
        imgToAudioFragment.setBackgroundResource(R.drawable.menu_item_unselect);
        imgToVideoFragment.setBackgroundResource(R.drawable.menu_item_unselect);
        img_to_English.setBackgroundResource(R.drawable.menu_item_unselect);
        img_to_Spanish.setBackgroundResource(R.drawable.menu_item_unselect);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        return view;
    }


    private void setActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        //定义左上角图标是否可以点击
//        toolbar.setNavigationIcon(R.drawable.list);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.list);


        //注册侧滑菜单监听器
        drawerToggle = new ActionBarDrawerToggle(
                getActivity(),/* host Activity */
                drawerLayout,/* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */

        ) {

            //当侧滑菜单关闭时
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            //当侧滑菜单滑动时
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            //当侧滑菜单打开时
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    //当onCreate彻底执行完毕
    protected void onPostCreate(Bundle savedInstanceState) {
        //ActionBarDrawerToggle的syncState()方法会和Toolbar关联，将图标放入到Toolbar上。
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //当状态改变时图标也改变
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        ((AppCompatActivity) getActivity()).getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

//        return true;

    }


    public void disableHomeButton() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);

    }


    public void enableHomeButton() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }


    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }




    public void updateLanguage() throws JSONException {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "switchLanguage";
        JSONObject params = new JSONObject();
        params.put("objectId", objectId);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    Log.e(TAG, "语言更新done: json：更改语言为" + result);

                    try {
                        getUserPageResponseData();
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });
    }

    private void getUserID() {
        SharedPreferences pref = getActivity().getSharedPreferences("userInformation", MODE_PRIVATE);
        objectId = pref.getString("userID", "");
    }

    private void getUserPageResponseData() throws JSONException {
        Bmob.initialize(getActivity(), "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "userPage";
        JSONObject params = new JSONObject();
        getUserID();
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
        personList.clear(); //新加的
        for (Personal personal : dataResponseList) {
            personList.add(personal);

            UserLocal userLocal = LitePal.findFirst(UserLocal.class);
            if (userLocal!=null){
                userLocal.setCoin(personal.getCoin());
                userLocal.setLevel(personal.getLevel());
                userLocal.setLanguage(personal.getLanguage());
                userLocal.updateAll();
            }
            else{
                userLocal=new UserLocal();
                userLocal.setCoin(personal.getCoin());
                userLocal.setLevel(personal.getLevel());
                userLocal.setLanguage(personal.getLanguage());
                userLocal.save(); //用save可以，初次
            }

            if (userLocal.getLanguage().equals("English")){
                img_to_English.setVisibility(View.INVISIBLE);
                img_to_Spanish.setVisibility(View.VISIBLE);

            }else {
                img_to_Spanish.setVisibility(View.INVISIBLE);
                img_to_English.setVisibility(View.VISIBLE);
            }

            List<UserLocal> personalList = LitePal.findAll(UserLocal.class);
            for (UserLocal userLocal1:personalList){
                Log.e(TAG, "知识广场个人接口: "+userLocal1.getLanguage());
            }
        }
    }
}
