package com.example.month.fragment;

import android.view.View;

import com.example.month.R;
import com.example.month.base.BaseFragment;

import butterknife.ButterKnife;

public class ShoppingFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.shopping_fragment;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
    }
}
