package com.example.month.mvp.view;

public interface IView<T> {
    void success(T data);
    void failed(String error);
}
