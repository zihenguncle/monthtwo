package com.example.month.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.example.month.R;
import com.example.month.activity.HomePageActivity;
import com.example.month.adapter.HomePagerAdapter;
import com.example.month.base.BaseFragment;
import com.example.month.bean.ListBean;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.url.Apis;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageFragment extends BaseFragment implements IView {

    @BindView(R.id.xrecycle)
    XRecyclerView xRecyclerView;

    IPersenterImpl iPersenter;

    HomePagerAdapter adapter;
    int page;
    @Override
    protected int getLayout() {
        return R.layout.homepage_fragment;
     }
    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        page = 1;
        //设置布局管理器
        setManage();
        iPersenter = new IPersenterImpl(this);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });
        getData();
        adapter = new HomePagerAdapter(getActivity());
        xRecyclerView.setAdapter(adapter);
    }

    private void getData(){
        Map<String,String> map = new HashMap<>();
        map.put("pscid",2+"");
        map.put("page",page+"");
        map.put("sort",0+"");
        iPersenter.startRequestPost(Apis.LIST_URL,map,ListBean.class);
    }


    private void setManage() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void success(Object data) {
        if(data instanceof ListBean){
            if(page == 1){
                adapter.setData(((ListBean) data).getData());
            }else {
                adapter.addData(((ListBean) data).getData());
            }
            page++;
            xRecyclerView.loadMoreComplete();
            xRecyclerView.refreshComplete();
        }
    }

    @Override
    public void failed(String error) {

    }
}
