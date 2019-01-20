package com.example.month.one.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.month.R;
import com.example.month.bean.HeadBean;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.one.bean.ShopBean;
import com.example.month.tool.VerifyUtils;
import com.example.month.url.Apis;
import com.stx.xhb.xbanner.XBanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity implements IView {

    @BindView(R.id.detail_xbanner)
    XBanner xBanner;
    @BindView(R.id.detail_title)
    TextView title;
    @BindView(R.id.detail_price)
    TextView price;
    @BindView(R.id.detail_addcar)
    Button addcar;

    IPersenterImpl iPersenter;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        Intent intent = getIntent();
        ShopBean.DataBean list = (ShopBean.DataBean) intent.getSerializableExtra("list");
        pid = list.getPid();
        title.setText(list.getTitle());
        price.setText(list.getPrice()+"");
        String images = list.getImages();
        String[] split = images.split("\\!");
        final List<String> iamgeList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            iamgeList.add(split[i]);
        }
        xBanner.setData(iamgeList,null);
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(DetailActivity.this).load(iamgeList.get(position)).into((ImageView) view);
                xBanner.setPageChangeDuration(1000);
            }
        });
    }

    @OnClick({R.id.detail_addcar,R.id.shopcar})
    public void setOnClick(View v){
        switch (v.getId()){
            case R.id.detail_addcar:
                    iPersenter.startRequestGet(String.format(Apis.ADDCAR_URL,26522,pid),HeadBean.class);
                break;
            case R.id.shopcar:
                Intent intent = new Intent(DetailActivity.this,ShopCarActivity.class);
                intent.putExtra("uid",26522);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
    @Override
    public void success(Object data) {
        if(data instanceof HeadBean){
            VerifyUtils.getInstance().toast(((HeadBean) data).getMsg());
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
