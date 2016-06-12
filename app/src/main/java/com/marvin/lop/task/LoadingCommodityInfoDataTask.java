package com.marvin.lop.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.marvin.lop.ui.linstener.OnDataLoadSuccessLinstener;
import com.marvin.lop.utils.QueryFromServer;

/**
 * Created by Marvin on 2016/6/10.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class LoadingCommodityInfoDataTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = LoadingCommodityInfoDataTask.class.getSimpleName();
    private Context context;

    private OnDataLoadSuccessLinstener onDataLoadSuccessLinstener;

    public OnDataLoadSuccessLinstener getOnDataLoadSuccessLinstener() {
        return onDataLoadSuccessLinstener;
    }

    public void setOnDataLoadSuccessLinstener(OnDataLoadSuccessLinstener onDataLoadSuccessLinstener) {
        this.onDataLoadSuccessLinstener = onDataLoadSuccessLinstener;
    }

    public LoadingCommodityInfoDataTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "正在获取商品信息...");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.i(TAG, "后台异步线程开始运行...");
        new QueryFromServer().queryCommodityInfoFromServer(context);
        return Boolean.TRUE;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.i(TAG, "获取商品信息成功!");
        onDataLoadSuccessLinstener.onSuccess(aBoolean);
    }
}
