package com.dongjin.android.hongf.model;

import java.util.List;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kimdongjin on 2017. 1. 26..
 */

public interface DaumSearchApi {

    @GET("local/v1/search/keyword.json/")
    Observable<List<SearchItem>> daumSearch(@Header("x-appid")String appId,
                                            @Header("x-platform")String android,
                                            @Query(value = "query",encoded = true) String query,
                                            @Query(value = "location",encoded = true) String location,
                                            @Query(value = "radious",encoded = true) Integer radious);




    class Factory {
        public static DaumSearchApi create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://apis.daum.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(DaumSearchApi.class);
        }
    }
}
