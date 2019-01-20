package com.example.month.one.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.example.month.R;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.one.adapter.OneAdapter;
import com.example.month.one.adapter.TwoAdapter;
import com.example.month.one.bean.FristBean;
import com.example.month.one.bean.ListDataBean;
import com.example.month.tool.VerifyUtils;
import com.example.month.url.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScendActivity extends AppCompatActivity implements IView {

    @BindView(R.id.shophome_recycle)
    RecyclerView shophome_recycle;
    @BindView(R.id.thing_recycle)
    RecyclerView thing_recycle;

    IPersenterImpl iPersenter;

    private OneAdapter oneAdapter;

    private TwoAdapter twoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scend);
        ButterKnife.bind(this);
        //设置布局管理器
        setmessage();
        iPersenter = new IPersenterImpl(this);
        //请求网络
        iPersenter.startRequestGet(Apis.ONR_URL,FristBean.class);

        //实例化
        oneAdapter = new OneAdapter(this);
        twoAdapter = new TwoAdapter(this);

        shophome_recycle.setAdapter(oneAdapter);

        oneAdapter.setCid(new OneAdapter.setThisCid() {
            @Override
            public void getcid(int cid) {
                iPersenter.startRequestGet(String.format(Apis.TWO_URL,cid),ListDataBean.class);
            }
        });
        thing_recycle.setAdapter(twoAdapter);
    }

    private void setmessage() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        shophome_recycle.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        thing_recycle.setLayoutManager(linearLayoutManager1);
    }

    @Override
    public void success(Object data) {
        if(data instanceof  FristBean){
            oneAdapter.setData(((FristBean) data).getData());
        }
        if(data instanceof ListDataBean){
            twoAdapter.setData(((ListDataBean) data).getData());
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
