package com.fragment.admin.retrofitdemo.model.subscribers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fragment.admin.retrofitdemo.view.progress.progressCancelListener;
import com.fragment.admin.retrofitdemo.view.progress.progressDialogHandler;

import rx.Subscriber;

/**
 * Created by admin on 2017/1/13.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements progressCancelListener {

    private static final String TAG="ProgressSubscriber";
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private progressDialogHandler mProgressDialogHandler;
    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new progressDialogHandler(context, true, this);
    }

    /**
     * 发送一条显示pd的消息
     */
    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(progressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 发送一个取消pd的消息
     */
    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(progressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        Toast.makeText(context,"get top movie completed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Log.e(TAG,"error:"+e.getMessage());
        Toast.makeText(context,"error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
