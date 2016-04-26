package com.marvin.lop.zxing.decoding;

import android.app.Activity;
import android.content.DialogInterface;
/**
 * Created by Marvin on 2016/3/31.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public final class FinishListener
        implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable {

    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    public void onCancel(DialogInterface dialogInterface) {
        run();
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        run();
    }

    public void run() {
        activityToFinish.finish();
    }

}

