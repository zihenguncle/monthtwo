package com.example.month.one.custom;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.month.R;
import com.example.month.one.adapter.ShopCarItemAdapter;
import com.example.month.one.bean.CarBean;
import com.example.month.tool.VerifyUtils;

import java.util.List;

public class NumberCustom extends LinearLayout {
    private Context context;
    private EditText editText;
    private ImageView jian;

    public NumberCustom(Context context) {
        super(context);
        this.context = context;
        info();
    }

    public NumberCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        info();
    }

    public NumberCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        info();
    }

    private void info(){
        View view = View.inflate(context, R.layout.custom_item,null);
        final ImageView add = view.findViewById(R.id.add);
        ImageView jian = view.findViewById(R.id.jian);
        editText = view.findViewById(R.id.numedit);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                data.get(position).setNum(num);
                editText.setText(num+"");
                adapter.notifyDataSetChanged();
                callBackListener.getCheckState();
            }
        });
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num>1){
                    num--;
                }else {
                    VerifyUtils.getInstance().toast("手下留情");
                }
                data.get(position).setNum(num);
                editText.setText(num+"");
                adapter.notifyDataSetChanged();
                callBackListener.getCheckState();
            }
        });
        addView(view);
    }

    ShopCarItemAdapter adapter;
    List<CarBean.DataBean.ListBean> data;
    int position;
    int num;
    public void getData(ShopCarItemAdapter adapter, List<CarBean.DataBean.ListBean> bean, int position){
        this.adapter = adapter;
        this.data = bean;
        this.position = position;
        num = data.get(position).getNum();
        editText.setText(num+"");
    }

    public onCallBackListener callBackListener;
    public void setOnCallBackListener(onCallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface onCallBackListener{
        void getCheckState();
    }

}
