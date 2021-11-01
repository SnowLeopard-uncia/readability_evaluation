package com.example.bamboo.fragment.ui.main;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


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

import com.example.bamboo.R;


import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class SquareFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> slideMenuItems = new ArrayList<>();
    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private ImageView imgToBookFragment;
    private ImageView imgToAudioFragment;
    private ImageView imgToVideoFragment;
    private AudioFragment audioFragment = new AudioFragment();
    private VideoFragment videoFragment = new VideoFragment();
    private BookFragment bookFragment = new BookFragment();


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setActionBar();
        createMenuList();


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.in_square_fragment_layout, bookFragment)
                .hide(bookFragment)
                .add(R.id.in_square_fragment_layout, audioFragment)
                .hide(audioFragment)
                .add(R.id.in_square_fragment_layout, videoFragment)
                .hide(videoFragment)
                .commit();
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
        toolbar = getView().findViewById(R.id.toolbar);


        imgToAudioFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                restartButton();
                imgToAudioFragment.setBackgroundResource(R.color.slide_select_bg);
                drawerLayout.closeDrawers();
                if (!audioFragment.isVisible()) {
                    fragmentTransaction.show(audioFragment)
                            .hide(bookFragment)
                            .hide(videoFragment)
                            .commit();
                }

            }
        });

        imgToBookFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                restartButton();
                imgToBookFragment.setBackgroundResource(R.color.slide_select_bg);
                drawerLayout.closeDrawers();
                if (!bookFragment.isVisible()) {
                    fragmentTransaction.show(bookFragment)
                            .hide(videoFragment)
                            .hide(audioFragment)
                            .commit();
                }

            }
        });

        imgToVideoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                restartButton();
                imgToVideoFragment.setBackgroundResource(R.color.slide_select_bg);
                drawerLayout.closeDrawers();
                if (!videoFragment.isVisible()) {
                    fragmentTransaction.show(videoFragment)
                            .hide(bookFragment)
                            .hide(audioFragment)
                            .commit();
                }

            }
        });

    }

    private void restartButton() {
        imgToBookFragment.setBackgroundResource(R.color.slide_menu_img_bg);
        imgToAudioFragment.setBackgroundResource(R.color.slide_menu_img_bg);
        imgToVideoFragment.setBackgroundResource(R.color.slide_menu_img_bg);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        return view;
    }


    private void setActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //定义左上角图标是否可以点击
//        toolbar.setNavigationIcon(R.drawable.list);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.list);


        /*
        ((AppCompatActivity) getActivity()).getActionBar().setHomeButtonEnabled(true);
        //在左边添加向左箭头返回键
         */

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

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem("close", R.drawable.icn_close);
        slideMenuItems.add(menuItem0);
        SlideMenuItem menuItem1 = new SlideMenuItem("book", R.drawable.icn_book);
        slideMenuItems.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem("video", R.drawable.icn_video);
        slideMenuItems.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem("audio", R.drawable.icn_audio);
        slideMenuItems.add(menuItem3);
    }

}
