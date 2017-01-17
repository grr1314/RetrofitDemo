package com.fragment.admin.retrofitdemo.http;

import android.util.Log;

import com.fragment.admin.retrofitdemo.factory.FastJsonConverterFactory;
import com.fragment.admin.retrofitdemo.iview.iMovieView;
import com.fragment.admin.retrofitdemo.model.HttpResult;
import com.fragment.admin.retrofitdemo.model.MovieEntity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 这个类是一个单例类
 * Created by admin on 2016/12/28.
 */
public class HttpMethods {
    private static final String TAG = "HttpMethods";
    private static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 10;
    private Retrofit retrofit;
    private Retrofit retrofitFastJson;
    private demoInterface movieService;
    private demoInterface movieServiceFast;

    /**
     * 构造方法私有化
     */
    private HttpMethods() {
        initOkHttpByGson();
        initOkHttpByFast();
    }

    private void initOkHttpByFast() {
        //创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofitFastJson = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        movieServiceFast = retrofitFastJson.create(demoInterface.class);
    }

    private void initOkHttpByGson() {
        //创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        movieService = retrofit.create(demoInterface.class);
    }

    private static class SingletonHolder {
        private static final HttpMethods methods = new HttpMethods();
    }

    /**
     * 获取单例
     */
    public static HttpMethods getInstance() {
        return SingletonHolder.methods;
    }

    /**
     * 创建一个类用来处理Http的resultCode，并将HttpResult的Data部分剥离出来返回给subcsriber
     *
     * @param <T>
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            //分离各种错误
            if (httpResult.getCount() == 0) {
                throw new ApiException(100);
            }
            //返回数据
            return httpResult.getSubjects();
        }
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getTopMovieByGson(Subscriber<List<MovieEntity>> subscriber, int start, int count) {
        Log.e(TAG, "start:" + start + "count:" + count);
        movieService.getTopMovie(start, count)
                .map(new HttpResultFunc<List<MovieEntity>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用于获取豆瓣电影Top250的数据,通过fastjson
     * @param subscriber
     * @param start
     * @param count
     */
    public void getTopMovieByFastJson(Subscriber<List<MovieEntity>> subscriber, int start, int count)
    {
        Log.e(TAG, "start:" + start + "count:" + count);
        movieServiceFast.getTopMovie(start, count)
                .map(new HttpResultFunc<List<MovieEntity>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}

