package com.example.month.one.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.one.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarAdapter.ViewHolder> {

    private List<CarBean.DataBean> data;
    private Context context;

    public ShopCarAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<CarBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopCarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.shopcar_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopCarAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(data.get(i).getSellerName());
        viewHolder.check.setChecked(data.get(i).isShop_check());
        //设置适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        final ShopCarItemAdapter adapter = new ShopCarItemAdapter(data.get(i).getList(),context);
        viewHolder.recyclerView.setAdapter(adapter);

        adapter.setOnCallBackListener(new ShopCarItemAdapter.onCallBackListener() {
            @Override
            public void getCheckState() {
                if(callBackListener != null){
                    callBackListener.getCheckState();
                }
                boolean ischeck = true;
                List<CarBean.DataBean.ListBean> list = data.get(i).getList();
                for (CarBean.DataBean.ListBean listbean: list){
                    if(!listbean.isThing_check()){
                        ischeck = false;
                    }
                }
                data.get(i).setShop_check(ischeck);
                viewHolder.check.setChecked(ischeck);
            }
        });
        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(i).setShop_check(viewHolder.check.isChecked());
                adapter.setCheck(viewHolder.check.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shopcar_item_title)
        TextView title;
        @BindView(R.id.shopcar_item_recycle)
        RecyclerView recyclerView;
        @BindView(R.id.shopcar_item_check)
        CheckBox check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public onCallBackListener callBackListener;
    public void setOnCallBackListener(onCallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface onCallBackListener{
        void getCheckState();
    }
}
