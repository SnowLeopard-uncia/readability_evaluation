package com.snowleopard.bamboo.fragment.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles;
    private int[] tabIcons;
    private int[] tabIconSelect;
    private List<Fragment> fragments=null;
    private  Context mContext;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, Context mContext) {
        super(fm, behavior);
        this.mContext=mContext;
    }

    public int[] getTabIcons() {
        return tabIcons;
    }

    public void setTabIcons(int[] tabIcons) {
        this.tabIcons = tabIcons;
    }

    public int[] getTabIconSelect() {
        return tabIconSelect;
    }

    public void setTabIconSelect(int[] tabIconSelect) {
        this.tabIconSelect = tabIconSelect;
    }

    public String[] getTabTitles() {
        return tabTitles;
    }

    public void setTabTitles(String[] tabTitles) {
        this.tabTitles = tabTitles;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
