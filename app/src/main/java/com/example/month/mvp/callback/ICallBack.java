package com.example.month.mvp.callback;

public interface ICallBack<T> {
    void getData(T data);
    void failed(String error);
}
