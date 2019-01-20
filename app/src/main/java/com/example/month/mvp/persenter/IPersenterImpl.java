package com.example.month.mvp.persenter;

import com.example.month.mvp.callback.ICallBack;
import com.example.month.mvp.model.IModelImpl;
import com.example.month.mvp.view.IView;

import java.util.Map;

public class IPersenterImpl implements IPersenter {

    private IView iView;
    private IModelImpl iModel;

    public IPersenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void startRequestGet(String url, Class clazz) {
        iModel.requestDataGet(url, clazz, new ICallBack() {
            @Override
            public void getData(Object data) {
                iView.success(data);
            }

            @Override
            public void failed(String error) {
                iView.failed(error);
            }
        });
    }

    @Override
    public void startRequestPost(String url, Map<String, String> map, Class clazz) {
        iModel.requestDataPost(url, map, clazz, new ICallBack() {
            @Override
            public void getData(Object data) {
                iView.success(data);
            }

            @Override
            public void failed(String error) {
                iView.failed(error);
            }
        });
    }

    @Override
    public void startRequestFile(String url, Map<String, String> map, Class clazz) {
        iModel.requestDataFile(url, map, clazz, new ICallBack() {
            @Override
            public void getData(Object data) {
                iView.success(data);
            }

            @Override
            public void failed(String error) {
                iView.failed(error);
            }
        });
    }


    public void detach(){
        if(iModel != null){
            iModel = null;
        }
        if(iView != null){
            iView = null;
        }
    }
}
