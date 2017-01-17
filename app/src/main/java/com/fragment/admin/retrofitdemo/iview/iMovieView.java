package com.fragment.admin.retrofitdemo.iview;

import com.fragment.admin.retrofitdemo.presenter.BasePresenter;

/**
 * Created by admin on 2017/1/17.
 */
public interface iMovieView {
    interface View extends BaseView<Presenter> {
        void showDialog();//显示加载进度条

        void dismissDialog();//取消加载进度条

        void showDataInfo(Object t);//更新界面
    }

    interface Presenter extends BasePresenter {
        void loadDataInfo(Object... params);
    }
}
