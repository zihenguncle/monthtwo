package com.example.month.one.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.one.bean.ListDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.ViewHolder> {

    private List<ListDataBean.DataBean> data;
    private Context context;
    private TwoItemAdapter adapter;
    private final int spanCount = 3;

    public TwoAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<ListDataBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.two_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TwoAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(data.get(i).getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new TwoItemAdapter(data.get(i).getList(),context);
        viewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.two_recycle)
        RecyclerView recyclerView;
        @BindView(R.id.two_titlename)
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
