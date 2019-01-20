package com.example.month.network;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApis {
    @GET
    Observable<ResponseBody> get(@Url String url);

    @POST
    Observable<ResponseBody> psot(@Url String url, @QueryMap Map<String,String> map);

    @POST
    Observable<ResponseBody> postFile(@Url String url, @Body MultipartBody multipartBody);
}
