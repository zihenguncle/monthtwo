package com.example.month.one.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.one.activity.ShopActivity;
import com.example.month.one.bean.ListDataBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwoItemAdapter extends RecyclerView.Adapter<TwoItemAdapter.ViewHolder> {

    private List<ListDataBean.DataBean.ListBean> data;
    private Context context;

    public TwoItemAdapter(List<ListDataBean.DataBean.ListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TwoItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.twoitem_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TwoItemAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(data.get(i).getName());
        viewHolder.simpleDraweeView.setImageURI(data.get(i).getIcon());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShopActivity.class);
                intent.putExtra("pscid",data.get(i).getPscid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.twoitem_name)
        TextView name;
        @BindView(R.id.twoitem_simple)
        SimpleDraweeView simpleDraweeView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
