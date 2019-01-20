package com.example.month.network;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.Multipart;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {

    private OkHttpClient mClient;

    private static final String BASE_URL = "http://120.27.23.105/";

    private static RetrofitUtils instance;
    private final BaseApis mBaseApis;

    public static RetrofitUtils getInstance(){
        if(instance == null){
            synchronized (RetrofitUtils.class){
                instance = new RetrofitUtils();
            }
        }
        return instance;
    }

    private RetrofitUtils(){

        //网络拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mBaseApis = retrofit.create(BaseApis.class);
    }

    /**
     * 上传文件------头像
     * @param url
     * @param map
     * @param listener
     */
    public void postFile(String url,Map<String,String> map,HttpListener listener){
        if (map == null){
            map = new HashMap<>();
        }
        MultipartBody multipartBody = getBody(map);
        mBaseApis.postFile(url,multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    private MultipartBody getBody(Map<String,String> map){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String,String> entry : map.entrySet()){
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(),"uncle.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"),file));
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody build = builder.build();
        return build;
    }
    /**
     * get
     * @param url
     * @param listener
     */
    public void get(String url,HttpListener listener){
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     * post
     * @param url
     * @param map
     * @param listener
     */
    public void post(String url, Map<String,String> map,HttpListener listener){
        if(map == null){
            map = new HashMap<>();
        }
        mBaseApis.psot(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    private Observer getObserver(final HttpListener listener){
        Observer<ResponseBody> observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (listener != null){
                    listener.getFailed(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                    try {
                        if(listener != null) {
                            String string = responseBody.string();
                            listener.getData(string);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (listener != null){
                            listener.getFailed(e.getMessage());
                        }
                    }
            }
        };
        return observer;
    }


    private HttpListener listener;
    private void setHttpListener(HttpListener listener){
        this.listener = listener;
    }

    public interface HttpListener{
        void getData(String data);
        void getFailed(String error);
    }


}
