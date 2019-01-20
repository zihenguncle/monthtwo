package com.example.month.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.month.R;
import com.example.month.adapter.TabPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout layout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
        layout.setupWithViewPager(viewPager);
    }
}
