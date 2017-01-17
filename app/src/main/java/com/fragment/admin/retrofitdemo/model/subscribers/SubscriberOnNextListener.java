package com.fragment.admin.retrofitdemo.model.subscribers;

/**
 * Created by admin on 2017/1/13.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onStart();
    void onCompleted();
    void onError(Throwable e);
}
