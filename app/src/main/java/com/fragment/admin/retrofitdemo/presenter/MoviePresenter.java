package com.fragment.admin.retrofitdemo.presenter;

import android.content.Context;

import com.fragment.admin.retrofitdemo.http.HttpMethods;
import com.fragment.admin.retrofitdemo.iview.iMovieView;
import com.fragment.admin.retrofitdemo.model.subscribers.ProgressSubscriber;
import com.fragment.admin.retrofitdemo.model.subscribers.SubscriberOnNextListener;

/**
 * Created by admin on 2017/1/17.
 */
public class MoviePresenter implements iMovieView.Presenter {

    iMovieView.View view;
    private SubscriberOnNextListener getTopMoveOnNext;
    private Context mContext;

    public MoviePresenter(iMovieView.View view,Context mContext,SubscriberOnNextListener getTopMoveOnNext) {
        this.view = view;
        view.setPresenter(this);
        this.mContext=mContext;
        this.getTopMoveOnNext=getTopMoveOnNext;
    }

    @Override
    public void loadDataInfo(Object... params) {
        //调用Retrofit的方法
        retrofitMeothd(params);
    }

    @Override
    public void start(Object... params) {
        loadDataInfo(params);
    }

    private void retrofitMeothd(Object... params) {
//        HttpMethods.getInstance().getTopMovieByGson(new ProgressSubscriber(getTopMoveOnNext, mContext), (Integer) params[0], (Integer) params[1]);
        HttpMethods.getInstance().getTopMovieByFastJson(new ProgressSubscriber(getTopMoveOnNext, mContext), (Integer) params[0], (Integer) params[1]);
    }

}
