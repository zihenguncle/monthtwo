package com.example.month.mvp.model;

import com.example.month.mvp.callback.ICallBack;

import java.util.Map;

public interface IModel {
    void requestDataGet(String url, Class clazz, ICallBack iCallBack);
    void requestDataPost(String url,  Map<String,String> map,Class clazz,ICallBack iCallBack);
    void requestDataFile(String url,Map<String,String> map,Class clazz,ICallBack iCallBack);
}
