package com.marvin.lop.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.image.ImageLoaderConfig;
import com.marvin.lop.task.AsyncCallable;
import com.marvin.lop.task.Callback;
import com.marvin.lop.task.EMobileTask;
import com.marvin.lop.task.ProgressCallable;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Locale;
import java.util.concurrent.Callable;


/**
 * Created by Marvin on 2016/3/18.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * Describe:  Activity的父类
 */
public abstract class BaseActivity extends Activity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    protected Handler mHandler = null;
    // 输入法框架结构的核心API，应用程序之间进行调度和当前输入法交互
    protected InputMethodManager imm;
    // TelephonyManager类主要提供一些访问与手机通讯相关的状态和信息
    private TelephonyManager tManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使用第三方开源库来加载图片
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
        }
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 绑定控件ID
     */
    protected abstract void findViewById();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void DisPlay(String content) {
        // TODO: 2016/3/19 第三个参数是1，但是没有定义，所以有问题
//        Toast.makeText(this, content, 1).show();
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载进度条
     */
    public void showProgressDislog() {
        ProgressDialog progressDialog = null;

        if (progressDialog != null) {
            progressDialog.cancel();
        }
        progressDialog = new ProgressDialog(this);
        Drawable drawable = getResources().getDrawable(R.drawable.loading_animation);
        progressDialog.setIndeterminateDrawable(drawable);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("正在加载，请稍后...");
        progressDialog.show();
    }

    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    protected void hideOrShowSoftInput(boolean isShowSoft, EditText editText) {
        if (isShowSoft) {
            imm.showSoftInput(editText, 0);
        } else {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 获得当前程序版本信息
     *
     * @return
     * @throws Exception
     */
    protected String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是当前类的包名，0代表是获取的版本信息
        PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packageInfo.versionName;
    }

    /**
     * 获得设备Id
     *
     * @return
     * @throws Exception
     */
    protected String getDeviceId() throws Exception {
        String deviceId = tManager.getDeviceId();
        return deviceId;
    }

    /**
     * 获取SIM卡序列号
     *
     * @return
     */
    protected String getToken() {
        return tManager.getSimSerialNumber();
    }

    /**
     * 获得系统版本
     *
     * @return
     */
    protected String getClientOs() {
        return Build.ID;
    }

    /**
     * 获得系统版本号
     *
     * @return
     */
    protected String getClientOsVer() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获得系统语言包
     *
     * @return
     */
    protected String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取设备所在国家
     *
     * @return
     */
    protected String getCountry() {
        return Locale.getDefault().getCountry();
    }

    /**
     * @param pCallable          需要异步调用的操作
     * @param pCallback          回调
     * @param pExceptionCallback
     * @param showDialog
     * @param message
     * @param <T>                模板参数，操作时要返回的内容
     */
    protected <T> void doAsync(final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception>
            pExceptionCallback, final boolean showDialog, String message) {
        EMobileTask.doAsync(this, null, message, pCallable, pCallback, pExceptionCallback, false, showDialog);
    }

    protected <T> void doAsync(final CharSequence pTitle, final CharSequence pMessage, final Callable<T> pCallable,
                               final Callback<T> pCallback, final boolean showDialog) {
        EMobileTask.doAsync(this, pTitle, pMessage, pCallable, pCallback, null, false, showDialog);
    }

    /**
     * Performs a task in the background, showing a ProgressDialog,
     * while the Callable is being processed.
     *
     * @param pTitleResID
     * @param pMessageResID
     * @param pCallable
     * @param pCallback
     * @param <T>
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final Callable<T> pCallable,
                               final Callback<T> pCallback) {
        this.doAsync(pTitleResID, pMessageResID, pCallable, pCallback, null);
    }

    /**
     * Performs a task in the background, showing a indeterminate
     * ProgressDialog, while the Callable is being processed.
     *
     * @param pTitleResID
     * @param pMessageResID
     * @param pCallable
     * @param pCallback
     * @param pExceptionCallback
     * @param <T>
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final Callable<T> pCallable,
                               final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doAsync(this, pTitleResID, pMessageResID, pCallable, pCallback, pExceptionCallback);
    }

    /**
     * Performs a task in the background, showing a ProgressDialog with
     * an ProgressBar, while the AsyncCallable is being processed.
     *
     * @param pTitleResID
     * @param pCallable
     * @param pCallback
     * @param <T>
     */
    protected <T> void doProgressAsync(final int pTitleResID, final ProgressCallable<T> pCallable,
                                       final Callback<T> pCallback) {
        this.doProgressAsync(pTitleResID, pCallable, pCallback, null);
    }

    /**
     * Performs a task in the background, showing a ProgressDialog with
     * a ProgressBar, while the AsyncCallable is being processed.
     *
     * @param pTitleResID
     * @param pCallable
     * @param pCallback
     * @param pExceptionCallback
     * @param <T>
     */
    protected <T> void doProgressAsync(final int pTitleResID, final ProgressCallable<T> pCallable,
                                       final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doProgressAsync(this, pTitleResID, pCallable, pCallback, pExceptionCallback);
    }

    /**
     * Performs a task in the background, showing an indeterminate
     * ProgressDialog, while the AsyncCallable is being processed.
     *
     * @param pTitleResID
     * @param pMessageResID
     * @param pAsyncCallable
     * @param pCallback
     * @param pExceptionCallback
     * @param <T>
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final AsyncCallable<T> pAsyncCallable,
                               final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doAsync(this, pTitleResID, pMessageResID, pAsyncCallable, pCallback, pExceptionCallback);
    }

}
