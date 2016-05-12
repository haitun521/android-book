package com.haitun.book.utils;

import com.haitun.book.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 笨笨 on 2016/4/27.
 */
public class HTTPUtil {
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static HTTPUtil ourInstance;
    private static Retrofit retrofit;

    private HTTPUtil() {

        ///这个是用来打印日志的
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static HTTPUtil getInstance() {
        if(ourInstance==null){
            synchronized (HTTPUtil.class){
               if(ourInstance==null){
                   ourInstance=new HTTPUtil();
               }
            }
        }
        return ourInstance;
    }

    public final  <T> T getService(Class<T> clazz){
        return retrofit.create(clazz);
    }
}
