package com.example.month.one.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.one.bean.CarBean;
import com.example.month.one.bean.ShopBean;
import com.example.month.one.custom.NumberCustom;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopCarItemAdapter extends RecyclerView.Adapter<ShopCarItemAdapter.ViewHolder> {

    private List<CarBean.DataBean.ListBean> data;
    private Context context;

    public ShopCarItemAdapter(List<CarBean.DataBean.ListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ShopCarItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.shopcar_item_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopCarItemAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.price.setText(data.get(i).getPrice()+"");
        viewHolder.title.setText(data.get(i).getTitle());
        viewHolder.simpleDraweeView.setImageURI(data.get(i).getImage());
        viewHolder.checkBox.setChecked(data.get(i).isThing_check());
        viewHolder.numberCustom.getData(this,data,i);
        //自定义布局加加减减
        viewHolder.numberCustom.setOnCallBackListener(new NumberCustom.onCallBackListener() {
            @Override
            public void getCheckState() {
                if(callBackListener != null){
                    callBackListener.getCheckState();
                }
            }
        });
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(callBackListener != null){
                    data.get(i).setThing_check(viewHolder.checkBox.isChecked());
                    callBackListener.getCheckState();
                }
            }
        });

    }

    public void setCheck(boolean ischeck){
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setThing_check(ischeck);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_check)
        CheckBox checkBox;
        @BindView(R.id.item_price)
        TextView price;
        @BindView(R.id.item_title)
        TextView title;
        @BindView(R.id.item_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.numberCustom)
        NumberCustom numberCustom;
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
