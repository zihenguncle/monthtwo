package com.example.month.one.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.one.bean.FristBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneAdapter extends RecyclerView.Adapter<OneAdapter.ViewHolder> {

    private List<FristBean.DataBean> data;
    private Context context;

    public OneAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<FristBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.one_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.simpleDraweeView.setImageURI(data.get(i).getIcon());
        viewHolder.name.setText(data.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thisCid != null){
                    thisCid.getcid(data.get(i).getCid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.one_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.one_name)
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private setThisCid thisCid;
    public void setCid(setThisCid setThisCid){
        thisCid = setThisCid;
    }
    public interface setThisCid{
        void getcid(int cid);
    }

}
