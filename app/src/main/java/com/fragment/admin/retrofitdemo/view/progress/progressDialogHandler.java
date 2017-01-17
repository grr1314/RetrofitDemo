package com.fragment.admin.retrofitdemo.view.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by admin on 2017/1/13.
 */
public class progressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private Context context;
    private boolean cancelble;
    private progressCancelListener mprogressCancelListener;

    public progressDialogHandler(Context context, boolean cancelble, progressCancelListener mprogressCancelListener) {
        super();
        this.context = context;
        this.cancelble = cancelble;
        this.mprogressCancelListener = mprogressCancelListener;
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setMessage("Loading");
            pd.setCancelable(cancelble);
            if (cancelble) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mprogressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG: {
                initProgressDialog();
            }
            break;
            case DISMISS_PROGRESS_DIALOG: {
                dismissProgressDialog();
            }
            break;
        }
    }
}
