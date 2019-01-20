package com.example.month.one.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.month.R;
import com.example.month.bean.HeadBean;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.one.adapter.ListDataAdapter;
import com.example.month.one.bean.ShopBean;
import com.example.month.tool.VerifyUtils;
import com.example.month.url.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopActivity extends AppCompatActivity implements IView {

    @BindView(R.id.shop_reccycle)
    RecyclerView recyclerView;
    IPersenterImpl iPersenter;
    private ListDataAdapter listDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);

        iPersenter = new IPersenterImpl(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        int pscid = intent.getIntExtra("pscid", 0);
        iPersenter.startRequestGet(String.format(Apis.LIST_DATA_URL,pscid),ShopBean.class);
        listDataAdapter = new ListDataAdapter(this);
        recyclerView.setAdapter(listDataAdapter);

    }

    @Override
    public void success(Object data) {
        if(data instanceof ShopBean){
            listDataAdapter.setData(((ShopBean) data).getData());
        }
        if(data instanceof HeadBean){
            VerifyUtils.getInstance().toast(((HeadBean) data).getMsg());
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
        Log.i("TAG",error);
    }
}
