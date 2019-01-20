package com.example.month.one.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.one.adapter.ShopCarAdapter;
import com.example.month.one.bean.CarBean;
import com.example.month.tool.VerifyUtils;
import com.example.month.url.Apis;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopCarActivity extends AppCompatActivity implements IView {

    @BindView(R.id.shopcar_one_recycle)
    RecyclerView recyclerView;
    IPersenterImpl iPersenter;
    private int uid;
    private ShopCarAdapter adapter;

    @BindView(R.id.shop_car_checked)
    CheckBox checked;
    @BindView(R.id.shop_price)
    TextView price;
    @BindView(R.id.goto_pay)
    TextView goto_pay;
    private List<CarBean.DataBean> data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        ButterKnife.bind(this);

        iPersenter = new IPersenterImpl(this);
        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", 0);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ShopCarAdapter(this);
        recyclerView.setAdapter(adapter);

        iPersenter.startRequestGet(String.format(Apis.SELECT_URL,26522),CarBean.class);

        adapter.setOnCallBackListener(new ShopCarAdapter.onCallBackListener() {
            @Override
            public void getCheckState() {
                double i_price = 0.0;
                int i_num = 0;
                int i_count = 0;
                for (int i = 0; i < data1.size(); i++) {
                    List<CarBean.DataBean.ListBean> list = data1.get(i).getList();
                    for (int j = 0; j < list.size(); j++) {
                        i_count+=list.get(j).getNum();
                        if(list.get(j).isThing_check()){
                            i_num+=list.get(j).getNum();
                            i_price += list.get(j).getPrice()*list.get(j).getNum();
                        }
                    }
                }
                if(i_num<i_count){
                    checked.setChecked(false);
                }else {
                    checked.setChecked(true);
                }
                price.setText("￥"+i_price);
                goto_pay.setText("去结算（"+i_num+"）");

            }
        });
    }

    @OnClick({R.id.shop_car_checked})
    public void setonclick(View v){
        switch (v.getId()){
            case R.id.shop_car_checked:
                getcheck(checked.isChecked());
                adapter.notifyDataSetChanged();
                break;
                default:
                    break;
        }
    }

    private void getcheck(boolean checked) {
        double p_price = 0.0;
        int p_num = 0;
        for (int i = 0; i < data1.size(); i++) {
            data1.get(i).setShop_check(checked);
            List<CarBean.DataBean.ListBean> list = data1.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                list.get(j).setThing_check(checked);
                p_num+=list.get(j).getNum();
                p_price+=list.get(j).getPrice()*list.get(j).getNum();
            }
        }
        if(checked){
            price.setText("￥"+p_price);
            goto_pay.setText("去结算（"+p_num+"）");
        }else {
            price.setText("￥"+0.0);
            goto_pay.setText("去结算（"+0+"）");
        }
    }

    @Override
    public void success(Object data) {
        if(data instanceof CarBean){
            data1 = ((CarBean) data).getData();
            adapter.setData(data1);
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
