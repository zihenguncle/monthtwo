package com.example.month.one.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.one.activity.DetailActivity;
import com.example.month.one.bean.ShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {

    private List<ShopBean.DataBean> data;
    private Context context;

    public ListDataAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<ShopBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.listdata_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDataAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.price.setText(data.get(i).getPrice()+"");
        viewHolder.title.setText(data.get(i).getTitle());
        viewHolder.simpleDraweeView.setImageURI(data.get(i).getimage());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("list",data.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.listdata_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.listdata_title)
        TextView title;
        @BindView(R.id.listdata_price)
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public setDataPid dataPid;
    public void setdataPid(setDataPid pid){
        dataPid = pid;
    }
    public interface setDataPid{
        void setPid(int pid);
    }
}
