package com.example.month.mvp.model;

import com.example.month.mvp.callback.ICallBack;
import com.example.month.network.RetrofitUtils;
import com.example.month.tool.VerifyUtils;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel{
    @Override
    public void requestDataGet(String url, final Class clazz, final ICallBack iCallBack) {
        if(!VerifyUtils.getInstance().isNetworkConnected()){
            VerifyUtils.getInstance().toast("请检查你的网络");
        }else {
            RetrofitUtils.getInstance().get(url, new RetrofitUtils.HttpListener() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    iCallBack.getData(o);
                }

                @Override
                public void getFailed(String error) {
                    iCallBack.failed(error);
                }
            });
        }
    }

    @Override
    public void requestDataPost(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {
        if(!VerifyUtils.getInstance().isNetworkConnected()){
            VerifyUtils.getInstance().toast("请检查你的网络");
        }else {
            RetrofitUtils.getInstance().post(url, map, new RetrofitUtils.HttpListener() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    iCallBack.getData(o);
                }

                @Override
                public void getFailed(String error) {
                    iCallBack.failed(error);
                }
            });
        }
    }

    @Override
    public void requestDataFile(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {
        if(!VerifyUtils.getInstance().isNetworkConnected()){
            VerifyUtils.getInstance().toast("请检查你的网络");
        }else {
            RetrofitUtils.getInstance().postFile(url, map, new RetrofitUtils.HttpListener() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    iCallBack.getData(o);
                }

                @Override
                public void getFailed(String error) {
                    iCallBack.failed(error);
                }
            });
        }
    }

}
