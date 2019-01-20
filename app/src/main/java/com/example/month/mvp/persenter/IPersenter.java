package com.example.month.mvp.persenter;

import java.util.Map;

public interface IPersenter {
    void startRequestGet(String url,Class clazz);
    void startRequestPost(String url, Map<String,String> map,Class clazz);
    void startRequestFile(String url,Map<String,String> map,Class clazz);
}
