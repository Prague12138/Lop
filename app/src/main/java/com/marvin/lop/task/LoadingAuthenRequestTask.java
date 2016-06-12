package com.marvin.lop.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.linstener.OnDataLoadSuccessLinstener;
import com.marvin.lop.utils.QueryFromServer;

/**
 * Created by Marvin on 2016/4/16.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 用来加载请求认证的用户数据的异步线程
 */
public class LoadingAuthenRequestTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = LoadingAuthenRequestTask.class.getSimpleName();

    private Context context;

    private SharedPreferences sharedPreferences;

    private OnDataLoadSuccessLinstener onDataLoadSuccessLinstener;

    public OnDataLoadSuccessLinstener getOnDataLoadSuccessLinstener() {
        return onDataLoadSuccessLinstener;
    }

    public void setOnDataLoadSuccessLinstener(OnDataLoadSuccessLinstener onDataLoadSuccessLinstener) {
        this.onDataLoadSuccessLinstener = onDataLoadSuccessLinstener;
    }

    public LoadingAuthenRequestTask(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, Context.MODE_PRIVATE);
    }

    //用户执行后台任务开始执行前的一些界面上的初始化操作，显示一个progressBar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "正在查询用户请求数据...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // 查询当前登录用户的权限，然后根据权限查找相应的请求数据
        Log.i(TAG, "后台异步线程开始运行...");
        int userPermission = sharedPreferences.getInt(Constants.SharedPreferencesConfig.USER_PERMISSION, 0);
        Log.i(TAG, "异步线程获取登录用户的权限:" + userPermission);
        if (userPermission != 0) {
            switch (userPermission) {
                case Constants.UserPermission.USER_PERMISSION_ADMIN:
                    // 管理员权限
                    // 把获取的数据存入List中
                    new QueryFromServer().queryAuthenDataFromServer(context, Constants.UserPermission.USER_PERMISSION_MONITOR,
                            Constants.UserAuthenState.BEING_CERTIFIED, false);
                    break;
                case Constants.UserPermission.USER_PERMISSION_MONITOR:
                    // 班长权限
                    // 把获取的数据存入List中
                    new QueryFromServer().queryAuthenDataFromServer(context, Constants.UserPermission.USER_PERMISSION_STUDENT,
                            Constants.UserAuthenState.BEING_CERTIFIED, false,
                            sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_CLASS, null));
                    break;
                default:
                    break;
            }
        } else {
            Log.i(TAG, "查询不到本地已登录用户的权限");
        }
        return Boolean.TRUE;
    }

    // 在后台任务执行完毕后调用，用来关闭progressBar
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Toast.makeText(context, "成功请求数据", Toast.LENGTH_SHORT).show();
        onDataLoadSuccessLinstener.onSuccess(aBoolean);
    }
}
