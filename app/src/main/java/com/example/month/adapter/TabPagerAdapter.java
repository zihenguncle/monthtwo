package com.example.month.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.month.fragment.HomepageFragment;
import com.example.month.fragment.MineFragment;
import com.example.month.fragment.PagingFragment;
import com.example.month.fragment.ShoppingFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    String[] str = new String[]{"首页","分类","购物车","个人"};

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new HomepageFragment();
            case 1:
                return new PagingFragment();
            case 2:
                return new ShoppingFragment();
            case 3:
                return new MineFragment();
                default:
                    return new Fragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return str[position];
    }

    @Override
    public int getCount() {
        return str.length;
    }
}
