package com.fragment.admin.retrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2016/12/6.
 */
public interface demoInterface {
    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

//    @GET("index")
//    Call<List<MovieEntity>> getTopMovie(@Query("info") String start, @Query("key") String key);
}
