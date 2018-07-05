package com.example.rxjava.hank.rxjavasample.Helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static String API_DOMAIN = "http://test-bike.infinitas.tech";
    private static OkHttpClient mOkHttpClient;

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_DOMAIN) //domain 路徑
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }
}
