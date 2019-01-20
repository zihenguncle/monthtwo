package com.example.month.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.activity.DetailActivity;
import com.example.month.bean.ListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePagerAdapter extends RecyclerView.Adapter<HomePagerAdapter.ViewHolder> {

    private List<ListBean.DataBean> data;
    private Context context;

    public HomePagerAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<ListBean.DataBean> data) {
        this.data.clear();
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addData(List<ListBean.DataBean> data) {
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomePagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.home_adapter_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerAdapter.ViewHolder viewHolder, final int i) {
        String images = data.get(i).getImages();
        String[] split = images.split("\\!");
        viewHolder.simpleDraweeView_p.setImageURI(split[0]);
        viewHolder.price.setText("￥"+data.get(i).getPrice());
        viewHolder.title.setText(data.get(i).getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pid",data.get(i).getPid()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_item)
        SimpleDraweeView simpleDraweeView_p;
        @BindView(R.id.title_item)
        TextView title;
        @BindView(R.id.price_item)
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
